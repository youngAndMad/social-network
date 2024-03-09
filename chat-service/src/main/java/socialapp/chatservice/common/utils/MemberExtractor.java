package socialapp.chatservice.common.utils;

import lombok.experimental.UtilityClass;
import socialapp.chatservice.model.entity.AppUser;
import socialapp.chatservice.model.entity.ChatMember;
import socialapp.chatservice.model.entity.PrivateChatMember;

import java.util.Set;

@UtilityClass
public class MemberExtractor {

    public static PrivateChatMember extractPrivateChatMember(
            Set<? extends ChatMember> chatMembers,
            AppUser currentUser
    ) {
        return chatMembers.stream()
                .filter(member -> member.getAppUser().getEmail().equals(currentUser.getEmail()))
                .map(PrivateChatMember.class::cast)
                .findFirst()
                .orElseThrow();
    }
}
