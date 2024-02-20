package socialapp.channelservice.model.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class AppUser {
    private String givenName;
    private String email;
    private String familyName;
    private Boolean emailVerified;
    private String preferredUsername;
}