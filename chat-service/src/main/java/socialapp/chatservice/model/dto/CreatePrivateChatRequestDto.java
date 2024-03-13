package socialapp.chatservice.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record CreatePrivateChatRequestDto(
       @Email @NotEmpty String receiverEmail
) {
}
