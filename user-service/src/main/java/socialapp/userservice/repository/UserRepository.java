package socialapp.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import socialapp.userservice.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);
}
