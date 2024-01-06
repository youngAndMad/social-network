package socialapp.mailservice.config.rabbit;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.rabbitmq")
@Data
@Slf4j
public class RabbitProperties {

    private Headers headers;
    private Queues queues;
    private String username;
    private String password;
    private String host;

    @PostConstruct
    void init() {
        log.debug("RabbitTopics initialized {}", this);
    }

    @Data
    public static class Headers {
        private String xDeadLetterExchange;
        private String xDeadLetterRoutingKey;
    }

    @Data
    public static class Queues {
        private String newsLetterName;
        private String newsLetterExchange;
        private String newsLetterRoutingKey;
        private String emailVerificationName;
        private String emailVerificationExchange;
        private String emailVerificationRoutingKey;
    }

}
