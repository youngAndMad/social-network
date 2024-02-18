package socialapp.chatservice.model.dto;

public record PrivateMessageRequest(
    String message,
    String chatId
){
}
