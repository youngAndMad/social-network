package socialapp.ktuserservice.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import socialapp.ktuserservice.model.entity.BaseEntity
import socialapp.ktuserservice.model.enums.RelationStatus

@Entity
class Friendship: Relation() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}