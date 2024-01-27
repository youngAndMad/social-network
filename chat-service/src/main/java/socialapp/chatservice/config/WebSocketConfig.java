package socialapp.chatservice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import socialapp.chatservice.common.context.UserContextHolder;

import java.util.ArrayList;

import static socialapp.chatservice.common.AppConstants.X_AUTHORIZATION;
import static socialapp.chatservice.common.utils.AuthenticationConvertUtils.extractAppUser;


@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
@Slf4j
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtDecoder jwtDecoder;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/social-app")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                var headers = message.getHeaders();
                var multiValueMap = headers.get(StompHeaderAccessor.NATIVE_HEADERS, MultiValueMap.class);

                try {
                    if (multiValueMap != null && multiValueMap.containsKey(X_AUTHORIZATION)) {
                        var authorization = (ArrayList<String>) multiValueMap.get(X_AUTHORIZATION);
                        var jwt = jwtDecoder.decode(authorization.get(0));
                        var appUser = extractAppUser(jwt);

                        UserContextHolder.setCurrentUser(appUser);
                    }
                    return message;
                }catch (Exception e){
                    log.error("Error occurred while extracting user from jwt", e);
                    throw e;
                }
            }
        });
    }
}
