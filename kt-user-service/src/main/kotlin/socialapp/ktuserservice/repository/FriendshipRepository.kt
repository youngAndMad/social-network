package socialapp.ktuserservice.repository

import org.springframework.stereotype.Repository
import socialapp.ktuserservice.model.entity.Friendship
import socialapp.ktuserservice.repository.common.CommonRepository

@Repository
interface FriendshipRepository : CommonRepository<Friendship,Long> {

    fun findAllByReceiverIdOrSenderId(receiverId: Long, senderId: Long): Set<Friendship>

    override fun entityClass(): Class<*> = Friendship::class.java
}