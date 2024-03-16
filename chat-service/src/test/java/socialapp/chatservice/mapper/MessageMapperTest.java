package socialapp.chatservice.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import socialapp.chatservice.model.dto.PrivateMessageRequest;
import socialapp.chatservice.model.entity.AppUser;


import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MessageMapperTest {

    private MessageMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(MessageMapper.class);
    }

    @Test
    void toMessage_withValidInput_returnsMessage() {
        var request = new PrivateMessageRequest(
                "Test message",
                UUID.randomUUID().toString()
        );

        var appUser = new AppUser();
        appUser.setPreferredUsername("TestUser");

        var message = mapper.toMessage(request, appUser);

        assertNotNull(message);
        assertEquals(request.message(), message.getContent());
        assertEquals(appUser, message.getSender());
        assertNull(message.getId()); // id should be null because we ignore it in mapping
    }

    @Test
    void toMessage_withNullInput_returnsNull() {
        var message = mapper.toMessage(null, null);
        assertNull(message);
    }
}