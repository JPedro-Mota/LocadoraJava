package com.springboot.locadora.rents.repositories;

import com.springboot.locadora.rents.entities.RentsEntity;
import com.springboot.locadora.rents.enums.RentStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentRepository extends JpaRepository<RentsEntity, Integer> {
    boolean existsByBookIdAndStatus(int bookId, RentStatusEnum status);
    boolean existsByRenterIdAndStatus(int renterId, RentStatusEnum status);
}