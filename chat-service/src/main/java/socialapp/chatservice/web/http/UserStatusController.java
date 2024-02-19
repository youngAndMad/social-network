package socialapp.chatservice.web.http;

import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import socialapp.chatservice.common.annotation.FetchUserContext;
import socialapp.chatservice.common.context.UserContextHolder;
import socialapp.chatservice.model.dto.IsOnlineResponse;
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

    @GetMapping("status")
    ResponseEntity<IsOnlineResponse> isOnline(
            @RequestParam @Email String email
    ){
        return ResponseEntity.ok(userStatusService.isUserOnline(email));
    }
}
