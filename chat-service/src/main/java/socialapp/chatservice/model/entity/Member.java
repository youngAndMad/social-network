package socialapp.chatservice.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import socialapp.chatservice.model.enums.ChatRole;

import java.time.LocalDateTime;

@Getter
@Setter
public class Member {
    private AppUser appUser;
    private ChatRole role;
    private LocalDateTime enteredAt;
    private String lastSeenMessageId;
}
