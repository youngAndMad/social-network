package socialapp.newsservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private Boolean emailSending;
    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL)
    private Set<FileMetaData> files;
    private LocalDateTime publishDate;
}
