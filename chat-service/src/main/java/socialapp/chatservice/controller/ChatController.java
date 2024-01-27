package socialapp.chatservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import socialapp.chatservice.model.dto.CreatePrivateChatRequestDto;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ChatController {

    @PostMapping("private-chat")
    ResponseEntity<?> createPrivateChat(
            @RequestBody CreatePrivateChatRequestDto requestDto
    ){

    }

}
