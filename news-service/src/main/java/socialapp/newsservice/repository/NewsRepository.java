package socialapp.newsservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import socialapp.newsservice.model.entity.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
}