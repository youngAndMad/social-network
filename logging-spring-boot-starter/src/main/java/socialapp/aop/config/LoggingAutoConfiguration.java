package socialapp.aop.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import socialapp.aop.aspects.LoggingMethodExecutionAspect;
import socialapp.aop.aspects.LoggingTimeExecutionAspect;

@Configuration
@EnableConfigurationProperties(LoggingProperties.class)
@ConditionalOnProperty(prefix = "app.common.logging", name = "enabled", havingValue = "true")
@EnableAspectJAutoProxy
public class LoggingAutoConfiguration {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PostConstruct
    void init() {
        log.info("LoggingAutoConfiguration initialized");
    }

    @Bean
    LoggingMethodExecutionAspect loggingMethodExecutionAspect() {
        log.info("LoggingMethodExecutionAspect bean start to create.");
        return new LoggingMethodExecutionAspect();
    }

    @Bean
    LoggingTimeExecutionAspect loggingTimeExecutionAspect() {
        log.info("LoggingTimeExecutionAspect bean start to create.");
        return new LoggingTimeExecutionAspect();
    }
}

