package peter.finalprojectparallel.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peter.finalprojectparallel.dto.ChannelDto;
import peter.finalprojectparallel.exception.QuackAppException;
import peter.finalprojectparallel.mapper.ChannelMapper;
import peter.finalprojectparallel.model.Channel;
import peter.finalprojectparallel.model.User;
import peter.finalprojectparallel.repository.ChannelRepository;
import peter.finalprojectparallel.repository.UserRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class ChannelService {

    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final ChannelMapper channelMapper;
    private final UserService userService;

    @Transactional
    public ChannelDto save(ChannelDto channelDto) {
        Channel channel = channelMapper.mapDtoToChannel(channelDto);
        channelRepository.save(channel);
        channelDto.setChannelId(channel.getChannelId());
        return channelDto;
    }

    @Transactional(readOnly = true)
    public List<ChannelDto> getAllChannels() {
        return channelRepository.findAll()
                .stream()
                .map(channelMapper::mapChannelToDto)
                .collect(toList());
    }

    //doesn't work properly
    @Transactional
    public ChannelDto addUserById(Long channelId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new QuackAppException("no user found with that ID"));
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new QuackAppException("no channel found with that ID"));
        channel.getSubscribedUsers().add(user);
        channelRepository.save(channel);
        user.getSubscribedChannels().add(channel);
        userRepository.save(user);
        return channelMapper.mapChannelToDto(channel);
    }

    public String deleteById(Long channelId) {
        channelRepository.deleteById(channelId);
        return "Channel with channel ID " + channelId + " successfully deleted.";
    }

    public ChannelDto getChannelById(Long channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new QuackAppException("no channel found with that ID"));
        return channelMapper.mapChannelToDto(channel);
    }
}

//  hand-written mapping methods made obsolete by mapstruct

//    private ChannelDto mapToChannelDto(Channel channel) {
//        return ChannelDto.builder()
//                .channelName(channel.getChannelName())
//                .channelId(channel.getChannelId())
//                .description(channel.getDescription())
//                .numberOfMessages(channel.getMessageList().size())
//                .numberOfUsers(channel.getSubscribedUsers().size())
//                .build();
//    }

//    private Channel mapChannelDto(ChannelDto channelDto) {
//        return Channel.builder().channelName(channelDto.getChannelName())
//                .description(channelDto.getDescription())
//                .created(Instant.now())
//                .build();
//    }
