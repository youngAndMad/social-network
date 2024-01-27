package socialapp.ktuserservice.model.dto


data class IsExistsResponse(
    val exists: Boolean,
    val user: AppUserDto? = null
)
