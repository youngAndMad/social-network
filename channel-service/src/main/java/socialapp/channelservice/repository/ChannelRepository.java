package socialapp.channelservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import socialapp.channelservice.model.entity.Channel;

import java.util.Set;

@Repository
public interface ChannelRepository extends MongoRepository<Channel, String> {

    Set<Channel> findByAdminEmail(String adminEmail);

}
