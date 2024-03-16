package socialapp.chatservice.web.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import socialapp.chatservice.common.annotation.BearerToken;
import socialapp.chatservice.common.utils.AuthenticationConvertUtils;
import socialapp.chatservice.model.dto.PrivateMessageRequest;
import socialapp.chatservice.service.ChatService;
import socialapp.chatservice.service.MessageService;
import socialapp.chatservice.service.UserStatusService;

import static socialapp.chatservice.common.AppConstants.X_AUTHORIZATION;
import static socialapp.chatservice.common.utils.MemberExtractor.extractPrivateChatMember;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PrivateChatHandler {

    private final AuthenticationConvertUtils authenticationConvertUtils;
    private final MessageService messageService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatService chatService;
    private final UserStatusService userStatusService;

    @MessageMapping("private-chat-message")
    void sendMessage(@Header(X_AUTHORIZATION) @BearerToken String authToken,
                     @Payload @Validated PrivateMessageRequest messageRequest
    ) {
        var appUser = authenticationConvertUtils.extractFromBearer(authToken);
        log.info("User {} sent message: {}", appUser.getEmail(), messageRequest);

        var messageNotification = messageService.saveMessage(messageRequest, appUser);

        var privateChatMembers = chatService.getChatMembers(messageRequest.chatId());

        var secondMember = extractPrivateChatMember(privateChatMembers, appUser);

        if (userStatusService.isUserOnline(secondMember.getAppUser().getEmail()).online()) {
            log.info("User {} is online, sending message = {}",
                    secondMember.getAppUser().getEmail(),
                    messageNotification
            );
            simpMessagingTemplate.convertAndSendToUser(
                    secondMember.getAppUser().getEmail(),
                    "/queue/private-message",
                    messageNotification
            );
        }
    }

}
