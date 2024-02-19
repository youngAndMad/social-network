package socialapp.chatservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import socialapp.chatservice.common.exception.CreatePrivateChatMemberNotExistException;
import socialapp.chatservice.common.feign.UserServiceClient;
import socialapp.chatservice.mapper.ChatMapper;
import socialapp.chatservice.mapper.MemberMapper;
import socialapp.chatservice.model.dto.CreatePrivateChatRequestDto;
import socialapp.chatservice.model.dto.notification.LeaveChatNotification;
import socialapp.chatservice.model.dto.notification.MessageNotification;
import socialapp.chatservice.model.dto.PrivateMessageRequest;
import socialapp.chatservice.model.entity.AppUser;
import socialapp.chatservice.model.entity.Chat;
import socialapp.chatservice.model.enums.ChatType;
import socialapp.chatservice.repository.ChatRepository;
import socialapp.chatservice.service.ChatService;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;
    private final MongoTemplate mongoTemplate;
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

        var saved = chatRepository.save(chatMapper.toModel(Set.of(creator, receiver), ChatType.PRIVATE, getPrivateChatName(appUser, userIsExist.user())));
        log.info("Creating private chat between {} and {} id = {}", creator, receiver, saved.getId());
        return saved;
    }

    @Override
    public MessageNotification saveMessage(PrivateMessageRequest messageRequest, AppUser appUser) {
        return null;//todo
    }

    @Override
    public LeaveChatNotification leaveChat(AppUser appUser, String chatId) {
        var query = new Query(Criteria.where(Chat.Fields.id).is(chatId));
        var update = new Update().pull(Chat.Fields.members, Query.query(Criteria.where(AppUser.Fields.email).is(appUser.getEmail())));
        var chat = mongoTemplate.findAndModify(query, update, Chat.class);
        assert chat != null; // todo throw exception
        return new LeaveChatNotification(chat.getId(), chat.getName(), appUser.getEmail());
    }

    private String getPrivateChatName(AppUser creator, AppUser receiver) {
        return creator.getEmail() + ":" + receiver.getEmail();
    }

}
