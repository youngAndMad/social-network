package socialapp.chatservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import socialapp.chatservice.model.entity.Chat;
import socialapp.chatservice.model.entity.ChatMember;
import socialapp.chatservice.model.enums.ChatType;

import java.util.Set;

@Mapper(config = GlobalMapperConfig.class)
public interface ChatMapper {

    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "type" , expression = "java(type)")
    @Mapping(target="createdAt" , expression = "java(java.time.LocalDateTime.now())")
    Chat toModel(Set<ChatMember> members, ChatType type, String name);

}
