package socialapp.mailservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.MessageConverter;


/**
 * Configuration class for RabbitMQ integration.
 * Enables RabbitMQ functionality and configures message converters.
 *
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

    @Value("${spring.rabbitmq.queues.news-letter.name}")
    private String newsletterQueue;
    @Value("${spring.rabbitmq.queues.news-letter.exchange}")
    private String newsLetterExchange;
    @Value("${spring.rabbitmq.queues.news-letter.routing-key}")
    private String newsLetterRoutingKey;

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


    /**
     * Represents a Spring bean for creating a queue named "newsLetterQueue" with durability enabled.
     *
     * @return A new instance of the "newsLetterQueue" queue.
     */
    @Bean
    Queue newsLetterQueue() {
        return new Queue(newsletterQueue, true);
    }


    /**
     * Represents a Spring bean for creating a direct exchange named "newsLetterExchange" with durability enabled.
     *
     * @return A new instance of the "newsLetterExchange" direct exchange.
     */
    @Bean
    Exchange newsLetterExchange() {
        return ExchangeBuilder
                .directExchange(newsLetterExchange)
                .durable(true)
                .build();
    }


    /**
     * Represents a Spring bean for creating a binding between the "newsLetterQueue" and "newsLetterExchange"
     * with the specified routing key ("newsLetterRoutingKey").
     *
     * @param queue    The "newsLetterQueue" queue instance.
     * @param exchange The "newsLetterExchange" exchange instance.
     * @return A new binding between the queue and exchange with the specified routing key and no additional arguments.
     */
    @Bean
    Binding notificationFriendshipBinding(
            @Qualifier("newsLetterQueue") Queue queue,
            @Qualifier("newsLetterExchange") Exchange exchange
    ) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(newsLetterRoutingKey)
                .noargs();
    }


}
