package peter.finalprojectparallel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {
    private Long messageId;
    private String content;
    private Instant created;
    private String channelName;
}
