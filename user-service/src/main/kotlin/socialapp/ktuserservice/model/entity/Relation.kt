package socialapp.ktuserservice.model.entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.MappedSuperclass

@MappedSuperclass
abstract class Relation : BaseEntity() {

    @ManyToOne
    @JoinColumn
    var receiver: User? = null

    @ManyToOne
    @JoinColumn
    var sender: User? = null

}