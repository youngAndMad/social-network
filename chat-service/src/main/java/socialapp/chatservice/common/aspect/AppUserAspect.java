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
import static socialapp.chatservice.common.AuthenticationConvertUtils.extractAppUser;

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

}
