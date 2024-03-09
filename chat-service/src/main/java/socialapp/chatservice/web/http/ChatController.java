package socialapp.chatservice.web.http;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import socialapp.chatservice.common.annotation.FetchUserContext;
import socialapp.chatservice.common.context.UserContextHolder;
import socialapp.chatservice.model.dto.CreatePrivateChatRequestDto;
import socialapp.chatservice.model.entity.Chat;
import socialapp.chatservice.service.ChatService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v/chat")
public class ChatController {

    private final ChatService chatService;

    @PostMapping("private-chat")
    @FetchUserContext
    @ResponseStatus(HttpStatus.CREATED)
    Chat createPrivateChat(
            @RequestBody @Valid CreatePrivateChatRequestDto requestDto
    ) {
        return chatService.createPrivateChat(UserContextHolder.getCurrentUser(), requestDto);
    }

}
