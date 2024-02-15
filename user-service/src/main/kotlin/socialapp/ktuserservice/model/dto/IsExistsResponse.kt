package socialapp.ktuserservice.model.dto

import socialapp.ktuserservice.model.entity.User


data class IsExistsResponse(
    val exists: Boolean,
    val user: User? = null
)
