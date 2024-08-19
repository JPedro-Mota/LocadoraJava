package com.springboot.locadora.publisher.repositories;

import com.springboot.locadora.publisher.entities.PublisherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PublisherRepository extends JpaRepository<PublisherEntity, Integer > {
}
