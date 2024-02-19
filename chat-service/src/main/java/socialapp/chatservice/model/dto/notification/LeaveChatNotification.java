package socialapp.chatservice.model.dto.notification;

public record LeaveChatNotification (
        String chatId,
        String chatName,
        String username
){
}

