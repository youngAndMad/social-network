package socialapp.chatservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import socialapp.chatservice.model.dto.notification.MessageNotification;
import socialapp.chatservice.model.entity.Chat;
import socialapp.chatservice.model.entity.Message;

import java.time.LocalDateTime;
import java.util.UUID;

@Mapper(
        imports = {
                UUID.class, LocalDateTime.class
        }
)
public interface NotificationMapper {

    @Mapping(target = "chatName", source = "chat.name")
    @Mapping(target = "senderName", source = "message.sender.email")
    @Mapping(target = "notificationTime", expression = "java(LocalDateTime.now())")
    @Mapping(target = "message", source = "message.content")
    @Mapping(target = "chatId", source = "chat.id")
    @Mapping(target = "id", source = "UUID.randomUUID().toString()")
    MessageNotification fromPrivateMessage(Message message, Chat chat);

}
