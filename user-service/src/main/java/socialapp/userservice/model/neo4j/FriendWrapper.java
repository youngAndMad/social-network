package socialapp.userservice.model.neo4j;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node("FRIEND")
@Getter
@Setter
public class FriendWrapper {
    @Id
    private String id;
    @Relationship(type = "FRIEND_OF")
    private Long sender;
    @Relationship(type = "FRIEND_OF")
    private Long receiver;
}
