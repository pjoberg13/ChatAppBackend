package peter.finalprojectparallel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peter.finalprojectparallel.model.VerificationToken;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);
}
