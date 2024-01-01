package socialapp.mailservice.consumer.advice;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpIllegalStateException;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

@Component
@Slf4j
public class RabbitErrorHandler implements ErrorHandler {

    @PostConstruct
    void init() {
        log.debug("RabbitErrorHandler initialized");
    }

    @Override
    public void handleError(Throwable t) {
        log.error("RabbitMQ error: " + t.getMessage());
        System.out.println("RabbitMQ error: " + t.getMessage());

        if (t.getCause() instanceof MailException) {
            log.error("PLEASE CHECK CURRENT PROBLEM WITH MAIL SERVICE");
            throw new AmqpIllegalStateException("PLEASE CHECK CURRENT PROBLEM WITH MAIL SERVICE", t);
        }
    }

}
