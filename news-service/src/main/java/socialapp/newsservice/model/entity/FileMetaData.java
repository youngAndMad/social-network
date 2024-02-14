package socialapp.newsservice.model.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "file_meta_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileMetaData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String fileId;
    private String extension;

    @ManyToOne
    @JoinColumn
    @JsonProperty("newsId")
    private News news;

    @JsonGetter("newsId")
    public Long getNewsId() {
        return news.getId();
    }

    public FileMetaData(String url, String fileId, News news,String extension) {
        this.url = url;
        this.extension=extension;
        this.fileId = fileId;
        this.news = news;
    }
}
