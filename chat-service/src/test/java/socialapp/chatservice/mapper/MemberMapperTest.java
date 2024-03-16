package socialapp.chatservice.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import socialapp.chatservice.model.entity.AppUser;

import static org.junit.jupiter.api.Assertions.*;

class MemberMapperTest {

    private MemberMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(MemberMapper.class);
    }

    @Test
    void toPrivateChatMember_withValidInput_returnsPrivateChatMember() {
        var appUser = new AppUser();
        appUser.setEmail("kkraken2005@gmail.com");
        appUser.setPreferredUsername("TestUser");

        var member = mapper.toPrivateChatMember(appUser);

        assertNotNull(member);
        assertEquals(appUser, member.getAppUser());
    }

    @Test
    void toPrivateChatMember_withNullInput_returnsNull() {
        var member = mapper.toPrivateChatMember(null);
        assertNull(member);
    }
}