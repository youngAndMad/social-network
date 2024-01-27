package socialapp.chatservice.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import socialapp.chatservice.model.enums.ChatType;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class Chat {

    @Id
    private String id;
    private ChatType type;
    private LocalDateTime createdAt;
    private Set<Member> members;
}
