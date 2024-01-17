package socialapp.newsservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import socialapp.newsservice.entity.FileMetaData;

@Repository
public interface FileMetaDataRepository extends JpaRepository<FileMetaData,Long> {
}
