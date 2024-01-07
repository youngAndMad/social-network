package socialapp.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.CrossOrigin;
import socialapp.authservice.config.ServerProperties;
import socialapp.authservice.config.rabbit.RabbitProperties;

@SpringBootApplication
@EnableConfigurationProperties({RabbitProperties.class, ServerProperties.class})
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }
}
