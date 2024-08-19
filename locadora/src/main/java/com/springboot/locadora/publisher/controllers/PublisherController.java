package com.springboot.locadora.publisher.controllers;

import com.springboot.locadora.publisher.DTOs.PublisherRecordDto;
import com.springboot.locadora.publisher.entities.PublisherEntity;
import com.springboot.locadora.publisher.repositories.PublisherRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
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

    @PostMapping("/publisher")
    public ResponseEntity<PublisherEntity> savePublisher(@RequestBody @Valid PublisherRecordDto publisherRecordDto){
        var publisherEntity = new PublisherEntity();
        BeanUtils.copyProperties(publisherRecordDto, publisherEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(publisherRepository.save(publisherEntity));
    }

    @GetMapping("/publisher")
    public ResponseEntity<List<PublisherEntity>> getAllPublisher(){
        return ResponseEntity.status(HttpStatus.OK).body(publisherRepository.findAll());
    }

    @GetMapping("/publisher/{id}")
    public ResponseEntity<Object> getOnePublisher(@PathVariable(value="id") int id){
        Optional<PublisherEntity> publisherO = publisherRepository.findById(id);
        if(publisherO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(publisherO.get());
    }

    @PutMapping("/publisher/{id}")
    public ResponseEntity<Object> updatePublisher(@PathVariable(value="id") int id, @RequestBody @Valid PublisherRecordDto publisherRecordDto){
        Optional<PublisherEntity> publisherO = publisherRepository.findById(id);
        if(publisherO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        var publisherEntity = publisherO.get();
        BeanUtils.copyProperties(publisherRecordDto, publisherEntity);
        return ResponseEntity.status(HttpStatus.OK).body(publisherRepository.save(publisherEntity));
    }

    @DeleteMapping("/publisher/{id}")
    public ResponseEntity<Object> deletePublisher(@PathVariable(value="id") int id){
        Optional<PublisherEntity> publisherO = publisherRepository.findById(id);
        if(publisherO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
        publisherRepository.delete(publisherO.get());
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully.");
    }
}
