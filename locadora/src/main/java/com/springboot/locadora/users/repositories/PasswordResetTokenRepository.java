package com.springboot.locadora.users.repositories;

import com.springboot.locadora.users.entities.PasswordResetToken;
import com.springboot.locadora.users.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
    PasswordResetToken findByUser(UserEntity user);
}
