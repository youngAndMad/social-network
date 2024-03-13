package socialapp.chatservice.model.dto;

import jakarta.validation.constraints.NotEmpty;

public record LeaveChatRequest(
        @NotEmpty String chatId
) {
}
