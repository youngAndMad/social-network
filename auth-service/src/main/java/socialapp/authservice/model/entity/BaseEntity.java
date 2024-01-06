package socialapp.authservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity {

    @CreatedDate
    @Column(name = "created_time", updatable = false)
    private LocalDateTime createdTime;

    @LastModifiedDate
    @Column(name = "last_modified_time", updatable = false)
    private LocalDateTime lastModifiedTime;

    @PostPersist
    void postPersist(){
        this.createdTime = LocalDateTime.now();
    }

    @PostUpdate
    void postUpdate(){
        this.lastModifiedTime = LocalDateTime.now();
    }
}
