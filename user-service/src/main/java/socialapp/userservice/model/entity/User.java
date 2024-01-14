package socialapp.userservice.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import socialapp.userservice.model.enums.Gender;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @OneToOne
    @JoinColumn
    private Address address;
}