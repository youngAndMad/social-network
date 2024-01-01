package socialapp.mailservice.service;

import socialapp.mailservice.model.dto.EmailMessageDto;
import socialapp.mailservice.model.enums.MailMessageType;

/**
 * Interface defining the contract for sending email messages.
 * @author Daneker
 */
public interface MailService {

    /**
     * Sends an email message based on the provided {@code EmailMessageDto} and {@code MailMessageType}.
     *
     * @param message The {@code EmailMessageDto} representing the content of the email.
     * @param type    The {@code MailMessageType} indicating the type or purpose of the email.
     * @see EmailMessageDto
     * @see MailMessageType
     */
    void send(EmailMessageDto message, MailMessageType type);
}
