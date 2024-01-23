package socialapp.chatservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;
import socialapp.chatservice.service.UserStatusService;

import static socialapp.chatservice.common.AppConstants.PROFILE_HEADER;
import static socialapp.chatservice.common.utils.ConvertUtils.convertToAppUser;

@RestController
@RequiredArgsConstructor
public class UserStatusController {

    private final UserStatusService userStatusService;

    @MessageMapping("start_session")
    void startSession(@Header(PROFILE_HEADER) String profile) {
        var appUser = convertToAppUser(profile);
        userStatusService.setUserOnline(appUser.getEmail());
    }

    @MessageMapping("close_session")
    void closeSession(@Header(PROFILE_HEADER) String profile) {
        var appUser = convertToAppUser(profile);
        userStatusService.setUserOffline(appUser.getEmail());
    }
}
