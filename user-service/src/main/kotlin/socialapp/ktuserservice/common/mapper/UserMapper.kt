package socialapp.ktuserservice.common.mapper

import org.springframework.stereotype.Component
import socialapp.ktuserservice.model.dto.UserDto
import socialapp.ktuserservice.model.entity.Address
import socialapp.ktuserservice.model.entity.User
import socialapp.ktuserservice.model.wrapper.UserElasticWrapper

@Component
class UserMapper(
    private val addressMapper: AddressMapper
) {
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

    fun toWrapper(user: User): UserElasticWrapper {
        return UserElasticWrapper(
            user.id!!,
            user.givenName!!,
            user.preferredUsername!!,
            user.familyName!!,
            user.email!!,
            user.avatar ?:"null",
            addressMapper.toDto(user.address)
        )
    }

}

