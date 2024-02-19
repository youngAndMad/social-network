package socialapp.chatservice.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Message {
    private String id;
    private String content;
    private String senderEmail;
    private LocalDateTime sentAt;
}
