package socialapp.chatservice.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

/**
 * This is a marker class for chat members.
 * Classes extending this interface can be used as chat members.
 */
@Getter
@Setter
@FieldNameConstants
public abstract class ChatMember {
    private AppUser appUser;
    private String lastSeenMessageId;
}