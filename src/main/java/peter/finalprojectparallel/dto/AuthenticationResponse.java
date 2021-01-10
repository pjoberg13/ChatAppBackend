package peter.finalprojectparallel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private String authenticationToken;
    private String username;
    private String refreshToken;
    private Instant expiresAt;
}
