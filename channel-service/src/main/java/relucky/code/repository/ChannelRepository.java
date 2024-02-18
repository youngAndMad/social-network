package relucky.code.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import relucky.code.model.entity.Channel;

@Repository
public interface ChannelRepository extends MongoRepository<Channel, String> {
}
