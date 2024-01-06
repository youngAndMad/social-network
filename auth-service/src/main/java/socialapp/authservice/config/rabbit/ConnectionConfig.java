package socialapp.authservice.config.rabbit;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectionConfig {

    /**
     * Configures a Jackson2JsonMessageConverter as the default message converter for RabbitMQ.
     *
     * @return Jackson2JsonMessageConverter instance
     */
    @Bean
    MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
