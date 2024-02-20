package socialapp.channelservice.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import socialapp.channelservice.model.entity.AppUser;

import static socialapp.channelservice.common.AppConstants.*;

@UtilityClass
@Slf4j
public class AuthenticationConvertUtils {
    public static AppUser extractAppUser(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
            return extractAppUser(jwt);
        }
        log.warn("extractAppUser authentication is null or not instance of Jwt");
        return null;
    }

    public static AppUser extractAppUser(Jwt jwt){
        var givenName = jwt.getClaimAsString(GIVEN_NAME);
        var email = jwt.getClaimAsString(EMAIL);
        var emailVerified = jwt.getClaimAsBoolean(EMAIL_VERIFIED);
        var familyName = jwt.getClaimAsString(FAMILY_NAME);
        var preferredUsername = jwt.getClaimAsString(PREFERRED_USERNAME);

        log.info("extractAppUser username: {}", email);

        return new AppUser(givenName, email, familyName, emailVerified,preferredUsername);
    }
}
