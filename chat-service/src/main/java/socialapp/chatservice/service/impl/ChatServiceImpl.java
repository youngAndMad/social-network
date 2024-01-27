package socialapp.chatservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import socialapp.chatservice.common.exception.CreatePrivateChatMemberNotExistException;
import socialapp.chatservice.common.feign.UserServiceClient;
import socialapp.chatservice.mapper.ChatMapper;
import socialapp.chatservice.mapper.MemberMapper;
import socialapp.chatservice.model.dto.CreatePrivateChatRequestDto;
import socialapp.chatservice.model.entity.AppUser;
import socialapp.chatservice.model.entity.Chat;
import socialapp.chatservice.model.enums.ChatType;
import socialapp.chatservice.repository.ChatRepository;
import socialapp.chatservice.service.ChatService;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;
    private final UserServiceClient userServiceClient;
    private final MemberMapper memberMapper;

    @Override
    public Chat createPrivateChat(
            AppUser appUser,
            CreatePrivateChatRequestDto requestDto
    ) {
        var userIsExist = userServiceClient.isExists(requestDto.receiverEmail());

        if (!userIsExist.isExists()) {
            throw new CreatePrivateChatMemberNotExistException(requestDto.receiverEmail());
        }

        var creator = memberMapper.toPrivateChatMember(appUser);
        var receiver = memberMapper.toPrivateChatMember(userIsExist.user());

        return chatRepository.save(chatMapper.toModel(Set.of(creator, receiver), ChatType.PRIVATE));
    }

}
