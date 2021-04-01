package com.example.learningmailing.registration;

import com.example.learningmailing.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class RegistrationController {
    private final RegistrationService service;

    @Autowired
    public RegistrationController(RegistrationService service) {
        this.service = service;
    }

    @PostMapping
    public String register(@RequestBody RegistrationRequest request) {
        return service.register(request);
    }

    @GetMapping("/validate")
    public User validate(@RequestParam String token) {
        //if(!service.confirmationTokenService.checkTokenExists(token)) return "There is no token " + token;
        return service.validate(token);
    }
}
