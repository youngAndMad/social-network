package socialapp.chatservice.common.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import socialapp.chatservice.common.context.UserContextHolder;
import socialapp.chatservice.common.utils.AuthenticationConvertUtils;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class FetchUserContextAspect {

    private final AuthenticationConvertUtils authenticationConvertUtils;

    @Pointcut("@annotation(socialapp.chatservice.common.annotation.FetchUserContext)")
    public void fetchUserContext() {
    }

    @Before("fetchUserContext()")
    public void before() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            var appUser = authenticationConvertUtils.extractAppUser(authentication);
            UserContextHolder.setCurrentUser(appUser);
            log.info("User context set for: {}", appUser);
        }else {
            log.info("Authentication from SecurityContextHolder is null, skipping setting user context");
        }

    }

    @After("fetchUserContext()")
    public void afterReturning() {
        log.info("Clearing user context");
        UserContextHolder.clear();
    }

}
