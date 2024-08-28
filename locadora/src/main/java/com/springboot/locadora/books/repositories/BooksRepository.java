package com.springboot.locadora.books.repositories;

import com.springboot.locadora.books.entities.BooksEntity;
import com.springboot.locadora.publisher.entities.PublisherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<BooksEntity, Integer > {
    UserDetails findByName(String name);
    List<BooksEntity> findAllByIsDeletedFalse();
    BooksEntity findByEmail(String email);
    BooksEntity findBySite(String site);
    BooksEntity findByTelephone(String telephone);
}