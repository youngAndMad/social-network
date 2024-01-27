package socialapp.ktuserservice.model.dto

data class UserSearchCriteria(
    val ageFrom: Int?,
    val ageTo: Int?,
    val nameLike: String?,
    val city: String?,
    val country: String?
)
