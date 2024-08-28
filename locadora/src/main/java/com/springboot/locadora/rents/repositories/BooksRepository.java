package com.springboot.locadora.rents.repositories;

import com.springboot.locadora.books.entities.BooksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends JpaRepository<BooksEntity, Integer > {
    UserDetails findByName(String name);
}