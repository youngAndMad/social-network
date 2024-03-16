package socialapp.chatservice.web.http;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import socialapp.chatservice.common.annotation.FetchUserContext;
import socialapp.chatservice.common.context.UserContextHolder;
import socialapp.chatservice.model.dto.CreatePrivateChatRequestDto;
import socialapp.chatservice.model.entity.Chat;
import socialapp.chatservice.service.ChatService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChatController {

    private final ChatService chatService;

    @PostMapping("private-chat")
    @FetchUserContext
    Chat createPrivateChat(
            @RequestBody @Valid CreatePrivateChatRequestDto requestDto
    ) {
        return chatService.createPrivateChat(UserContextHolder.getCurrentUser(), requestDto);
    }

    @GetMapping("my")
    @FetchUserContext
    List<Chat> getUserChats() {
        return chatService.getChatsByMember(UserContextHolder.getCurrentUser());
    }

}
