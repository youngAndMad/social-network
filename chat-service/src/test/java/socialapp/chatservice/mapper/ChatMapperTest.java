package socialapp.chatservice.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import socialapp.chatservice.model.entity.ChatMember;
import socialapp.chatservice.model.enums.ChatType;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class ChatMapperTest {

    private ChatMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(ChatMapper.class);
    }

    @Test
    void toModel_withValidInput_returnsChat() {
        var members = new HashSet<ChatMember>();
        var type = ChatType.GROUP;
        var name = "Test Chat";

        var chat = mapper.toModel(members, type, name);

        assertNotNull(chat);
        assertEquals(members, chat.getMembers());
        assertEquals(type, chat.getType());
        assertEquals(name, chat.getName());

        assertNull(chat.getId()); // id should be null because we ignore it in mapping
    }

    /**
     * #ChatMapper.toModel should return null when input is null,
     * because this mapper uses global mapper configuration to ignore null values
     * and return null when input is null.
     *
     * @see socialapp.chatservice.mapper.GlobalMapperConfig
     */
    @Test
    void toModel_withNullInput_returnsNull() {
        var chat = mapper.toModel(null, null, null);
        assertNull(chat);
    }
}