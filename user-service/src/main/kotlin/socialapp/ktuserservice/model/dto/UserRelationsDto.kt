package socialapp.ktuserservice.model.dto

data class UserRelationsDto(
    val blocks: Set<RelationDto>,
    val friends: Set<RelationDto>,
    val outgoingSubscriptions: Set<RelationDto>,
    val incomingSubscriptions: Set<RelationDto>
)
