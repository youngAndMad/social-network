package socialapp.chatservice.model.dto;

import jakarta.validation.constraints.NotEmpty;

public record PrivateMessageRequest(
    @NotEmpty String message,
    @NotEmpty String chatId
){
}
