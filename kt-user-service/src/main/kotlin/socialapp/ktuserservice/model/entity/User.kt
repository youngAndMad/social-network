package socialapp.ktuserservice.model.entity

import jakarta.persistence.*
import org.springframework.data.elasticsearch.annotations.Document
import socialapp.ktuserservice.common.AppConstants.Companion.USER_INDEX
import socialapp.ktuserservice.model.enums.Gender
import java.time.LocalDate

@Entity
@Table(name = "users")
@Document(indexName = USER_INDEX)
class User : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var firstName: String? = null
    var lastName: String? = null
    var email: String? = null
    var birthDate: LocalDate? = null
    var avatar: String? = null

    @Enumerated(EnumType.STRING)
    var gender: Gender? = null

    @OneToOne
    @JoinColumn
    var address: Address? = null
}