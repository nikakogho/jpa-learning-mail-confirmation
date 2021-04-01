package com.example.learningmailing.registration;

import com.example.learningmailing.confirmation.ConfirmationTokenService;
import com.example.learningmailing.email.EmailService;
import com.example.learningmailing.user.User;
import com.example.learningmailing.user.UserRank;
import com.example.learningmailing.user.UserRepository;
import com.example.learningmailing.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.EventRecodingLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {
    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;

    private final Logger logger = LoggerFactory.getLogger(RegistrationService.class);

    @Autowired
    public RegistrationService(UserService userService, ConfirmationTokenService confirmationTokenService, EmailService emailService) {
        this.userService = userService;
        this.confirmationTokenService = confirmationTokenService;
        this.emailService = emailService;
    }

    @Transactional
    @Modifying
    public String register(RegistrationRequest request) {
        var user= userService.register(request);
        var token = confirmationTokenService.create(user);

        // logger.debug("Generated token: " + token.getToken() + " \n Will be valid until " + token.getValidUntil().toString());

        return emailService.send(user.getEmail(), getEmailText(token.getToken()));
    }

    private String getEmailText(String token) {
        String host = "localhost";
        String port = "8080";

        String url = "http://" + host + ":" + port + "/register/validate?token=" + token;

        return "You can recover your email by clicking <a href='" + url + "'>this link<a/>";
    }

    @Transactional
    @Modifying
    public User validate(String token) {
        var tokenObject = confirmationTokenService.getToken(token)
                .orElseThrow(() -> new IllegalStateException("There is no token " + token));

        var user = tokenObject.getUser();

        userService.validate(user.getId());

        confirmationTokenService.remove(tokenObject);

        return user;
    }
}
