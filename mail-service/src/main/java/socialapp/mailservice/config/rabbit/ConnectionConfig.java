package socialapp.mailservice.config.rabbit;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import socialapp.mailservice.consumer.advice.RabbitErrorHandler;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(RabbitProperties.class)
public class ConnectionConfig {

    private final RabbitProperties rabbitProperties;

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
        var fct = new CachingConnectionFactory(rabbitProperties.getHost());

        fct.setUsername(rabbitProperties.getUsername());
        fct.setPassword(rabbitProperties.getPassword());

        return fct;
    }

    /**
     * Configures a {@code SimpleRabbitListenerContainerFactory} bean for handling RabbitMQ message listeners.
     * This factory is responsible for creating {@code SimpleRabbitListenerContainer} instances used to
     * asynchronously consume messages from RabbitMQ queues.
     *
     * @param connectionFactory       The {@code ConnectionFactory} used for creating connections to RabbitMQ.
     * @param configurer              The {@code SimpleRabbitListenerContainerFactoryConfigurer} for configuring the factory.
     * @param errorHandler            The custom {@code RabbitErrorHandler} for handling errors during message consumption.
     * @return A configured {@code SimpleRabbitListenerContainerFactory} bean.
     * @see SimpleRabbitListenerContainerFactory
     * @see SimpleRabbitListenerContainerFactoryConfigurer
     * @see RabbitErrorHandler
     */
    @Bean
    SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            RabbitErrorHandler errorHandler
    ) {
        var factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setErrorHandler(errorHandler);
        return factory;
    }

    @Bean
    RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory,
            MessageConverter jsonMessageConverter
    ) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter);
        return rabbitTemplate;
    }


}
