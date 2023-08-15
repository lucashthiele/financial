package com.lucashthiele.financial.models.user;

import java.time.LocalDate;

public record CreateUserData(String email, String password, String firstName, String surname, LocalDate birthDate) {
}
