package socialapp.newsservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import socialapp.newsservice.model.entity.EmailSending;

import java.util.List;

@Repository
public interface EmailSendingRepository extends JpaRepository<EmailSending, Long> {

//    @Query(
//            value = """
//
//                    """
//    )
//    List<EmailSending> test();

}
