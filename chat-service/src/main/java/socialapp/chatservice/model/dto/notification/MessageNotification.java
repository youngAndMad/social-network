package socialapp.chatservice.model.dto.notification;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageNotification extends Notification {
    private String id;
    private String senderName;
    private String message;
}
