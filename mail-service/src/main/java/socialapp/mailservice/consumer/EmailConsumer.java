package socialapp.mailservice.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import socialapp.mailservice.model.dto.EmailMessageDto;
import socialapp.mailservice.model.enums.MailMessageType;
import socialapp.mailservice.service.MailService;

@Component
@Slf4j
@RequiredArgsConstructor
public class EmailConsumer {

    private final MailService mailService;

    /**
     * RabbitMQ message listener method that consumes messages from the "newsletter" queue.
     * The method processes an incoming {@code EmailMessageDto} and sends it as a newsletter email
     * using the {@code mailService} with the message type set to {@code MailMessageType.NEWSLETTER}.
     *
     * @param message The incoming {@code EmailMessageDto} representing the newsletter message.
     * @see RabbitListener
     * @see EmailMessageDto
     * @see MailService
     * @see MailMessageType
     */
    @RabbitListener(queues = "${spring.rabbitmq.queues.newsletter.name}")
    void consumeNewsLetter(EmailMessageDto message) {
        log.info("Consuming email: {}", message);
        mailService.send(message, MailMessageType.NEWSLETTER);
    }

}
