package socialapp.authservice.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import socialapp.authservice.model.enums.AppRole;

import java.util.List;

@Entity
@Getter
@Setter
public class Role extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private AppRole role;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Authority> authorities;
}
