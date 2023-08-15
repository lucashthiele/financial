package com.lucashthiele.financial.repositories;

import com.lucashthiele.financial.models.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User, String> {

    @Query("{email:'?0'}")
    User findByEmail(String email);

    @Query("{passwordRecoveryCode:?0}")
    User findByPasswordRecoveryCode(Integer code);
}
