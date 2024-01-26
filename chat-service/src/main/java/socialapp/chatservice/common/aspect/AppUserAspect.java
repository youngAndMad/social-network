package socialapp.chatservice.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import socialapp.chatservice.common.UserContextHolder;
import socialapp.chatservice.model.entity.AppUser;

import static socialapp.chatservice.common.AppConstants.*;

@Aspect
@Slf4j
@Component
public class AppUserAspect {

    @Pointcut("@annotation(socialapp.chatservice.common.annotation.FetchUserContext)")
    public void fetchUserContext() {
    }

    @Before("fetchUserContext()")
    public void before() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            AppUser appUser = extractAppUser(authentication);
            UserContextHolder.setCurrentUser(appUser);
        }
    }

    @After("fetchUserContext()")
    public void afterReturning() {
        UserContextHolder.clear();
    }

    public static AppUser extractAppUser(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {

            var givenName = jwt.getClaimAsString(GIVEN_NAME);
            var email = jwt.getClaimAsString(EMAIL);
            var emailVerified = jwt.getClaimAsBoolean(EMAIL_VERIFIED);
            var familyName = jwt.getClaimAsString(FAMILY_NAME);
            var preferredUsername = jwt.getClaimAsString(PREFERRED_USERNAME);

            log.info("extractAppUser username: {}", givenName);

            return new AppUser(givenName, email, familyName, emailVerified,preferredUsername);
        }
        log.warn("extractAppUser authentication is null or not instance of Jwt");
        return null;
    }


}
