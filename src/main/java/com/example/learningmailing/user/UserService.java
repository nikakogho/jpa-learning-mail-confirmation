package com.example.learningmailing.user;

import com.example.learningmailing.registration.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<User> getAll(Boolean validated) {
        if(!validated) return repository.findAll();
        return repository.getAllValidated();
    }

    public User validate(Long id) {
        var user = repository.findById(id).orElseThrow();

        repository.validateUser(id);

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Not found user by email " + email));
    }

    public User register(RegistrationRequest request) {
        if(repository.findByEmail(request.email).isPresent())
            throw new IllegalStateException("User with mail " + request.email + " already exists!");

        String password = bCryptPasswordEncoder.encode(request.password);

        var user = new User(request.name, request.surname, request.email, password, request.birth, UserRank.USER);

        repository.save(user);

        return user;
    }
}
