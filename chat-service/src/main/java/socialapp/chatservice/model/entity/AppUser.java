package socialapp.chatservice.model.entity;

import lombok.*;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@AllArgsConstructor
@ToString
@FieldNameConstants
@NoArgsConstructor
public class AppUser {
    private String givenName;
    private String email;
    private String familyName;
    private Boolean emailVerified;
    private String preferredUsername;
}
