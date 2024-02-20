package socialapp.channelservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import socialapp.channelservice.model.entity.Channel;

@Repository
public interface ChannelRepository extends MongoRepository<Channel, String> {
}
