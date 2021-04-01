package com.example.learningmailing.confirmation;

import com.example.learningmailing.user.User;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository repository;

    @Autowired
    public ConfirmationTokenService(ConfirmationTokenRepository repository) {
        this.repository = repository;
    }

    public ConfirmationToken create(User user) {
        if(repository.findByUserId(user.getId()).isPresent()) throw new IllegalStateException("A token already exists for this user!");

        var token = new ConfirmationToken(UUID.randomUUID().toString(), user);

        repository.save(token);

        return token;
    }

//    public Boolean checkTokenExists(String token) {
//        return repository.checkTokenExists(token);
//    }

    public Optional<ConfirmationToken> getToken(String token) {
        return repository.getToken(token);
    }

    public void remove(ConfirmationToken tokenObject) {
        repository.delete(tokenObject);
    }

    private void clearExpiredTokens() {
        repository.deleteAllExpiredBefore(LocalDateTime.now());
    }
}
