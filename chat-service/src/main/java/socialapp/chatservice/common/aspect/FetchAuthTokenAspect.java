package socialapp.chatservice.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import socialapp.chatservice.common.context.TokenContextHolder;

@Aspect
@Slf4j
@Component
public class FetchAuthTokenAspect {

    @Pointcut("@annotation(socialapp.chatservice.common.annotation.FetchAuthToken)")
    public void fetchAuthToken() {
    }

    @Before("fetchAuthToken()")
    public void before() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
            var jwtToken = jwt.getTokenValue();
            TokenContextHolder.set(jwtToken);
        }
    }

    @After("fetchAuthToken()")
    public void afterReturning() {
        TokenContextHolder.clear();
    }
}
