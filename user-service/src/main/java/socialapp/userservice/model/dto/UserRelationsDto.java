package socialapp.userservice.model.dto;

import java.util.Set;

public record UserRelationsDto(
    Set<RelationDto> blocks,
    Set<RelationDto> friends,
    Set<RelationDto> outgoingSubscriptions,
    Set<RelationDto> incomingSubscriptions

) {
}
