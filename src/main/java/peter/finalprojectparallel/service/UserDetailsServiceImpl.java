package peter.finalprojectparallel.service;

import peter.finalprojectparallel.model.User;
import peter.finalprojectparallel.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * takes the username as input and returns a userDetails object
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //query db for user with matching username. If username not found throw exception
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        //return object of type userdetails.User, provided by Spring Security, with the User values mapped to it
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), user.getEnabled(),
                true, true, true, getAuthorities("USER"));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }
}
