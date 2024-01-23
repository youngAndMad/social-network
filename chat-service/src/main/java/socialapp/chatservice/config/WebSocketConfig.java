package socialapp.chatservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.core.Authentication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import socialapp.chatservice.common.UserContextHolder;


import static socialapp.chatservice.common.aspect.AppUserAspect.extractAppUser;

@Configuration
@EnableWebSocketMessageBroker
@EnableWebSocket
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @EventListener
    void handleWebSocketConnectListener(SessionConnectEvent event) {
        UserContextHolder.setCurrentUser(extractAppUser((Authentication) event.getUser()));
    }

    @EventListener
    void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        UserContextHolder.clear();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/socialapp")
                .setAllowedOriginPatterns("")
                .withSockJS();
    }
}
