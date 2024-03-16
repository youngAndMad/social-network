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
public class TestContainerConfig {

    @Value("${spring.data.redis.port}")
    private Integer redisPort;

    @Value("${spring.data.mongodb.port}")
    private Integer mongoPort;

    private GenericContainer<?> mongoContainer;
    private GenericContainer<?> redisContainer;


    @Bean
    GenericContainer<?> mongoContainer() {
        mongoContainer = new GenericContainer<>(DockerImageName.parse("mongo:latest"))
                .withExposedPorts(mongoPort, mongoPort);

        mongoContainer.start();

        log.info("Mongo container started on port: {}", mongoContainer.getMappedPort(mongoPort));

        return mongoContainer;
    }

    @Bean
    GenericContainer<?> redisContainer() {
        redisContainer = new GenericContainer<>(DockerImageName.parse("redis:latest"))
                .withExposedPorts(redisPort, redisPort);

        redisContainer.start();

        log.info("Redis container started on port: {}", redisContainer.getMappedPort(redisPort));
        return redisContainer;
    }

    @PreDestroy
    public void destroy() {
        log.info("Stopping containers... [redis, mongo]");
        mongoContainer.stop();
        redisContainer.stop();
    }


}
