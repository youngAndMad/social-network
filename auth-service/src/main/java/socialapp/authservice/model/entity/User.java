package socialapp.authservice.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name = "users")
@Setter
@JsonIgnoreProperties(
        { "password" , "otp" , "otpCreationTime" }
)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String tag;
    private String email;
    private String phone;
    private Boolean emailVerified;
    private Integer otp;
    private LocalDateTime otpCreationTime;

    @OneToMany(mappedBy = "user")
    private List<Role> roles;
}
