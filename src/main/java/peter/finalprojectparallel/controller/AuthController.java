package peter.finalprojectparallel.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import peter.finalprojectparallel.dto.AuthenticationResponse;
import peter.finalprojectparallel.dto.LoginRequest;
import peter.finalprojectparallel.dto.RegisterRequest;
import peter.finalprojectparallel.service.AuthService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * endpoint for sending a POST request with necessary information to sign up a new user to the server
     * @param registerRequest
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity<>("User registration successful", HttpStatus.OK);
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account successfully activated", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}
