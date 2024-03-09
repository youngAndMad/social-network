package socialapp.chatservice.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import socialapp.chatservice.model.enums.ChatType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@FieldNameConstants
public class Chat {
    @Id
    private String id;
    private String name;
    private ChatType type;
    private LocalDateTime createdAt;
    private Set<ChatMember> members = new HashSet<>();
    private Set<Message> messages = new HashSet<>();
}
