package socialapp.mailservice.model.dto;

/**
 * Represents a data transfer object (DTO) for an email message.
 * This record encapsulates the email address and message content.
 * @author Daneker
 */
public record EmailMessageDto(
        /*
         * The email address to which the message will be sent.
         */
        String email,
        /*
         * The content of the email message.
         */
        String message
) {
}

