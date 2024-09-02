package com.springboot.locadora.publisher.controllers;

import com.springboot.locadora.publisher.DTOs.CreatePublisherRecordDTO;
import com.springboot.locadora.publisher.DTOs.PublisherRecordDTO;
import com.springboot.locadora.publisher.DTOs.UpdatePublisherRecordDTO;
import com.springboot.locadora.publisher.entities.PublisherEntity;
import com.springboot.locadora.publisher.mappers.PublisherMapper;
import com.springboot.locadora.publisher.repositories.PublisherRepository;
import com.springboot.locadora.publisher.services.PublisherServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class PublisherController {

    @Autowired
    PublisherRepository publisherRepository;

    @Autowired
    PublisherServices publisherServices;

    @Autowired
    PublisherMapper publisherMapper;

    @PostMapping("/publisher")
    public ResponseEntity<PublisherEntity> save(@Valid CreatePublisherRecordDTO createPublisherRecordDTO){
        return publisherServices.create(createPublisherRecordDTO);
    }

    @GetMapping("/publisher")
    public ResponseEntity<List<PublisherEntity>> getAllPublisher(){
        return ResponseEntity.status(HttpStatus.OK).body(publisherRepository.findAll());
    }

    @GetMapping("/publisher/{id}")
    public ResponseEntity<PublisherRecordDTO> getById(@PathVariable(value = "id") int id) {
        return ResponseEntity.status(HttpStatus.OK).body(publisherMapper.toPublisherResponse(publisherServices.findById(id).get()));
    }

    @PutMapping("/publisher/{id}")
    public ResponseEntity<Object> update (@PathVariable(value="id") int id, @Valid UpdatePublisherRecordDTO updatePublisherRecordDTO){
        return publisherServices.update(id, updatePublisherRecordDTO);
    }

    @DeleteMapping("/publisher/{id}")
    public ResponseEntity<Object> deletePublisher(@PathVariable(value="id") int id){
        return publisherServices.delete(id);
    }
}