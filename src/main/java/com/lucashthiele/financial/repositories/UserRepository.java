package com.lucashthiele.financial.repositories;

import com.lucashthiele.financial.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    User findByPasswordRecoveryCode(Integer passwordRecoveryCode);
}
