package socialapp.chatservice.model.dto.notification;


import java.time.LocalDateTime;

public record MessageNotification (
        String chatName,
        String senderName,
        LocalDateTime notificationTime,
        String message,
        String chatId
){
}
