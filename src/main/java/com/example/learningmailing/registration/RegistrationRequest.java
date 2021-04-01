package com.example.learningmailing.registration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class RegistrationRequest {
    public String name;
    public String surname;
    public String email;
    public String password;
    public LocalDate birth;

    @Override
    public String toString() {
        return "RegistrationRequest{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", birth=" + birth +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return toString().equals(o.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, email, password, birth);
    }
}
