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
    public ResponseEntity<PublisherEntity> save(@Valid @RequestBody CreatePublisherRecordDTO createPublisherRecordDTO) {
        return publisherServices.create(createPublisherRecordDTO);
    }

    @GetMapping("/publisher")
    public ResponseEntity<List<PublisherRecordDTO>> getAllPublisher() {
        List<PublisherEntity> publishers = publisherServices.findAll();
        List<PublisherRecordDTO> publisherDTOs = publisherMapper.toPublisherReponseList(publishers);
        return ResponseEntity.status(HttpStatus.OK).body(publisherDTOs);
    }

    @GetMapping("/publisher/{id}")
    public ResponseEntity<PublisherRecordDTO> getById(@PathVariable(value = "id") int id) {
        return ResponseEntity.status(HttpStatus.OK).body(publisherMapper.toPublisherResponse(publisherServices.findById(id).get()));
    }

    @PutMapping("/publisher/{id}")
    public ResponseEntity<Object> update (@PathVariable(value="id") int id, @RequestBody @Valid UpdatePublisherRecordDTO updatePublisherRecordDTO){
        return publisherServices.update(id, updatePublisherRecordDTO);
    }

    @DeleteMapping("/publisher/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value="id") int id){
        return publisherServices.delete(id);
    }
}