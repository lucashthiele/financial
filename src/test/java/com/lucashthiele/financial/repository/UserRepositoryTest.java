package com.lucashthiele.financial.repository;

import com.lucashthiele.financial.AbstractPostgreSQLTestContainerIT;
import com.lucashthiele.financial.models.user.User;
import com.lucashthiele.financial.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.time.LocalDate;
import java.util.NoSuchElementException;


public class UserRepositoryTest extends AbstractPostgreSQLTestContainerIT {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private User user;

    @BeforeEach
    public void setupData() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "USERS");

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

    @Test
    public void UserRepository_findByEmail_ReturnsUser() {
        userRepository.save(this.user);

        var retrievedUser = userRepository.findByEmail(this.user.getEmail());

        Assertions.assertThat(retrievedUser).isEqualTo(this.user);
    }

    @Test
    public void UserRepository_findByPasswordRecoveryCode_ReturnsUser() {
        var recoveryCode = 12345678;
        this.user.setPasswordRecoveryCode(recoveryCode);
        userRepository.save(this.user);

        var retrievedUser = userRepository.findByPasswordRecoveryCode(recoveryCode);

        Assertions.assertThat(retrievedUser).isEqualTo(this.user);
    }
}
