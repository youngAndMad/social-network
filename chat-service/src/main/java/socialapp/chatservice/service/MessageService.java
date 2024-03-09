package socialapp.chatservice.service;

import socialapp.chatservice.model.dto.PrivateMessageRequest;
import socialapp.chatservice.model.dto.notification.MessageNotification;
import socialapp.chatservice.model.entity.AppUser;

public interface MessageService{

    MessageNotification saveMessage(PrivateMessageRequest messageRequest, AppUser appUser);

}
