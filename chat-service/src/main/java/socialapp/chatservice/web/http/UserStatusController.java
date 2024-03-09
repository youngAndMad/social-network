package socialapp.chatservice.web.http;

import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import socialapp.chatservice.model.dto.IsOnlineResponse;
import socialapp.chatservice.service.UserStatusService;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/chat/user")
public class UserStatusController {

    private final UserStatusService userStatusService;

    @GetMapping("status")
    IsOnlineResponse isOnline(
            @RequestParam @Email String email
    ) {
        return userStatusService.isUserOnline(email);
    }
}
