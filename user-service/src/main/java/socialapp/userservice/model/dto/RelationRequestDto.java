package socialapp.userservice.model.dto;

import socialapp.userservice.model.enums.RelationStatus;

public record RelationRequestDto(
        Long from,
        Long receiver,
        RelationStatus status
) {
}
