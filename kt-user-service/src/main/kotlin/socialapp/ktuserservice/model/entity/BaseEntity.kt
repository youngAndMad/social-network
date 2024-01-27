package socialapp.ktuserservice.model.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import java.time.LocalDateTime

@MappedSuperclass
open class BaseEntity {
    @JsonIgnore
    var createdTime: LocalDateTime? = null
    @JsonIgnore
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