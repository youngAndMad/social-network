package socialapp.chatservice.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import socialapp.chatservice.model.entity.AppUser;
import socialapp.chatservice.model.entity.Chat;
import socialapp.chatservice.model.entity.Message;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class NotificationMapperTest {

    private NotificationMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(NotificationMapper.class);
    }

    @Test
    void fromPrivateMessage_withValidInput_returnsMessageNotification() {
        var message = new Message();
        message.setContent("Test message");
        var sender = new AppUser();
        sender.setPreferredUsername("TestUser");
        message.setSender(sender);

        var chat = new Chat();
        chat.setName("Test Chat");
        chat.setId(UUID.randomUUID().toString());

        var notification = mapper.fromPrivateMessage(message, chat);

        assertNotNull(notification);
        assertEquals(chat.getName(), notification.getChatName());
        assertEquals(message.getSender().getPreferredUsername(), notification.getSenderName());
        assertEquals(message.getContent(), notification.getMessage());
        assertEquals(chat.getId(), notification.getChatId());
    }

    @Test
    void createLeaveChatNotification_withValidInput_returnsLeaveChatNotification() {
        var appUser = new AppUser();
        appUser.setPreferredUsername("TestUser");

        var chat = new Chat();
        chat.setName("Test Chat");
        chat.setId(UUID.randomUUID().toString());

        var notification = mapper.createLeaveChatNotification(appUser, chat);

        assertNotNull(notification);
        assertEquals(chat.getName(), notification.getChatName());
        assertEquals(chat.getId(), notification.getChatId());
        assertEquals(appUser.getPreferredUsername(), notification.getUsername());
    }

    @Test
    void createLeaveChatNotification_withNullInput_returnsNull() {
        var notification = mapper.createLeaveChatNotification(null, null);
        assertNull(notification);
    }

    @Test
    void fromPrivateMessage_withNullInput_returnsNull() {
        var notification = mapper.fromPrivateMessage(null, null);
        assertNull(notification);
    }

}