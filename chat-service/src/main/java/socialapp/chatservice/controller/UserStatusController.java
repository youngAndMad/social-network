package socialapp.chatservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import socialapp.chatservice.service.UserStatusService;

import java.security.Principal;

import static socialapp.chatservice.common.AppConstants.PROFILE_HEADER;
import static socialapp.chatservice.common.utils.ConvertUtils.convertToAppUser;

@RestController
@RequiredArgsConstructor
@RequestMapping("ws")
public class UserStatusController {

    private final UserStatusService userStatusService;

    @MessageMapping("start_session")
    void startSession() {
//        var appUser = convertToAppUser(profile);
//        userStatusService.setUserOnline(appUser.getEmail());
    }

    @MessageMapping("close_session")
    void closeSession() {
//        var appUser = convertToAppUser(profile);
//        userStatusService.setUserOffline(appUser.getEmail());
    }
}
