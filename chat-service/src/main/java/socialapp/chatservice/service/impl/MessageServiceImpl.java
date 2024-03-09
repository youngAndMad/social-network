package socialapp.chatservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final ChatService chatService;
    private final MessageMapper messageMapper;
    private final NotificationMapper notificationMapper;


    @Override
    public MessageNotification saveMessage(
            PrivateMessageRequest messageRequest,
            AppUser appUser
    ) {
        if (!chatService.existById(messageRequest.chatId())) {
            log.warn("Chat with id = {} not found", messageRequest.chatId());
            throw new EntityNotFoundException(Chat.class, messageRequest.chatId());
        }

        var message = messageMapper.toMessage(messageRequest, appUser);

        var chat = chatService.insertMessage(messageRequest.chatId(), message);

        log.info("Message saved to chat with id = {}", chat.getId());

        return notificationMapper.fromPrivateMessage(message, chat);
    }

}
