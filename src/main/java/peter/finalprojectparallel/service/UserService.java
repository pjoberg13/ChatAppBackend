package peter.finalprojectparallel.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import peter.finalprojectparallel.dto.ChannelDto;
import peter.finalprojectparallel.dto.UserDto;
import peter.finalprojectparallel.model.User;

@Service
@AllArgsConstructor
public class UserService {

    public UserDto mapToDto(User user) {
        return UserDto.builder()
                .id(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .numberOfChannels(user.getSubscribedChannels().size())
                .build();
    }

}
