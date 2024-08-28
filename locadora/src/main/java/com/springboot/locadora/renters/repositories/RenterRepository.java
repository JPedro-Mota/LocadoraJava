package com.springboot.locadora.renters.repositories;

import com.springboot.locadora.renters.entities.RenterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;


@Repository
public interface RenterRepository extends JpaRepository<RenterEntity, Integer > {
    UserDetails findByName(String name);
    RenterEntity findByEmail(String email);
}
