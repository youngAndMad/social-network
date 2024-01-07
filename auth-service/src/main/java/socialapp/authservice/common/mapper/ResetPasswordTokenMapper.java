package socialapp.authservice.common.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import socialapp.authservice.model.entity.ResetPasswordToken;
import socialapp.authservice.model.entity.User;

import java.time.LocalDateTime;

@Mapper
public interface ResetPasswordTokenMapper {

    @Mapping(target = "id",ignore = true)
    ResetPasswordToken toModel(User user, String token, LocalDateTime expiryDate);

}
