package socialapp.ktuserservice.model.entity

import jakarta.persistence.*
import socialapp.ktuserservice.model.enums.Gender
import java.time.LocalDate

@Entity
@Table(name = "users")
class User : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var givenName: String? = null
    var preferredUsername: String? = null
    var familyName: String? = null
    var email: String? = null

    var birthDate: LocalDate? = null
    var avatar: String? = null

    @Enumerated(EnumType.STRING)
    var gender: Gender? = null

    @OneToOne
    @JoinColumn
    var address: Address? =null
}