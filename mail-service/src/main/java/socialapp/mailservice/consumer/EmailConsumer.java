package socialapp.mailservice.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import socialapp.mailservice.model.dto.EmailMessageDto;
import socialapp.mailservice.model.enums.MailMessageType;
import socialapp.mailservice.service.MailService;

@Component
@Slf4j
@RequiredArgsConstructor
public class EmailConsumer {

    private final MailService mailService;


    @KafkaListener(topics = {"${spring.rabbitmq.queues.news-letter-name}"}, groupId = "")
    public void consumeNewsLetter(EmailMessageDto message) {
        log.info("Consuming email: {}", message);
        mailService.send(message, MailMessageType.NEWSLETTER);
    }

    @KafkaListener(topics = {"${spring.rabbitmq.queues.email-verification-name}"}, groupId = "")
    public void consumeEmailVerification(EmailMessageDto message){
        log.info("Consuming email: {}", message);
        mailService.send(message,MailMessageType.EMAIL_VERIFICATION);
    }

    @KafkaListener(topics = {"${spring.rabbitmq.queues.reset-password-name}"}, groupId = "")
    public void consumeResetPassword(EmailMessageDto message){
        log.info("Consuming email: {}", message);
        mailService.send(message,MailMessageType.RESET_PASSWORD);
    }



}
