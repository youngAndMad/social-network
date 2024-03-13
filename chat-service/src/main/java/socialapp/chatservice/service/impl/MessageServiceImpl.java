package socialapp.chatservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import socialapp.chatservice.common.exception.EntityNotFoundException;
import socialapp.chatservice.mapper.MessageMapper;
import socialapp.chatservice.mapper.NotificationMapper;
import socialapp.chatservice.model.dto.PrivateMessageRequest;
import socialapp.chatservice.model.dto.notification.MessageNotification;
import socialapp.chatservice.model.entity.AppUser;
import socialapp.chatservice.model.entity.Chat;
import socialapp.chatservice.service.ChatService;
import socialapp.chatservice.service.MessageService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static socialapp.chatservice.common.AppConstants.USER_MESSAGE_NOTIFICATIONS;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final ChatService chatService;
    private final MessageMapper messageMapper;
    private final NotificationMapper notificationMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    private final Function<LocalDateTime, Long> toEpochSecond = time -> time.toEpochSecond(ZoneOffset.UTC);


    @Override
    public MessageNotification saveMessage(
            PrivateMessageRequest messageRequest,
            AppUser appUser
    ) {
        if (!chatService.existById(messageRequest.chatId())) {
            log.error("Chat with id = {} not found", messageRequest.chatId());
            throw new EntityNotFoundException(Chat.class, messageRequest.chatId());
        }

        var message = messageMapper.toMessage(messageRequest, appUser);

        var chat = chatService.insertMessage(messageRequest.chatId(), message);

        log.info("Message saved to chat with id = {}", chat.getId());

        return notificationMapper.fromPrivateMessage(message, chat);
    }

    @Override
    public Set<MessageNotification> checkMessages(AppUser appUser) { // todo extract to separate service with redis operations
        var notifications = redisTemplate.opsForZSet()
                .range(USER_MESSAGE_NOTIFICATIONS.formatted(appUser.getEmail()), 0, -1);

        if (notifications == null) {
            return Collections.emptySet();
        }

        return notifications.stream()
                .map(notification -> (MessageNotification) notification)
                .collect(Collectors.toSet());
    }

    @Override
    public void persistMessageNotification(MessageNotification messageNotification) {
        redisTemplate.opsForZSet()
                .add(USER_MESSAGE_NOTIFICATIONS.formatted(messageNotification.getSenderName()),
                        messageNotification,
                        toEpochSecond.apply(messageNotification.getNotificationTime())
                );
    }

}
