package socialapp.userservice.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import socialapp.userservice.common.exception.EntityNotFoundException;

import java.io.Serializable;

@NoRepositoryBean
public interface CommonRepository<E,P extends Serializable> extends JpaRepository<E,P> {

    default E findByID(P id){
        return findById(id)
                .orElseThrow(() -> new EntityNotFoundException(entityClass(),id));
    }

    Class<?> entityClass();
}
