package socialapp.chatservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import socialapp.chatservice.model.entity.AppUser;
import socialapp.chatservice.model.entity.PrivateChatMember;
import socialapp.chatservice.model.enums.ChatRole;

@Mapper(imports = {ChatRole.class})
public interface MemberMapper {

    @Mapping(target = "appUser", expression = "appUser")
    PrivateChatMember toPrivateChatMember(AppUser appUser);

}
