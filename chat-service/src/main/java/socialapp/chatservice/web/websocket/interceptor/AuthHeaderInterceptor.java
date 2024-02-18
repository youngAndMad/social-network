package socialapp.chatservice.web.websocket.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.util.MultiValueMap;
import socialapp.chatservice.common.context.UserContextHolder;
import socialapp.chatservice.common.utils.AuthenticationConvertUtils;

import java.util.ArrayList;

import static socialapp.chatservice.common.AppConstants.X_AUTHORIZATION;

@RequiredArgsConstructor
@Slf4j
public class AuthHeaderInterceptor implements ChannelInterceptor {

    private final AuthenticationConvertUtils authenticationConvertUtils;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        var headers = message.getHeaders();
        var multiValueMap = headers.get(StompHeaderAccessor.NATIVE_HEADERS, MultiValueMap.class);

        try {
            if (multiValueMap != null && multiValueMap.containsKey(X_AUTHORIZATION)) {
                var authorization = (ArrayList<String>) multiValueMap.get(X_AUTHORIZATION);
                var appUser = authenticationConvertUtils.extractFromBearer(authorization.get(0));

                UserContextHolder.setCurrentUser(appUser);
            }
            return message;
        }catch (Exception e){
            log.error("Error occurred while extracting user from jwt", e);
            throw e;
        }
    }
}
