package socialapp.mailservice.config;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.MessageConverter;


/**
 * Configuration class for RabbitMQ integration.
 * Enables RabbitMQ functionality and configures message converters.
 * @author Daneker
 */
@Configuration
@EnableRabbit
public class RabbitConfig {

    @Value("${spring.rabbitmq.username}")
    private String rabbitUsername;

    @Value("${spring.rabbitmq.password}")
    private String rabbitPassword;

    @Value("${spring.rabbitmq.host}")
    private String rabbitHost;

    /**
     * Configures a Jackson2JsonMessageConverter as the default message converter for RabbitMQ.
     *
     * @return Jackson2JsonMessageConverter instance
     */
    @Bean
    MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * Configures a RabbitMQ connection factory with the specified host, username, and password.
     *
     * @return ConnectionFactory instance
     */
    @Bean
    ConnectionFactory connectionFactory() {
        var connectionFactory = new CachingConnectionFactory(rabbitHost);

        connectionFactory.setUsername(rabbitUsername);
        connectionFactory.setPassword(rabbitPassword);

        return connectionFactory;
    }

}
