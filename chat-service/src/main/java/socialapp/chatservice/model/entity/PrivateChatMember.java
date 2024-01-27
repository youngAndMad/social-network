package socialapp.chatservice.model.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrivateChatMember implements ChatMember{
    private AppUser appUser;
    private String lastSeenMessageId;
}
