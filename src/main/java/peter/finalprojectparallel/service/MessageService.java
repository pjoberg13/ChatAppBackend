package peter.finalprojectparallel.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peter.finalprojectparallel.dto.MessageRequest;
import peter.finalprojectparallel.dto.MessageResponse;
import peter.finalprojectparallel.exception.ChannelNotFoundException;
import peter.finalprojectparallel.mapper.MessageMapper;
import peter.finalprojectparallel.model.Channel;
import peter.finalprojectparallel.model.Message;
import peter.finalprojectparallel.repository.ChannelRepository;
import peter.finalprojectparallel.repository.MessageRepository;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;
    private final MessageMapper messageMapper;
    private final AuthService authService;

    public void create(MessageRequest messageRequest, Long channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFoundException(messageRequest.getChannelName()));

        Message savedMessage = messageRepository.save(messageMapper.mapFromDto(messageRequest, channel, authService.getCurrentUser()));
        channel.getMessageList().add(savedMessage);
        channelRepository.save(channel);
    }

    public List<MessageResponse> getAllByChannel(Long channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFoundException(channelId.toString()));
        List<Message> messages = messageRepository.findAllByChannel(channel);
        return messages.stream()
                .map(messageMapper::mapToDto)
                .collect(toList());
    }
}
