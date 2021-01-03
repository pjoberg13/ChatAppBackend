package peter.finalprojectparallel.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peter.finalprojectparallel.dto.RegisterRequest;
import peter.finalprojectparallel.exception.QuackAppException;
import peter.finalprojectparallel.model.NotificationEmail;
import peter.finalprojectparallel.model.User;
import peter.finalprojectparallel.model.VerificationToken;
import peter.finalprojectparallel.repository.UserRepository;
import peter.finalprojectparallel.repository.VerificationTokenRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;

    /**
     * This method creates a new instance of user, maps the values from the register request instance to their respective
     * fields in the new user object, sets the created and enabled fields in the new user object, and saves the user
     * to the userRepository that is Autowired in. Finally the method sends an account activation email to the new user.
     * @param registerRequest
     */
    @Transactional
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        //pass the raw text password through the passwordEncoder before storing it to store only the hash and not the raw pw
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);
        userRepository.save(user);
        String token = generateVerificationToken(user);
        //this call to the mailService send mail method instantiates a new Notification email, sets the subject, sets the
        //address to the user's email address, and in the body we append the user's account verification token to a url
        //with our authorization path.
        mailService.sendMail(new NotificationEmail("Please activate your Quack account",
                user.getEmail(), "Follow this link to complete your account activation" +
                "http://localhost:8080/api/auth/accountVerification/" + token ));
        //When the user pastes that url into their browser we take the token, look it up in our DB,
        //and if the token is valid for that user we complete their account verification
    }

    /**
     * This method uses Java's UUID.randomeUUID method to create a random String that we will use as our verification token.
     * The token, as a String, is passed to a new VerificationToken object along with the User it is associated with and that
     * VerificationToken object is saved to the DB
     * @param user
     * @return The String token is then returned to the signup method so it can be sent in an account activation email
     */
    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        //because our method from the repository returns an Optional of type VerificationToken we can call the
        //orElseThrow() method in case there is no verificationToken returned
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new QuackAppException("Invalid token"));
        fetchUserAndEnable(verificationToken.get());
    }

    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user =userRepository.findByUsername(username).orElseThrow(() -> new QuackAppException("User not found with name" + username));
        user.setEnabled(true);
        userRepository.save(user);
    }
}
