package socialapp.ktuserservice.common.mapper

import org.springframework.stereotype.Component
import socialapp.ktuserservice.model.dto.UserDto
import socialapp.ktuserservice.model.entity.Address
import socialapp.ktuserservice.model.entity.User

@Component
class UserMapper {

    fun toModel(userDto: UserDto, address: Address): User {
        return User().apply {
            setUserProperties(this, userDto, address)
        }
    }

    fun update(user: User, userDto: UserDto): User {
        return user.apply {
            setUserProperties(this, userDto, user.address!!)
        }
    }

    private fun setUserProperties(user: User, userDto: UserDto, address: Address) {
        user.apply {
            givenName = userDto.givenName
            preferredUsername = userDto.preferredUsername
            familyName = userDto.familyName
            email = userDto.email
            birthDate = userDto.birthDate
            gender = userDto.gender
            this.address = address
        }
    }

}

