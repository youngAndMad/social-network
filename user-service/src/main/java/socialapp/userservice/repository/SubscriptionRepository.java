package socialapp.userservice.repository;

import org.springframework.stereotype.Repository;
import socialapp.userservice.model.entity.Subscription;
import socialapp.userservice.repository.common.CommonRepository;

@Repository
public interface SubscriptionRepository extends CommonRepository<Subscription,Long> {

    @Override
    default Class<?> entityClass(){
        return Subscription.class;
    };

}
