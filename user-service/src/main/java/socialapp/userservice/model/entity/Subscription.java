package socialapp.userservice.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import socialapp.userservice.model.enums.RelationStatus;

@Entity
@Getter
@Setter
public class Subscription extends Relation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Override
    public RelationStatus getRelationStatus() {
        return RelationStatus.SUBSCRIPTION;
    }
}
