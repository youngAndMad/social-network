package socialapp.ktuserservice.repository

import org.springframework.stereotype.Repository
import socialapp.ktuserservice.model.entity.Subscription
import socialapp.ktuserservice.repository.common.CommonRepository

@Repository
interface SubscriptionRepository: CommonRepository<Subscription,Long> {

    fun findBySenderIdAndReceiverId(senderId: Long, receiverId: Long): Subscription?

    fun findAllBySenderId(senderId: Long): Set<Subscription>

    fun findAllByReceiverId(receiverId: Long): Set<Subscription>

    override fun entityClass(): Class<*> = Subscription::class.java
}