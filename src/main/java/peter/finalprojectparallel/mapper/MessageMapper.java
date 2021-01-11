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
