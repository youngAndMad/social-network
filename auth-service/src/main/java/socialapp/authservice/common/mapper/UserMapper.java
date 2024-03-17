package socialapp.authservice.common.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import socialapp.authservice.model.dto.RegistrationDto;
import socialapp.authservice.model.entity.User;

import java.time.LocalDateTime;

@Mapper(imports = {LocalDateTime.class})
public interface UserMapper {

    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "emailVerified" , expression = "java(false)")
    @Mapping(target = "password", expression = "java(hashPassword)")
    @Mapping(target = "otpCreationTime" , expression = "java(LocalDateTime.now())")
    User toModel(RegistrationDto registrationDto, String hashPassword, Integer otp);
}
