package com.lucashthiele.financial.services;

import com.lucashthiele.financial.models.user.CreateUserData;
import com.lucashthiele.financial.models.user.UpdateUserData;
import com.lucashthiele.financial.models.user.User;
import com.lucashthiele.financial.models.user.UserDetailData;
import com.lucashthiele.financial.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Integer createUser(CreateUserData data) {
        var user = new User(
                null,
                data.email(),
                encryptPassword(data.password()),
                data.firstName(),
                data.surname(),
                data.birthDate(),
                null
        );
        userRepository.save(user);
        return user.getId();
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow();
    }

    public void updatePasswordRecoveryCode(Integer code, String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setPasswordRecoveryCode(code);
            userRepository.save(user);
        } else {
            throw new RuntimeException("Não foi possível localizar um usuário com o email  " + email);
        }
    }

    public User getByPasswordRecoveryCode(Integer code) {
        return userRepository.findByPasswordRecoveryCode(code);
    }

    public void updatePassword(User user, String newPassword) {
        String encodedPassword = encryptPassword(newPassword);
        user.setPassword(encodedPassword);

        user.setPasswordRecoveryCode(null);
        userRepository.save(user);
    }

    public UserDetailData updateUser(User user, UpdateUserData data) {
        user.setEmail(data.email());
        if (data.password() != null) user.setPassword(this.encryptPassword(data.password()));
        user.setFirstName(data.firstName());
        user.setSurname(data.surname());
        user.setBirthDate(data.birthDate());

        userRepository.save(user);

        return new UserDetailData(user);
    }

    public Integer generateRandomToken() {
        var secureRandom = new SecureRandom();
        return Math.abs(secureRandom.nextInt());
    }

    private String encryptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

}
