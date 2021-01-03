package peter.finalprojectparallel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peter.finalprojectparallel.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
