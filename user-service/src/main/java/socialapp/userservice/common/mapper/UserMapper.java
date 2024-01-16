package socialapp.userservice.common.mapper;

import org.mapstruct.*;
import socialapp.userservice.model.dto.RegistrationDto;
import socialapp.userservice.model.dto.UserUpdateDto;
import socialapp.userservice.model.entity.Address;
import socialapp.userservice.model.entity.User;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address", expression = "java(address)")
    User toModel(RegistrationDto registrationDto, Address address);

    void update(UserUpdateDto userUpdateDto, @MappingTarget User user);
}
