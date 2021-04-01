package com.example.learningmailing.confirmation;

import com.example.learningmailing.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@Transactional
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    @Query("SELECT t FROM confirmation_tokens t WHERE t.user.id = ?1")
    public Optional<ConfirmationToken> findByUserId(Long id);

//    @Query("SELECT count(t) FROM confirmation_tokens t WHERE t.token = ?1")
//    public Boolean checkTokenExists(String token);

    @Query("SELECT t FROM confirmation_tokens t WHERE t.token = ?1")
    public Optional<ConfirmationToken> getToken(String token);

    @Modifying
    @Query("DELETE FROM confirmation_tokens t WHERE t.validUntil <= ?1")
    void deleteAllExpiredBefore(LocalDateTime now);
}
