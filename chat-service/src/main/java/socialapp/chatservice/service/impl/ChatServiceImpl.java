package socialapp.chatservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import socialapp.chatservice.common.exception.CreatePrivateChatMemberNotExistException;
import socialapp.chatservice.common.exception.EntityNotFoundException;
import socialapp.chatservice.common.feign.UserServiceClient;
import socialapp.chatservice.mapper.ChatMapper;
import socialapp.chatservice.mapper.MemberMapper;
import socialapp.chatservice.mapper.NotificationMapper;
import socialapp.chatservice.model.dto.CreatePrivateChatRequestDto;
import socialapp.chatservice.model.dto.notification.LeaveChatNotification;
import socialapp.chatservice.model.entity.AppUser;
import socialapp.chatservice.model.entity.Chat;
import socialapp.chatservice.model.entity.ChatMember;
import socialapp.chatservice.model.entity.Message;
import socialapp.chatservice.model.enums.ChatType;
import socialapp.chatservice.repository.ChatRepository;
import socialapp.chatservice.service.ChatService;

import java.util.List;
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
    private final NotificationMapper notificationMapper;

    @Override
    public Chat createPrivateChat(
            AppUser appUser,
            CreatePrivateChatRequestDto requestDto
    ) {
        var userIsExist = userServiceClient.isExists(requestDto.receiverEmail());

        if (!userIsExist.exists()) {
            throw new CreatePrivateChatMemberNotExistException(requestDto.receiverEmail());
        }

        var creator = memberMapper.toPrivateChatMember(appUser);
        var receiver = memberMapper.toPrivateChatMember(userIsExist.user());

        var query = new Query(Criteria.where(Chat.Fields.members).all(Set.of(creator, receiver)));
        var chat = mongoTemplate.findOne(query, Chat.class);

        if (chat != null) {
            return chat;
        }

        var saved = chatRepository.save(chatMapper.toModel(Set.of(creator, receiver), ChatType.PRIVATE, getPrivateChatName(appUser, userIsExist.user())));
        log.info("Creating private chat between {} and {} id = {}", creator, receiver, saved.getId());
        return saved;
    }

    @Override
    public LeaveChatNotification leaveChat(AppUser appUser, String chatId) {
        var query = new Query(Criteria.where(Chat.Fields.id).is(chatId));
        var update = new Update().pull(Chat.Fields.members, Query.query(Criteria.where(AppUser.Fields.email).is(appUser.getEmail())));
        var chat = mongoTemplate.findAndModify(query, update, Chat.class);

        if (chat == null) {
            throw new EntityNotFoundException(Chat.class, chatId);
        }

        if (chat.getMembers().isEmpty()) {
            chatRepository.deleteById(chatId);
        }

        return notificationMapper.createLeaveChatNotification(appUser, chat);
    }

    @Override
    @Cacheable(value = "chat", key = "#chatId")
    public boolean existById(String chatId) {
        return chatRepository.existsById(chatId);
    }

    @Override
    @CachePut(value = "chat", key = "#chatId")
    public Chat insertMessage(String chatId, Message message) {
        var query = new Query(Criteria.where(Chat.Fields.id).is(chatId));
        var update = new Update().push(Chat.Fields.messages, message);

        var chat = mongoTemplate.findAndModify(query, update, Chat.class);

        if (chat == null) {
            throw new EntityNotFoundException(Chat.class, chatId);
        }

        return chat;
    }

    @Override
    @Cacheable(value = "chat", key = "#appUser.email")
    public List<Chat> getChatsByMember(AppUser appUser) {
        var query = new Query(Criteria.where(Chat.Fields.members)
                .elemMatch(Criteria.where(AppUser.Fields.email).is(appUser.getEmail())));

        return mongoTemplate.find(query, Chat.class);
    }

    @Override
    @Cacheable(value = "chatMembers", key = "#chatId")
    public Set<ChatMember> getChatMembers(String chatId) {
        var query = new Query(Criteria.where(Chat.Fields.id).is(chatId));
        query.fields().include(Chat.Fields.members);
        var chat = mongoTemplate.findOne(query, Chat.class);

        if (chat == null) {
            throw new EntityNotFoundException(Chat.class, chatId);
        }

        return chat.getMembers();
    }

    private String getPrivateChatName(AppUser creator, AppUser receiver) {
        return creator.getEmail() + ":" + receiver.getEmail();
    }

}
