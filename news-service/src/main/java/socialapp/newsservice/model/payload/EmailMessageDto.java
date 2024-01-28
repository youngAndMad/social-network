package socialapp.newsservice.model.payload;

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
