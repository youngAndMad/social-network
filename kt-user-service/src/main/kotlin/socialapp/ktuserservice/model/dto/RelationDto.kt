package socialapp.ktuserservice.model.dto

import socialapp.ktuserservice.model.entity.User
import java.time.LocalDate
import java.time.LocalDateTime

data class RelationDto(
    val user: User,
    val createdTime: LocalDate
)
