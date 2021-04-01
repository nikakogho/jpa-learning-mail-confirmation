package com.example.learningmailing.user;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/show")
    public List<User> showAllUsers(@RequestParam(defaultValue = "false") String validated) {
        return service.getAll(validated.equals("true"));
    }

//    @GetMapping("/validate")
//    public User validateUser(@RequestParam Long id) {
//        return service.validate(id);
//    }
}
