package socialapp.userservice.repository;

import org.springframework.stereotype.Repository;
import socialapp.userservice.model.entity.User;
import socialapp.userservice.repository.common.CommonRepository;

@Repository
public interface UserRepository extends CommonRepository<User, Long> {

    Boolean existsByEmail(String email);

    @Override
    default Class<?> entityClass(){
        return User.class;
    };

}
