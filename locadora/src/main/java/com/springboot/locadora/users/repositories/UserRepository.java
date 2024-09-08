package com.springboot.locadora.users.repositories;

import com.springboot.locadora.users.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer > {
    UserEntity findByName(String name);
    UserEntity findByEmail(String email);

    @Query("SELECT u FROM UserEntity u WHERE LOWER(REPLACE(u.name, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(:name, ' ', ''), '%'))")
    List<UserEntity> findAllByName(@Param("name") String name);
}
