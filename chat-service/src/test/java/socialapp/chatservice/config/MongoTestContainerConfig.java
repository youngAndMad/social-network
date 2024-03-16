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
public class MongoTestContainerConfig {

    @Value("${spring.data.mongodb.port}")
    private Integer mongoPort;

    private GenericContainer<?> mongoContainer;

    @Bean
    GenericContainer<?> mongoContainer() {
        this.mongoContainer = new GenericContainer<>(DockerImageName.parse("mongo:latest"))
                .withExposedPorts(mongoPort, mongoPort);

        this.mongoContainer.start();

        log.info("Mongo container started on port: {}", this.mongoContainer.getMappedPort(mongoPort));

        return this.mongoContainer;
    }

    @PreDestroy
    public void destroy() {
        log.info("Stopping containers... [mongo]");
        this.mongoContainer.stop();
    }
}
