package com.example.learningmailing.confirmation;

import com.example.learningmailing.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name="confirmation_tokens")
@Table(name="confirmation_tokens")
public class ConfirmationToken {
    final static int durationInMinutes = 15;

    @Id
    @SequenceGenerator(name="sequence_token", sequenceName = "sequence_token", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_token")
    private Long id;

    @Column(name="token", nullable = false, unique = true, columnDefinition = "TEXT")
    private String token;

    @OneToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name="valid_until", nullable = false)
    private LocalDateTime validUntil;

    public ConfirmationToken() {
        createdAt = LocalDateTime.now();
        validUntil = createdAt.plusMinutes(durationInMinutes);
    }

    public ConfirmationToken(String token, User user) {
        this.token = token;
        this.user = user;
        createdAt = LocalDateTime.now();
        validUntil = createdAt.plusMinutes(durationInMinutes);
    }

    public ConfirmationToken(Long id, String token, User user, LocalDateTime createdAt, LocalDateTime validUntil) {
        this.id = id;
        this.token = token;
        this.user = user;
        this.createdAt = createdAt;
        this.validUntil = validUntil;
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getValidUntil() {
        return validUntil;
    }

    public User getUser() {
        return user;
    }
}
