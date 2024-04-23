package com.lucashthiele.financial.models.user;

public record PasswordRecoveryData(Integer userId, String token) {
    public PasswordRecoveryData(User user, String token) {
        this(user.getId(), token);
    }
}
