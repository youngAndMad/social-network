package socialapp.chatservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import socialapp.chatservice.config.MongoTestContainerConfig;
import socialapp.chatservice.config.RedisTestContainerConfig;

@SpringBootTest
@Import({RedisTestContainerConfig.class, MongoTestContainerConfig.class})
class ChatServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
