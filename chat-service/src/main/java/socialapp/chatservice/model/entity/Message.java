package socialapp.chatservice.model.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {

    private String id;
    private String content;
    private String sender;
}
