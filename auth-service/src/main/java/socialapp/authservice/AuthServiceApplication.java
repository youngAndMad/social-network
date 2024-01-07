package socialapp.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import socialapp.authservice.config.OAuth2ClientProperties;
import socialapp.authservice.config.ServerProperties;
import socialapp.authservice.config.rabbit.RabbitProperties;

@SpringBootApplication
@EnableConfigurationProperties(
        {RabbitProperties.class, OAuth2ClientProperties.class, ServerProperties.class})
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }
}
