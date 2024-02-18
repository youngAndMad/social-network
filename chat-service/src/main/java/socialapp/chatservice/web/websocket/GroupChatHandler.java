package socialapp.chatservice.web.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import socialapp.chatservice.service.ChatService;

@Controller
@RequiredArgsConstructor
public class GroupChatHandler {

    private final ChatService chatService;

    void leaveChat(){

    };
}
