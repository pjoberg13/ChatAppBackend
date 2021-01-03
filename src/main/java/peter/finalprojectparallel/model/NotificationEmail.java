package peter.finalprojectparallel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotificationEmail {
    private String subject;
    private String recipientAddress;
    private String body;
}
