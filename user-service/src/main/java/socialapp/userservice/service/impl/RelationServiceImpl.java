package socialapp.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import socialapp.userservice.repository.BlockRepository;
import socialapp.userservice.repository.FriendshipRepository;
import socialapp.userservice.repository.SubscriptionRepository;
import socialapp.userservice.service.RelationService;

@Service
@RequiredArgsConstructor
public class RelationServiceImpl implements RelationService {

    private final SubscriptionRepository subscriptionRepository;
    private final BlockRepository blockRepository;
    private final FriendshipRepository friendshipRepository;

    @Override
    public void deleteBlock(Long id) {
        blockRepository.deleteById(id);
    }

    @Override
    public void deleteSubscription(Long id) {

    }

    @Override
    public void deleteFriendship(Long id) {
        var friendship = friendshipRepository.findByID(id);

    }
}
