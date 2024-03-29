package socialapp.chatservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import socialapp.chatservice.model.entity.Chat;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ChatRepository extends MongoRepository<Chat,String> {

}
