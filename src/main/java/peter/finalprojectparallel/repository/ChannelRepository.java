package peter.finalprojectparallel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peter.finalprojectparallel.model.Channel;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
}
