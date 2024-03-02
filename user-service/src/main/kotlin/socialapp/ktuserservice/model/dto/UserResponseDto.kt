package socialapp.ktuserservice.model.dto

import socialapp.ktuserservice.model.entity.User

data class UserResponseDto(
    val user: User,
    val relations: UserRelationsDto
)
