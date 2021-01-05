package peter.finalprojectparallel.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peter.finalprojectparallel.dto.ChannelDto;
import peter.finalprojectparallel.model.Channel;
import peter.finalprojectparallel.repository.ChannelRepository;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class ChannelService {

    private final ChannelRepository channelRepository;

    @Transactional
    public ChannelDto save(ChannelDto channelDto) {
        Channel channel = mapChannelDto(channelDto);
        channelRepository.save(channel);
        channelDto.setId(channel.getChannelId());
        return channelDto;
    }

    private Channel mapChannelDto(ChannelDto channelDto) {
        return Channel.builder().channelName(channelDto.getChannelName())
                .description(channelDto.getDescription())
                .created(Instant.now())
                .build();
    }

    @Transactional(readOnly = true)
    public List<ChannelDto> getAllChannels() {
        return channelRepository.findAll()
                .stream()
                .map(this::mapToChannelDto)
                .collect(toList());
    }

    private ChannelDto mapToChannelDto(Channel channel) {
        return ChannelDto.builder()
                .channelName(channel.getChannelName())
                .id(channel.getChannelId())
                .description(channel.getDescription())
                .numberOfMessages(channel.getMessageList().size())
                .numberOfUsers(channel.getSubscribedUsers().size())
                .build();
    }
}
