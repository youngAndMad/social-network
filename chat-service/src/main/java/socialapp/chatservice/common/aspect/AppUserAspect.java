package socialapp.chatservice.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import socialapp.chatservice.common.UserContextHolder;
import socialapp.chatservice.model.entity.AppUser;

@Aspect
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
    public void afterReturning(JoinPoint joinPoint) {
        UserContextHolder.clear();
    }

    public static AppUser extractAppUser(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {

            var username = jwt.getClaimAsString("preferred_username");
            var email = jwt.getClaimAsString("email");
            var emailVerified = jwt.getClaimAsBoolean("email_verified");
            var familyName = jwt.getClaimAsString("family_name");

            return new AppUser(username, email, familyName, emailVerified);
        }

        return null;
    }


}
