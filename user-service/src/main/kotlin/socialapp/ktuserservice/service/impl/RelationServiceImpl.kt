package socialapp.ktuserservice.service.impl

import org.springframework.stereotype.Service
import socialapp.ktuserservice.common.exception.InvalidSubscription
import socialapp.ktuserservice.model.dto.RelationDto
import socialapp.ktuserservice.model.dto.UserRelationsDto
import socialapp.ktuserservice.model.entity.Block
import socialapp.ktuserservice.model.entity.Friendship
import socialapp.ktuserservice.model.entity.Subscription
import socialapp.ktuserservice.repository.BlockRepository
import socialapp.ktuserservice.repository.FriendshipRepository
import socialapp.ktuserservice.repository.SubscriptionRepository
import socialapp.ktuserservice.service.RelationService
import socialapp.ktuserservice.service.UserService
import java.util.stream.Collectors

@Service
class RelationServiceImpl(
    private val subscriptionRepository: SubscriptionRepository,
    private val blockRepository: BlockRepository,
    private val friendshipRepository: FriendshipRepository,
    private val userService: UserService
) : RelationService {

    override fun deleteBlock(id: Long) {
        blockRepository.deleteById(id)
    }

    override fun deleteSubscription(id: Long) {
        subscriptionRepository.deleteById(id)
    }

    override fun deleteFriendship(id: Long, by: Long) {
        val friendship = friendshipRepository.findByID(id)

        val unsubscribedUser = if (friendship.sender?.id == by) friendship.sender else friendship.receiver

        if (unsubscribedUser?.id != by) {
            return
        }

        friendshipRepository.deleteById(id)

        val subscription = Subscription().apply {
            receiver = unsubscribedUser
            sender = if (friendship.sender?.id == unsubscribedUser.id) friendship.receiver else friendship.sender
        }

        subscriptionRepository.save(subscription)
    }

    override fun blockUser(sender: Long, receiver: Long) {
        val senderUser = userService.findById(sender)
        val receiverUser = userService.findById(receiver)

        val block = Block().apply {
            this.sender = senderUser
            this.receiver = receiverUser
        }

        blockRepository.save(block)
    }

    override fun subscribeUser(sender: Long, receiver: Long) {
        val senderUser = userService.findById(sender)
        val receiverUser = userService.findById(receiver)

        val subscription = Subscription().apply {
            this.sender = senderUser
            this.receiver = receiverUser
        }

        subscriptionRepository.save(subscription)
    }

    override fun addFriendship(sender: Long, receiver: Long) {
        val subscription = subscriptionRepository.findBySenderIdAndReceiverId(sender, receiver)
            ?: throw InvalidSubscription()

        val friendShip = Friendship().apply {
            this.sender = subscription.sender
            this.receiver = subscription.receiver
        }


        friendshipRepository.save(friendShip).let {
            subscriptionRepository.delete(subscription)
        }
    }

    override fun findUserRelations(userId: Long): UserRelationsDto {
        val user = userService.findById(userId)
        val blockSet = blockRepository.findAllBySenderId(user.id!!)
        val incomingSubscriptions = subscriptionRepository.findAllByReceiverId(user.id!!)
        val outgoingSubscriptions = subscriptionRepository.findAllBySenderId(user.id!!)
        val friends = friendshipRepository.findAllByReceiverIdOrSenderId(user.id!!, user.id!!)

        return UserRelationsDto(
            blockSet.stream().map { b -> RelationDto(b.id!!, b.receiver!!, b.createdTime?.toLocalDate()!!) }
                .collect(Collectors.toSet()),
            friends.stream().map { f ->
                RelationDto(
                    f.id!!,
                    if (f.receiver?.id == user.id) f.sender!! else f.receiver!!,
                    f.createdTime?.toLocalDate()!!
                )
            }
                .collect(Collectors.toSet()),
            outgoingSubscriptions.stream().map { b -> RelationDto(b.id!!,b.receiver!!, b.createdTime?.toLocalDate()!!) }
                .collect(Collectors.toSet()),
            incomingSubscriptions.stream().map { b -> RelationDto(b.id!!,b.sender!!, b.createdTime?.toLocalDate()!!) }
                .collect(Collectors.toSet())
        )
    }
}