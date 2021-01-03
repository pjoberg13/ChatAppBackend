package peter.finalprojectparallel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peter.finalprojectparallel.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
