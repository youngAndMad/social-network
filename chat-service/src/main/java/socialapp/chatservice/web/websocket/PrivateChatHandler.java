package socialapp.chatservice.web.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import socialapp.chatservice.common.utils.AuthenticationConvertUtils;
import socialapp.chatservice.model.dto.PrivateMessageRequest;
import socialapp.chatservice.service.ChatService;
import socialapp.chatservice.service.UserStatusService;

import static socialapp.chatservice.common.AppConstants.X_AUTHORIZATION;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PrivateChatHandler {

    private final AuthenticationConvertUtils authenticationConvertUtils;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatService chatService;
    private final UserStatusService userStatusService;

    @MessageMapping("private-chat-message")
    void sendMessage(@Header(X_AUTHORIZATION) String authToken,
                     @Payload PrivateMessageRequest messageRequest
    ){
        var appUser = authenticationConvertUtils.extractFromBearer(authToken);
        log.info("User {} sent message: {}", appUser.getEmail(), messageRequest);


        // send message to the recipient
    }

}
