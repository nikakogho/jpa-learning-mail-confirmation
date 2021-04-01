package com.example.learningmailing.user;

import org.apache.tomcat.jni.Local;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity(name="users")
@Table(name="users")
public class User implements UserDetails {
    @Id
    @SequenceGenerator(sequenceName = "user_sequence", name = "user_sequence", allocationSize = 1)
    @GeneratedValue(generator = "user_sequence", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "TEXT")
    private String firstName;

    @Column(name = "surname", nullable = false, columnDefinition = "TEXT")
    private String lastName;

    @Column(name = "email", unique = true, nullable = false, columnDefinition = "TEXT")
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="birth", nullable = false)
    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    @Column(name="rank", nullable = false)
    private UserRank rank;

    @Column(name="validated", nullable = false)
    private Boolean validated;

    @Column(name="created_at", nullable = false)
    private final LocalDateTime createdAt;

    public User() {
        createdAt = LocalDateTime.now();
        validated = false;
    }

    public User(String firstName, String lastName, String email, String password, LocalDate birth, UserRank rank) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birth = birth;
        this.rank = rank;
        createdAt = LocalDateTime.now();
        validated = false;
    }

    public User(Long id, String firstName, String lastName, String email, String password, LocalDate birth, UserRank rank, Boolean validated, LocalDateTime createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birth = birth;
        this.rank = rank;
        this.validated = validated;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getValidated() {
        return validated;
    }

    public void setValidated(Boolean validated) {
        this.validated = validated;
    }

    public UserRank getRank() {
        return rank;
    }

    public void setRank(UserRank rank) {
        this.rank = rank;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var authority = new SimpleGrantedAuthority(rank.name());

        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
