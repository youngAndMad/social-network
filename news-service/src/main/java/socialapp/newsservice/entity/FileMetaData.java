package socialapp.newsservice.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "file_meta_data")
@Getter
@Setter
@NoArgsConstructor
public class FileMetaData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String fileId;

    @ManyToOne
    @JoinColumn
    @JsonProperty("newsId")
    private News news;

    @JsonGetter("newsId")
    public Long getNewsId() {
        return news.getId();
    }

    public FileMetaData(String url, String fileId, News news) {
        this.url = url;
        this.fileId = fileId;
        this.news = news;
    }
}
