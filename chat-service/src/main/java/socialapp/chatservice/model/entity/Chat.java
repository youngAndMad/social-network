package socialapp.chatservice.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import socialapp.chatservice.model.enums.ChatType;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@FieldNameConstants
public class Chat {

    @Id
    private String id;
    private ChatType type;
    private LocalDateTime createdAt;
    private String name;
    private Set<ChatMember> members;
    private Set<Message> messages;
}
