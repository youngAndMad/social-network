package socialapp.chatservice.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import socialapp.chatservice.common.UserContextHolder;
import socialapp.chatservice.model.entity.AppUser;

@Aspect
@Slf4j
@Component
public class AppUserAspect {


    @Before("execution(* socialapp.chatservice.controller.TestController.*(..))")
    public void before(JoinPoint joinPoint) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            AppUser appUser = extractAppUser(authentication);
            UserContextHolder.setCurrentUser(appUser);
        }
    }

    @After("execution(* socialapp.chatservice.controller.TestController.*(..))")
    public void afterReturning() {
        UserContextHolder.clear();
    }

    public static AppUser extractAppUser(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {

            var givenName = jwt.getClaimAsString("preferred_username");
            var email = jwt.getClaimAsString("email");
            var emailVerified = jwt.getClaimAsBoolean("email_verified");
            var familyName = jwt.getClaimAsString("family_name");
            var preferredUsername = jwt.getClaimAsString("preferred_username");

            log.info("extractAppUser username: {}", givenName);

            return new AppUser(givenName, email, familyName, emailVerified,preferredUsername);
        }
        log.warn("extractAppUser authentication is null or not instance of Jwt");
        return null;
    }


}
