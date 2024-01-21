package socialapp.newsservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import socialapp.newsservice.entity.FileMetaData;
import socialapp.newsservice.entity.News;

import java.util.List;

@Repository
public interface FileMetaDataRepository extends JpaRepository<FileMetaData,Long> {
    List<FileMetaData> findAllByNews(News news);
}