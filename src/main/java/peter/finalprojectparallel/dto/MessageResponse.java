package peter.finalprojectparallel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    private Long messageId;
    private String content;
    private String duration;
    private String channelName;
    private String userName;
}
