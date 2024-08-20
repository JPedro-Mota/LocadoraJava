package com.springboot.locadora.publisher.controllers;

import com.springboot.locadora.publisher.DTOs.PublisherRecordDto;
import com.springboot.locadora.publisher.entities.PublisherEntity;
import com.springboot.locadora.publisher.repositories.PublisherRepository;
import com.springboot.locadora.publisher.services.PublisherServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PublisherController {

    @Autowired
    PublisherRepository publisherRepository;

    @Autowired
    PublisherServices publisherServices;

    @PostMapping("/publisher")
    public ResponseEntity<PublisherEntity> savePublisher(@RequestBody @Valid PublisherRecordDto publisherRecordDto){
        return publisherServices.savePublisher(publisherRecordDto);
    }

    @GetMapping("/publisher")
    public ResponseEntity<List<PublisherEntity>> getAllPublisher(){
        return ResponseEntity.status(HttpStatus.OK).body(publisherRepository.findAll());
    }

    @GetMapping("/publisher/{id}")
    public ResponseEntity<Object> getOnePublisher(@PathVariable(value="id") int id){
        return publisherServices.getOnePublisher(id);
    }

    @PutMapping("/publisher/{id}")
    public ResponseEntity<Object> updatePublisher(@PathVariable(value="id") int id, @RequestBody @Valid PublisherRecordDto publisherRecordDto){
        return publisherServices.updatePublisher(id, publisherRecordDto);
    }

    @DeleteMapping("/publisher/{id}")
    public ResponseEntity<Object> deletePublisher(@PathVariable(value="id") int id){
        return publisherServices.deletePublisher(id);
    }
}