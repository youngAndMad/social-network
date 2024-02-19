package socialapp.chatservice.common.feign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import socialapp.chatservice.common.context.TokenContextHolder;

import static socialapp.chatservice.common.AppConstants.AUTHORIZATION;
import static socialapp.chatservice.common.AppConstants.BEARER;

@Component
@Slf4j
public class AuthHeaderInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (TokenContextHolder.isEmpty()) {
            log.warn("token context holder is empty, will skip adding auth header");
            return;
        }
        requestTemplate.header(AUTHORIZATION, BEARER.concat(TokenContextHolder.get()));
    }
}
