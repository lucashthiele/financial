package com.lucashthiele.financial.repository;

import com.lucashthiele.financial.models.user.User;
import com.lucashthiele.financial.repositories.UserRepository;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@DataJpaTest
@AutoConfigureEmbeddedDatabase
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    public void setupData() {
        this.user = User.builder()
                .id(null)
                .email("test@test.com")
                .password("12345678")
                .firstName("First Name Test")
                .surname("Surname Test")
                .birthDate(LocalDate.parse("2000-01-01"))
                .build();
    }

    @Test
    public void UserRepository_SaveAll_ReturnsSavedUser() {
        var savedUser = userRepository.save(this.user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }

    /**
     * TODO - add new tests scenarios, need to evaluate all usages od the repository
     *  - Methods
     *      findByEmail
     *      findByPasswordRecoveryCode
     */

    @Test
    public void UserRepository_findById_ReturnUser() {
        var savedUser = userRepository.save(this.user);

        var retrievedUser = userRepository.findById(savedUser.getId()).get();

        Assertions.assertThat(retrievedUser).isEqualTo(this.user);
    }

    @Test
    public void UserRepository_findById_ThrowNotFoundException() {
        Assertions.assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> {
                    userRepository.findById(1).orElseThrow();
                });
    }
}
