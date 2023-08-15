package com.lucashthiele.financial.services;

import com.lucashthiele.financial.models.user.CreateUserData;
import com.lucashthiele.financial.models.user.User;
import com.lucashthiele.financial.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public String createUser(CreateUserData data) {
        var user = new User(null, data.email(), encryptPassword(data.password()), data.firstName(), data.surname(), data.birthDate(), null);
        repository.save(user);
        return user.getId();
    }

    public User getUserById(String id) {
        return repository.findById(id).orElseThrow();
    }

    public void updatePasswordRecoveryCode(Integer code, String email) {
        User user = repository.findByEmail(email);
        if (user != null) {
            user.setPasswordRecoveryCode(code);
            repository.save(user);
        } else {
            throw new RuntimeException("Não foi possível localizar um usuário com o email  " + email);
        }
    }

    public User getByPasswordRecoveryCode(Integer code) {
        return repository.findByPasswordRecoveryCode(code);
    }

    public void updatePassword(User user, String newPassword) {
        String encodedPassword = encryptPassword(newPassword);
        user.setPassword(encodedPassword);

        user.setPasswordRecoveryCode(null);
        repository.save(user);
    }

    private String encryptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
