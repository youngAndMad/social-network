package socialapp.chatservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import socialapp.chatservice.model.dto.PrivateMessageRequest;
import socialapp.chatservice.model.entity.AppUser;
import socialapp.chatservice.model.entity.Message;

@Mapper
public interface MessageMapper {

    @Mapping(target = "message", source = "privateMessageRequest.message")
    @Mapping(target = "sender", source = "appUser")
    @Mapping(target = "sentAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "id", ignore = true)
    Message toMessage(PrivateMessageRequest privateMessageRequest, AppUser appUser);

}
