package socialapp.ktuserservice.service

import socialapp.ktuserservice.model.dto.UserRelationsDto

interface RelationService {

    fun deleteBlock(id: Long)

    fun deleteSubscription(id: Long)

    fun deleteFriendship(id: Long, by: Long)

    fun blockUser(sender: Long, receiver: Long)

    fun subscribeUser(sender: Long, receiver: Long)

    fun addFriendship(sender: Long, receiver: Long)

    fun findUserRelations(userId: Long): UserRelationsDto
}