package com.springboot.locadora.publisher.repositories;

import com.springboot.locadora.publisher.entities.PublisherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PublisherRepository extends JpaRepository<PublisherEntity, Integer > {
    PublisherEntity findByName(String name);
    List<PublisherEntity> findAllByIsDeletedFalse();
    PublisherEntity findByEmail(String email);
    PublisherEntity findBySite(String site);
    PublisherEntity findByTelephone(int telephone);
}