package socialapp.userservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity {

    @JsonIgnore
    private LocalDateTime createdTime;
    @JsonIgnore
    private LocalDateTime lastModifiedTime;

    @PrePersist
    void prePersist(){
        this.createdTime = LocalDateTime.now();
    }

    @PreUpdate
    void preUpdate(){
        this.lastModifiedTime = LocalDateTime.now();
    }
}
