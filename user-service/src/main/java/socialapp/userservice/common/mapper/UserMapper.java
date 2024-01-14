package socialapp.userservice.common.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import socialapp.userservice.model.dto.RegistrationDto;
import socialapp.userservice.model.entity.Address;
import socialapp.userservice.model.entity.User;

@Mapper
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address", expression = "java(address)")
    User toModel(RegistrationDto registrationDto, Address address);
}
