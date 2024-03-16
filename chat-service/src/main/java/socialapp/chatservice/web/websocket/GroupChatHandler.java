package socialapp.chatservice.web.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import socialapp.chatservice.common.annotation.BearerToken;
import socialapp.chatservice.common.utils.AuthenticationConvertUtils;
import socialapp.chatservice.model.dto.LeaveChatRequest;
import socialapp.chatservice.service.ChatService;
import socialapp.chatservice.service.NotificationService;
import socialapp.chatservice.service.UserStatusService;

import static socialapp.chatservice.common.AppConstants.X_AUTHORIZATION;

@Controller
@RequiredArgsConstructor
public class GroupChatHandler {

    private final ChatService chatService;
    private final AuthenticationConvertUtils authenticationConvertUtils;
    private final UserStatusService userStatusService;
    private final SimpMessagingTemplate webSocket;
    private final NotificationService notificationService;

    @MessageMapping("/leave-chat")
    void leaveChat(
            @Header(X_AUTHORIZATION) @BearerToken String authToken,
            @Validated LeaveChatRequest leaveChatRequest
    ){
        var currentUser = authenticationConvertUtils.extractFromBearer(authToken);
        var leaveChatNotification = chatService.leaveChat(currentUser, leaveChatRequest.chatId());

        var chatMembers = chatService.getChatMembers(leaveChatRequest.chatId());

        chatMembers.forEach(chatMember -> {
            if (userStatusService.isUserOnline(chatMember.getAppUser().getEmail()).online()) {
                webSocket.convertAndSendToUser(
                        chatMember.getAppUser().getEmail(),
                        "/queue/leave-chat",
                        leaveChatNotification
                );
            }else{
                notificationService.saveNotification(
                        leaveChatNotification,
                        chatMember.getAppUser()
                );
            }
        });


    };
}
