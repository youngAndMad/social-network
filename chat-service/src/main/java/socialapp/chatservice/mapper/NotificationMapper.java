package socialapp.chatservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import socialapp.chatservice.model.dto.notification.LeaveChatNotification;
import socialapp.chatservice.model.dto.notification.MessageNotification;
import socialapp.chatservice.model.entity.AppUser;
import socialapp.chatservice.model.entity.Chat;
import socialapp.chatservice.model.entity.Message;
import socialapp.chatservice.model.enums.NotificationType;

import java.time.LocalDateTime;
import java.util.UUID;

@Mapper(
        config = GlobalMapperConfig.class,
        imports = {
                UUID.class, LocalDateTime.class, NotificationType.class
        }
)
public interface NotificationMapper {

    @Mapping(target = "chatName", source = "chat.name")
    @Mapping(target = "senderName", source = "message.sender.preferredUsername")
    @Mapping(target = "notificationTime", expression = "java(LocalDateTime.now())")
    @Mapping(target = "message", source = "message.content")
    @Mapping(target = "chatId", source = "chat.id")
    @Mapping(target = "type", expression = "java(NotificationType.MESSAGE)")
    @Mapping(target = "id", expression = "java(UUID.randomUUID().toString())")
    MessageNotification fromPrivateMessage(Message message, Chat chat);

    @Mapping(target = "chatName", source = "chat.name")
    @Mapping(target = "chatId", source = "chat.id")
    @Mapping(target = "notificationTime", expression = "java(LocalDateTime.now())")
    @Mapping(target = "id", expression = "java(UUID.randomUUID().toString())")
    @Mapping(target = "username", source = "appUser.preferredUsername")
    @Mapping(target = "type", expression = "java(NotificationType.LEAVE_CHAT)")
    LeaveChatNotification createLeaveChatNotification(AppUser appUser, Chat chat);

}
