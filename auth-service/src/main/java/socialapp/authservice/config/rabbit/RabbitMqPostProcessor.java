package socialapp.authservice.config.rabbit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class RabbitMqPostProcessor {


    @Bean
    public ApplicationRunner applicationRunner(
            RabbitTemplate rabbitTemplate
    ) {
        return args -> {
            rabbitTemplate.addBeforePublishPostProcessors(
                    message -> {
                        log.debug("rabbit mq message sending attempt = {}", message.toString());
                        return message;
                    }
            );
        };
    }

}
