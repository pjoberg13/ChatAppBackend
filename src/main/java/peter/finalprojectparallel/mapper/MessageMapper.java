package peter.finalprojectparallel.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import peter.finalprojectparallel.dto.MessageRequest;
import peter.finalprojectparallel.dto.MessageResponse;
import peter.finalprojectparallel.model.Channel;
import peter.finalprojectparallel.model.Message;
import peter.finalprojectparallel.model.User;

@AllArgsConstructor
//@NoArgsConstructor
@Mapper(componentModel = "spring")
public abstract class MessageMapper {

    @Mapping(target = "created", expression = "java(java.time.Instant.now())")
    @Mapping(target = "channel", source = "channel")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "content", source = "messageRequest.content")
    public abstract Message mapFromDto(MessageRequest messageRequest, Channel channel, User user);

    @Mapping(target = "messageId", source = "messageId")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "channelName", source = "channel.channelName")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "duration", expression = "java(getDuration(message))")
    public abstract MessageResponse mapToDto(Message message);

    String getDuration(Message message) {
        return TimeAgo.using(message.getCreated().toEpochMilli());
    }
}

//String getDuration(Post post) { return TimeAgo.using(post.getCreatedDate().toEpochMilli()); }

//@Mapping(target = "id", source = "postId")
//@Mapping(target = "postName", source = "postName")
//@Mapping(target = "description", source = "description")
//@Mapping(target = "url", source = "url")
//@Mapping(target = "subredditName", source = "subreddit.name")
//@Mapping(target = "userName", source = "user.username")
////@Mapping(target = "commentCount", expression = "java(commentCount(post))")
//@Mapping(target = "duration", expression = "java(getDuration(post))")
//public abstract PostResponse mapToDto(Post post);

//@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
//@Mapping(target = "subreddit", source = "subreddit")
//@Mapping(target = "user", source = "user")
//@Mapping(target = "description", source = "postRequest.description")
//@Mapping(target = "voteCount", constant = "0")
//public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);
