package com.springboot.locadora.rents.repositories;

import com.springboot.locadora.rents.entities.RentsEntity;
import com.springboot.locadora.rents.enums.RentStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentRepository extends JpaRepository<RentsEntity, Integer> {
    boolean existsByBookIdAndStatus(int bookId, RentStatusEnum status);
    boolean existsByRenterIdAndStatus(int renterId, RentStatusEnum status);
    boolean existsByRenterIdAndBookIdAndStatus(int renterId, int bookId, RentStatusEnum status);
    List<RentsEntity> findAllByStatus(RentStatusEnum status);
    List<RentsEntity> findAllByRenterId(int renterId);
    List<RentsEntity> findAllByRenterIdAndStatus(int renterId, RentStatusEnum status);
    List<RentsEntity> findAllByBookId(int bookId);

}
