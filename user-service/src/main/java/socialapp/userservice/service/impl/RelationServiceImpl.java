package socialapp.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import socialapp.userservice.common.exception.InvalidSubscription;
import socialapp.userservice.model.dto.RelationDto;
import socialapp.userservice.model.dto.UserRelationsDto;
import socialapp.userservice.model.entity.Block;
import socialapp.userservice.model.entity.Friendship;
import socialapp.userservice.model.entity.Subscription;
import socialapp.userservice.repository.BlockRepository;
import socialapp.userservice.repository.FriendshipRepository;
import socialapp.userservice.repository.SubscriptionRepository;
import socialapp.userservice.service.RelationService;
import socialapp.userservice.service.UserService;

import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RelationServiceImpl implements RelationService {

    private final SubscriptionRepository subscriptionRepository;
    private final BlockRepository blockRepository;
    private final FriendshipRepository friendshipRepository;
    private final UserService userService;

    @Override
    public void deleteBlock(Long id) {
        blockRepository.deleteById(id);
    }

    @Override
    public void deleteSubscription(Long id) {
        subscriptionRepository.deleteById(id);
    }

    @Override
    public void deleteFriendship(Long id, Long by) { // todo refactor govno_code
        var friendship = friendshipRepository.findByID(id);

        var unsubscribedUser = friendship.getSender().getId().equals(by) ?
                friendship.getSender() : friendship.getReceiver();

        if (!unsubscribedUser.getId().equals(by)) {
            return;//throw exception and handle with rest controller advice
        }

        friendshipRepository.deleteById(id);

        var subscription = new Subscription();
        subscription.setReceiver(unsubscribedUser);
        subscription.setSender(friendship.getSender().getId().equals(unsubscribedUser.getId()) ? friendship.getReceiver() : friendship.getSender());

        subscriptionRepository.save(subscription);
    }

    @Override
    public void blockUser(Long sender, Long receiver) {
        var senderUser = userService.findById(sender);
        var receiverUser = userService.findById(receiver);

        var block = new Block();
        block.setSender(senderUser);
        block.setReceiver(receiverUser);

        blockRepository.save(block);
    }

    @Override
    public void subscribeUser(Long sender, Long receiver) {
        var senderUser = userService.findById(sender);
        var receiverUser = userService.findById(receiver);

        var subscription = new Subscription();
        subscription.setSender(senderUser);
        subscription.setReceiver(receiverUser);

        subscriptionRepository.save(subscription);
    }

    @Override
    public void addFriendship(Long sender, Long receiver) {
        var subscription = subscriptionRepository.findBySenderIdAndReceiverId(sender, receiver)
                .orElseThrow(InvalidSubscription::new);

        var friendShip = new Friendship();
        friendShip.setSender(subscription.getSender());
        friendShip.setReceiver(subscription.getReceiver());

        friendshipRepository.save(friendShip);
    }

    @Override
    public UserRelationsDto findUserRelations(Long userId) {
        var user = userService.findById(userId);
        var blockSet = blockRepository.findAllBySenderId(user.getId());
        var incomingSubscriptions = subscriptionRepository.findAllByReceiverId(user.getId());
        var outgoingSubscriptions = subscriptionRepository.findAllBySenderId(user.getId());
        var friends = friendshipRepository.findAllByReceiverIdOrSenderId(user.getId(), user.getId());

        return new UserRelationsDto( // never do this // todo refactor code
                blockSet.stream().map(b -> new RelationDto(b.getReceiver(),b.getCreatedTime().toLocalDate())).collect(Collectors.toSet()),
                friends.stream().map(f -> new RelationDto(f.getReceiver().getId().equals(user.getId()) ? f.getSender() : f.getReceiver(),f.getCreatedTime().toLocalDate()))
                        .collect(Collectors.toSet()),
                outgoingSubscriptions.stream().map(b -> new RelationDto(b.getReceiver(),b.getCreatedTime().toLocalDate())).collect(Collectors.toSet()),
                incomingSubscriptions.stream().map(b -> new RelationDto(b.getSender(),b.getCreatedTime().toLocalDate())).collect(Collectors.toSet())
        );
    }
}
