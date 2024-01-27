package socialapp.chatservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import socialapp.chatservice.mapper.ChatMapper;
import socialapp.chatservice.model.dto.CreatePrivateChatRequestDto;
import socialapp.chatservice.model.entity.AppUser;
import socialapp.chatservice.model.entity.Chat;
import socialapp.chatservice.repository.ChatRepository;
import socialapp.chatservice.service.ChatService;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;

    @Override
    public Chat createPrivateChat(AppUser appUser, CreatePrivateChatRequestDto requestDto) {
        return null;
    }

}
