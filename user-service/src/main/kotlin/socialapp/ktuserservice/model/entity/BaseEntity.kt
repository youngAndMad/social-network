package socialapp.ktuserservice.model.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import org.springframework.data.annotation.Transient
import java.time.LocalDateTime

@MappedSuperclass
open class BaseEntity {
    @JsonIgnore
    @Transient
    var createdTime: LocalDateTime? = null
    @JsonIgnore
    @Transient
    var lastModifiedTime: LocalDateTime? = null

    @PrePersist
    fun prePersist() {
        this.createdTime = LocalDateTime.now()
    }

    @PreUpdate
    fun preUpdate() {
        this.lastModifiedTime = LocalDateTime.now()
    }
}