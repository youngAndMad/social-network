package socialapp.ktuserservice.common.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.NullValueCheckStrategy
import org.mapstruct.NullValuePropertyMappingStrategy
import socialapp.ktuserservice.model.dto.RegistrationDto
import socialapp.ktuserservice.model.dto.UserUpdateDto
import socialapp.ktuserservice.model.entity.Address
import socialapp.ktuserservice.model.entity.User

@Mapper(
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address", expression = "java(address)")
    fun toModel(registrationDto: RegistrationDto, address: Address): User

    fun update(userUpdateDto: UserUpdateDto, @MappingTarget user: User)
}

