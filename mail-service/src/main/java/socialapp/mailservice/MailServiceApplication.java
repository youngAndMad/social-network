package socialapp.mailservice;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import socialapp.mailservice.model.dto.EmailMessageDto;
import socialapp.mailservice.model.enums.MailMessageType;
import socialapp.mailservice.service.MailService;

@SpringBootApplication
public class MailServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailServiceApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(MailService mailService){
        return new ApplicationRunner() {
            @Override
            public void run(ApplicationArguments args) throws Exception {
                mailService.send(new EmailMessageDto(
                        "beibitsatov98@gmail.com","hello"
                ), MailMessageType.NEWSLETTER);
            }
        };
    }
}
