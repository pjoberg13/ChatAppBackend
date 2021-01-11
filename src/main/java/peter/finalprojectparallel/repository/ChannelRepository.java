package peter.finalprojectparallel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peter.finalprojectparallel.model.Channel;

import java.util.Optional;

public interface ChannelRepository extends JpaRepository<Channel, Long> {

//    Optional<Channel> findByName(String channelName);
}
