package socialapp.userservice.model.dto;

import socialapp.userservice.model.entity.User;

import java.time.LocalDate;

public record RelationDto(
        User user,
        LocalDate createdTime
) {
}
