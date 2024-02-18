package socialapp.chatservice.common.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import socialapp.chatservice.model.entity.AppUser;

import static socialapp.chatservice.common.AppConstants.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationConvertUtils {

    private final JwtDecoder jwtDecoder;

    public AppUser extractAppUser(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
            return extractAppUser(jwt);
        }
        log.warn("extractAppUser authentication is null or not instance of Jwt");
        return null;
    }

    public AppUser extractAppUser(Jwt jwt){
        var givenName = jwt.getClaimAsString(GIVEN_NAME);
        var email = jwt.getClaimAsString(EMAIL);
        var emailVerified = jwt.getClaimAsBoolean(EMAIL_VERIFIED);
        var familyName = jwt.getClaimAsString(FAMILY_NAME);
        var preferredUsername = jwt.getClaimAsString(PREFERRED_USERNAME);

        log.info("extractAppUser username: {}", email);

        return new AppUser(givenName, email, familyName, emailVerified,preferredUsername);
    }

    public AppUser extractFromBearer(String bearerToken){
        var jwt = jwtDecoder.decode(bearerToken.substring(BEARER.length()));
        return extractAppUser(jwt);
    }
}