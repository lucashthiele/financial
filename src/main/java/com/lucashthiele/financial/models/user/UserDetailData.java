package com.lucashthiele.financial.models.user;

import java.time.LocalDate;

public record UserDetailData(Integer id, String email, String firstName, String surname, LocalDate birthDate) {
    public UserDetailData(User user) {
        this(user.getId(), user.getEmail(), user.getFirstName(), user.getSurname(), user.getBirthDate());
    }
}
