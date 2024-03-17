package socialapp.authservice.config.rabbit;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "spring.rabbitmq.queues")
public class RabbitProperties {
    private String emailVerificationName;
    private String emailVerificationExchange;
    private String emailVerificationRoutingKey;
    private String resetPasswordName;
    private String resetPasswordExchange;
    private String resetPasswordRoutingKey;
}
