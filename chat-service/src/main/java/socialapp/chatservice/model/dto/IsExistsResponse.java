package socialapp.chatservice.model.dto;

import socialapp.chatservice.model.entity.AppUser;

public record IsExistsResponse(
    boolean isExists,
    AppUser user
) {
}
