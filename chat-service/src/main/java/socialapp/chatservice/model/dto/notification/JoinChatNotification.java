package socialapp.chatservice.model.dto.notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinChatNotification extends Notification{
    private String id;
    private String username;
}
