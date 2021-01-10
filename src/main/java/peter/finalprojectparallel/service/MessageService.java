package peter.finalprojectparallel.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
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

//@Transactional(readOnly = true)
//public List<PostResponse> getPostsBySubreddit(Long subredditId) {
//    Subreddit subreddit = subredditRepository.findById(subredditId)
//            .orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
//    List<Post> posts = postRepository.findAllBySubreddit(subreddit);
//    return posts.stream().map(postMapper::mapToDto).collect(Collectors.toList());
//}

//public void save(PostRequest postRequest) {
//    Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
//            .orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
//
//    Post savedPost = postRepository.save(postMapper.map(postRequest, subreddit, authService.getCurrentUser()));
//    subreddit.getPosts().add(savedPost);
//    subredditRepository.save(subreddit);
//}
