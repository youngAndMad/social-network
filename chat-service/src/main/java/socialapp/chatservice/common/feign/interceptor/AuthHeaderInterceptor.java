package socialapp.chatservice.common.feign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import socialapp.chatservice.common.context.TokenContextHolder;

import static socialapp.chatservice.common.AppConstants.AUTHORIZATION;
import static socialapp.chatservice.common.AppConstants.BEARER;

@Component
public class AuthHeaderInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (!TokenContextHolder.isEmpty()) {
            return;
        }
        requestTemplate.header(AUTHORIZATION, BEARER.concat(TokenContextHolder.get()));
    }
}
