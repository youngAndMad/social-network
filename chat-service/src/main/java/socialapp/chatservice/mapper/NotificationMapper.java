package socialapp.chatservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import socialapp.chatservice.model.dto.notification.MessageNotification;
import socialapp.chatservice.model.entity.Chat;
import socialapp.chatservice.model.entity.Message;

@Mapper
public interface NotificationMapper {

    @Mapping(target = "chatName", source = "chat.name")
    @Mapping(target = "senderName", source = "message.sender.preferredUsername")
    @Mapping(target = "notificationTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "message", source = "message.content")
    @Mapping(target = "chatId", source = "chat.id")
    MessageNotification fromPrivateMessage(Message message, Chat chat);

}
