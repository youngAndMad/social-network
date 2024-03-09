package socialapp.chatservice.service;

import socialapp.chatservice.model.dto.CreatePrivateChatRequestDto;
import socialapp.chatservice.model.dto.notification.LeaveChatNotification;
import socialapp.chatservice.model.dto.notification.MessageNotification;
import socialapp.chatservice.model.dto.PrivateMessageRequest;
import socialapp.chatservice.model.entity.AppUser;
import socialapp.chatservice.model.entity.Chat;
import socialapp.chatservice.model.entity.ChatMember;
import socialapp.chatservice.model.entity.Message;

import java.util.List;
import java.util.Set;

public interface ChatService {

    Chat createPrivateChat(AppUser appUser, CreatePrivateChatRequestDto requestDto);

    LeaveChatNotification leaveChat(AppUser appUser, String chatId);

    boolean existById(String chatId);

    Chat insertMessage(String chatId, Message message);

    Set<ChatMember> getChatMembers(String chatId);
}
