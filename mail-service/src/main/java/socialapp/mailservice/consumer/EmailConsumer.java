package socialapp.mailservice.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


//    @KafkaListener(topics = {"${spring.kafka.queues.news-letter}"}, groupId = "")
    void consumeNewsLetter(EmailMessageDto message) {
        log.info("Consuming email: {}", message);
        mailService.send(message, MailMessageType.NEWSLETTER);
    }

}
