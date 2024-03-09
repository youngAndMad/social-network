package socialapp.chatservice.mapper;

import org.mapstruct.Mapper;
import socialapp.chatservice.model.dto.notification.MessageNotification;
import socialapp.chatservice.model.entity.Chat;
import socialapp.chatservice.model.entity.Message;

@Mapper
public interface NotificationMapper {

    MessageNotification fromPrivateMessage(Message message, Chat chat);

}
