package com.lucashthiele.financial.repository;

import com.lucashthiele.financial.models.user.User;
import com.lucashthiele.financial.repositories.UserRepository;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.time.LocalDate;

@DataJpaTest
@AutoConfigureEmbeddedDatabase
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void UserRepository_SaveAll_ReturnsSavedUser() {
            var user = User.builder()
                    .id(null)
                    .email("test@test.com")
                    .password("12345678")
                    .firstName("First Name Test")
                    .surname("Surname Test")
                    .birthDate(LocalDate.parse("2000-01-01"))
                    .build();

            var savedUser = userRepository.save(user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @AfterEach
    public void cleanDatabaseAfterEachTest(@Autowired JdbcTemplate jdbcTemplate) {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "USERS");
    }
}
