package socialapp.chatservice.config;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
@Slf4j
public class RedisTestContainerConfig {

    @Value("${spring.data.redis.port}")
    private Integer redisPort;
    private GenericContainer<?> redisContainer;

    @Bean
    GenericContainer<?> redisContainer() {
        redisContainer = new GenericContainer<>(DockerImageName.parse("redis:latest"))
                .withExposedPorts(redisPort);

        redisContainer.start();
        return redisContainer;
    }

    @PreDestroy
    public void destroy() {
        log.info("Stopping containers... [redis]");
        redisContainer.stop();
    }

}
