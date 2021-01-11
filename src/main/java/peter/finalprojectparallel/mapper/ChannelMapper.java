package peter.finalprojectparallel.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import peter.finalprojectparallel.dto.ChannelDto;
import peter.finalprojectparallel.model.Channel;
import peter.finalprojectparallel.model.Message;
import peter.finalprojectparallel.model.User;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ChannelMapper {

    @Mapping(target = "numberOfMessages",
            expression = "java(mapMessages(channel.getMessageList()))")
    @Mapping(target = "numberOfUsers",
            expression = "java(mapUsers(channel.getSubscribedUsers()))")
    ChannelDto mapChannelToDto(Channel channel);

    default Integer mapMessages(List<Message> numberOfMessages) {
        return numberOfMessages.size();
    }

    default Integer mapUsers(Collection<User> numberOfUsers) {
        return numberOfUsers.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "messageList", ignore = true)
    @Mapping(target = "subscribedUsers", ignore = true)
    Channel mapDtoToChannel(ChannelDto channelDto);
}
