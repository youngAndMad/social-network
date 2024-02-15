package socialapp.ktuserservice.common.mapper

import org.springframework.stereotype.Component
import socialapp.ktuserservice.model.dto.RegistrationDto
import socialapp.ktuserservice.model.dto.UserUpdateDto
import socialapp.ktuserservice.model.entity.Address
import socialapp.ktuserservice.model.entity.User

@Component
class UserMapper {

    fun toModel(registrationDto: RegistrationDto, address: Address): User {
        return User().apply {
            givenName = registrationDto.givenName
            preferredUsername = registrationDto.preferredUsername
            familyName = registrationDto.familyName
            email = registrationDto.email
            birthDate = registrationDto.birthDate
            gender = registrationDto.gender
            this.address = address
        }
    }

    fun update(userUpdateDto: UserUpdateDto, user: User) {
        user.apply {
            givenName = userUpdateDto.firstName
            familyName = userUpdateDto.lastName
            birthDate = userUpdateDto.birthDate
        }
    }
}

