package socialapp.chatservice.web.http;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import socialapp.chatservice.common.annotation.FetchUserContext;
import socialapp.chatservice.common.context.UserContextHolder;
import socialapp.chatservice.model.dto.CreatePrivateChatRequestDto;
import socialapp.chatservice.model.entity.Chat;
import socialapp.chatservice.service.ChatService;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ChatController {

    private final ChatService chatService;

    @PostMapping("private-chat")
    @FetchUserContext
    ResponseEntity<Chat> createPrivateChat(
            @RequestBody @Valid CreatePrivateChatRequestDto requestDto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(chatService.createPrivateChat(UserContextHolder.getCurrentUser(), requestDto));
    }

}
