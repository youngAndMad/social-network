package socialapp.mailservice.config.rabbit;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Configuration class for RabbitMQ integration.
 * Enables RabbitMQ functionality and configures message converters.
 *
 * @author Daneker
 */
@Configuration
@EnableRabbit
@RequiredArgsConstructor
public class TopicsConfig {


    private final RabbitProperties rabbitProperties;

    /**
     * Represents a Spring bean for creating a queue named "newsLetterQueue" with durability enabled.
     *
     * @return A new instance of the "newsLetterQueue" queue.
     */
    @Bean
    Queue newsLetterQueue() {
        return new Queue(rabbitProperties.getQueues().getNewsLetterName(), true);
    }

    @Bean
    Queue emailVerificationQueue() {
        return new Queue(rabbitProperties.getQueues().getEmailVerificationName(), true);
    }

    @Bean
    Queue resetPasswordQueue(){return new Queue(rabbitProperties.getQueues().getResetPasswordName(),true);}

    /**
     * Represents a Spring bean for creating a direct exchange named "newsLetterExchange" with durability enabled.
     *
     * @return A new instance of the "newsLetterExchange" direct exchange.
     */
    @Bean
    Exchange newsLetterExchange() {
        return ExchangeBuilder
                .directExchange(rabbitProperties.getQueues().getNewsLetterExchange())
                .durable(true)
                .build();
    }

    @Bean
    Exchange emailVerificationExchange() {
        return ExchangeBuilder
                .directExchange(rabbitProperties.getQueues().getEmailVerificationExchange())
                .durable(true)
                .build();
    }

    @Bean
    Exchange resetPasswordExchange(){
        return ExchangeBuilder
                .directExchange(rabbitProperties.getQueues().getResetPasswordExchange())
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
    Binding newsLetterBinding(
            @Qualifier("newsLetterQueue") Queue queue,
            @Qualifier("newsLetterExchange") Exchange exchange
    ) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(rabbitProperties.getQueues().getNewsLetterRoutingKey())
                .noargs();
    }

    @Bean
    Binding emailVerificationBinding(
            @Qualifier("emailVerificationQueue") Queue queue,
            @Qualifier("emailVerificationExchange") Exchange exchange
    ) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(rabbitProperties.getQueues().getEmailVerificationRoutingKey())
                .noargs();
    }

    @Bean
    Binding resetPasswordBinding(
            @Qualifier("resetPasswordQueue") Queue queue,
            @Qualifier("resetPasswordExchange") Exchange exchange
    ) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(rabbitProperties.getQueues().getResetPasswordRoutingKey())
                .noargs();
    }
}
