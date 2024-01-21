package socialapp.userservice.repository.neo4j;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import socialapp.userservice.model.neo4j.FriendWrapper;

@Repository
public interface FriendRepository extends Neo4jRepository<FriendWrapper,String> {
}
