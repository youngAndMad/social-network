package socialapp.ktuserservice.model.dto


data class AppUserDto(
    val givenName: String,
    val email: String,
    val familyName: String,
    val preferredUsername: String
)
