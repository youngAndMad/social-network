package socialapp.chatservice.service;

import socialapp.chatservice.model.dto.CreatePrivateChatRequestDto;
import socialapp.chatservice.model.dto.notification.LeaveChatNotification;
import socialapp.chatservice.model.dto.notification.MessageNotification;
import socialapp.chatservice.model.dto.PrivateMessageRequest;
import socialapp.chatservice.model.entity.AppUser;
import socialapp.chatservice.model.entity.Chat;

public interface ChatService {

    Chat createPrivateChat(AppUser appUser, CreatePrivateChatRequestDto requestDto);

    MessageNotification saveMessage(PrivateMessageRequest messageRequest, AppUser appUser);

    LeaveChatNotification leaveChat(AppUser appUser, String chatId);
}
