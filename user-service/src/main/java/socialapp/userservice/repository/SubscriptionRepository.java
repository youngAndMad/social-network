package socialapp.userservice.repository;

import org.springframework.stereotype.Repository;
import socialapp.userservice.model.entity.Subscription;
import socialapp.userservice.repository.common.CommonRepository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends CommonRepository<Subscription,Long> {

    Optional<Subscription> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
    @Override
    default Class<?> entityClass(){
        return Subscription.class;
    };

}
