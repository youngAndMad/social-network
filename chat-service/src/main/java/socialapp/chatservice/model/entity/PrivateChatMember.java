package socialapp.chatservice.model.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrivateChatMember {
    private AppUser appUser;
    private String lastSeenMessageId;
}
