package com.example.learningmailing.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
@Transactional(readOnly = false)
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM users u WHERE u.validated = True")
    public List<User> getAllValidated();

    @Modifying
    @Query("UPDATE users u SET u.validated = True WHERE u.id = ?1")
    public void validateUser(Long id);

    @Query("SELECT u FROM users u WHERE u.email = ?1")
    public Optional<User> findByEmail(String email);
}
