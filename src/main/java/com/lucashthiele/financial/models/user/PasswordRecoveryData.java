package com.lucashthiele.financial.models.user;

public record PasswordRecoveryData(String userId, String token) {
    public PasswordRecoveryData(User user, String token) {
        this(user.getId(), token);
    }
}
