package socialapp.chatservice.web.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import socialapp.chatservice.common.annotation.BearerToken;
import socialapp.chatservice.common.utils.AuthenticationConvertUtils;
import socialapp.chatservice.service.MessageService;
import socialapp.chatservice.service.UserStatusService;

import static socialapp.chatservice.common.AppConstants.USER_NOTIFICATION_DESTINATION;
import static socialapp.chatservice.common.AppConstants.X_AUTHORIZATION;

@Controller
@RequiredArgsConstructor
public class UserStatusHandler {

    private final UserStatusService userStatusService;
    private final MessageService messageService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final AuthenticationConvertUtils authenticationConvertUtils;

    @MessageMapping("/user-status/start-session")
    void startSession(
            @Header(X_AUTHORIZATION) @BearerToken String authToken
    ) {
        var appUser = authenticationConvertUtils.extractFromBearer(authToken);

        userStatusService.setUserOnline(appUser);

        var userNotifications = messageService.checkMessages(appUser);

        userNotifications.forEach(notification -> {
            simpMessagingTemplate.convertAndSendToUser(
                    appUser.getEmail(),
                    USER_NOTIFICATION_DESTINATION,
                    notification)
            ;
        });
    }

    @MessageMapping("/user-status/close-session")
    void closeSession(@Header(X_AUTHORIZATION) @BearerToken String authToken) {
        var appUser = authenticationConvertUtils.extractFromBearer(authToken);
        userStatusService.setUserOffline(appUser);
    }


}
