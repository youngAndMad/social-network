package socialapp.chatservice.model.dto.notification;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public abstract class Notification implements Serializable {
    private LocalDateTime notificationTime;
    private String chatName;
    private String chatId;
}
