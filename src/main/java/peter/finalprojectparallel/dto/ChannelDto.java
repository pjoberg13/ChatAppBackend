package peter.finalprojectparallel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChannelDto {
    private Long channelId;
    private String channelName;
    private String description;
    private Integer numberOfMessages;
    private Integer numberOfUsers;
}
