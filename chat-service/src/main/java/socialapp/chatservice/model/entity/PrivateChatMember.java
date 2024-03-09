package socialapp.chatservice.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class PrivateChatMember extends ChatMember{
    @Id
    private String id;
}
