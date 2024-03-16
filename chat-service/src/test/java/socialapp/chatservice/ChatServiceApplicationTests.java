package socialapp.chatservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import socialapp.chatservice.config.TestContainerConfig;

@SpringBootTest
@Import(TestContainerConfig.class)
class ChatServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
