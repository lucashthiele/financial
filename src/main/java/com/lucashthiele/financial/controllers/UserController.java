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
    UserService service;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private KafkaService kafkaService;

    @PostMapping
    @Transactional
    public ResponseEntity<ResponseEntity.BodyBuilder> create(@RequestBody CreateUserData data, UriComponentsBuilder uriBuilder) {
        var createdUserId = service.createUser(data);

        var uri = uriBuilder.path("/user/{id}").buildAndExpand(createdUserId).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDetailData> read(@PathVariable String id) {
        var user = service.getUserById(id);

        return ResponseEntity.ok(new UserDetailData(user));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordData data) {
        var email = data.email();
        var recoveryToken = generateRandomToken();

        service.updatePasswordRecoveryCode(recoveryToken, email);
        kafkaService.produceEmailSenderMessage(recoveryToken, email);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/validate-code")
    public ResponseEntity<PasswordRecoveryData> validateRecoveryCode(@RequestBody ValidateRecoveryCodeData data) {
        var user = service.getByPasswordRecoveryCode(data.code());
        if (user != null){
            var token = tokenService.generateToken(user);
            return ResponseEntity.ok(new PasswordRecoveryData(user, token));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordData data, @PathVariable String id) {
        var user = service.getUserById(id);
        service.updatePassword(user, data.password());

        return ResponseEntity.ok().build();
    }

    private Integer generateRandomToken() {
        var secureRandom = new SecureRandom();
        return Math.abs(secureRandom.nextInt());
    }

}
