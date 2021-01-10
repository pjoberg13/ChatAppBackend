package peter.finalprojectparallel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peter.finalprojectparallel.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);
}
