package socialapp.userservice.repository;

import org.springframework.stereotype.Repository;
import socialapp.userservice.model.entity.Block;
import socialapp.userservice.repository.common.CommonRepository;

@Repository
public interface BlockRepository extends CommonRepository<Block,Long> {
    @Override
    default Class<?> entityClass(){
        return Block.class;
    };

}
