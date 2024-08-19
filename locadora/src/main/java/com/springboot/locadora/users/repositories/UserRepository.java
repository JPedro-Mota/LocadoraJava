package com.springboot.locadora.users.repositories;

import com.springboot.locadora.users.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer > {
    UserDetails findByName(String name);
}
