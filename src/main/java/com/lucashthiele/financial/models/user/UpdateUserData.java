package com.lucashthiele.financial.models.user;

import java.time.LocalDate;

public record UpdateUserData(String email, String password, String firstName, String surname, LocalDate birthDate) {
}
