package socialapp.chatservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import socialapp.chatservice.common.annotation.FetchUserContext;
import socialapp.chatservice.common.context.UserContextHolder;
import socialapp.chatservice.service.UserStatusService;

@RestController
@RequiredArgsConstructor
@RequestMapping("ws")
public class UserStatusController {

    private final UserStatusService userStatusService;

    @PostMapping("start-session")
    @FetchUserContext
    void startSession(
    ) {
        userStatusService.setUserOnline(UserContextHolder.getCurrentUser());
    }

    @PostMapping("close-session")
    @FetchUserContext
    void closeSession() {
        userStatusService.setUserOffline(UserContextHolder.getCurrentUser());
    }


}
