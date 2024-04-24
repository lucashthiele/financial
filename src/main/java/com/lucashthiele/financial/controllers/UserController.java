package com.lucashthiele.financial.controllers;

import com.lucashthiele.financial.models.user.*;
import com.lucashthiele.financial.services.KafkaService;
import com.lucashthiele.financial.services.TokenService;
import com.lucashthiele.financial.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.SecureRandom;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private KafkaService kafkaService;

    @Transactional
    @PostMapping
    public ResponseEntity<ResponseEntity.BodyBuilder> create(@RequestBody CreateUserData data, UriComponentsBuilder uriBuilder) {
        var createdUserId = userService.createUser(data);

        var uri = uriBuilder.path("/user/{id}").buildAndExpand(createdUserId).toUri();
        return ResponseEntity.created(uri).build();
    }

    @Transactional
    @PutMapping("{id}")
    public ResponseEntity<UserDetailData> update(@PathVariable Integer id, @RequestBody UpdateUserData data, UriComponentsBuilder uriBuilder) {
        var user = userService.getUserById(id);
        var updatedUser = userService.updateUser(user, data);

        var uri = uriBuilder.path("/user/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDetailData> read(@PathVariable Integer id) {
        var user = userService.getUserById(id);

        return ResponseEntity.ok(new UserDetailData(user));
    }

    @Transactional
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordData data) {
        var email = data.email();
        var recoveryToken = generateRandomToken();

        userService.updatePasswordRecoveryCode(recoveryToken, email);
        kafkaService.produceEmailSenderMessage(recoveryToken, email);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/validate-code")
    public ResponseEntity<PasswordRecoveryData> validateRecoveryCode(@RequestBody ValidateRecoveryCodeData data) {
        var user = userService.getByPasswordRecoveryCode(data.code());
        if (user != null){
            var token = tokenService.generateToken(user);
            return ResponseEntity.ok(new PasswordRecoveryData(user, token));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    @PutMapping("/{id}/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordData data, @PathVariable Integer id) {
        var user = userService.getUserById(id);
        userService.updatePassword(user, data.password());

        return ResponseEntity.ok().build();
    }

    private Integer generateRandomToken() {
        var secureRandom = new SecureRandom();
        return Math.abs(secureRandom.nextInt());
    }

}
