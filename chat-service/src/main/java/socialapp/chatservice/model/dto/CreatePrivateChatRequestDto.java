package socialapp.chatservice.model.dto;

import jakarta.validation.constraints.Email;

public record CreatePrivateChatRequestDto(
       @Email String receiverEmail
) {
}
