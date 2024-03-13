package socialapp.chatservice.model.dto.notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeaveChatNotification extends Notification{
    private String id;
    private String username;
}

