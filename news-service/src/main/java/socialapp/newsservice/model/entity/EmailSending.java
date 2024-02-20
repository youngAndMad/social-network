package socialapp.newsservice.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import socialapp.newsservice.model.enums.EmailSendingState;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class EmailSending {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private EmailSendingState state;
    @ManyToOne
    @JoinColumn
    private News news;
    private LocalDateTime lastAttemptTime;
    private String receiverEmail;
}

