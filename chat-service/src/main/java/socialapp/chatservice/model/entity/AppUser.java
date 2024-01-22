package socialapp.chatservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AppUser {
    private String username;
    private String email;
    private String familyName;
    private Boolean emailVerified;
}
