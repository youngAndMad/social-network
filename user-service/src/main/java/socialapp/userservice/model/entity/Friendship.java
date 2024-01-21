package socialapp.userservice.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import socialapp.userservice.model.enums.RelationStatus;

@Entity
@Getter
@Setter
public class Friendship extends Relation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Override
    public RelationStatus getRelationStatus() {
        return RelationStatus.FRIENDSHIP;
    }
}
