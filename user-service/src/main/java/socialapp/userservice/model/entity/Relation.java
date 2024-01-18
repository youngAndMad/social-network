package socialapp.userservice.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import socialapp.userservice.model.enums.RelationStatus;

@MappedSuperclass
@Getter
@Setter
public abstract class Relation extends BaseEntity{
    @ManyToOne
    @JoinColumn
    private User receiver;
    @ManyToOne
    @JoinColumn
    private User sender;
    public abstract RelationStatus getRelationStatus();
}

