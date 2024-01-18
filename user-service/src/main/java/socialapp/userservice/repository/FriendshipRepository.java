package socialapp.userservice.repository;

import org.springframework.stereotype.Repository;
import socialapp.userservice.model.entity.Friendship;
import socialapp.userservice.repository.common.CommonRepository;

@Repository
public interface FriendshipRepository extends CommonRepository<Friendship,Long> {

    @Override
    default Class<?> entityClass(){
        return Friendship.class;
    };

}
