package socialapp.userservice.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import socialapp.userservice.model.enums.Gender;

import java.time.LocalDate;

import static socialapp.userservice.common.AppConstants.USER_INDEX;

@Entity
@Getter
@Setter
@Table(name = "users")
@Document(indexName = USER_INDEX)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;
    @Transient
    private Integer age = LocalDate.now().getYear() - birthDate.getYear();
    private String avatar;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    @OneToOne
    @JoinColumn
    private Address address;
}
