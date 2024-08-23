package com.springboot.locadora.publisher.services;

import com.springboot.locadora.publisher.DTOs.PublisherRecordDto;
import com.springboot.locadora.publisher.entities.PublisherEntity;
import com.springboot.locadora.publisher.repositories.PublisherRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
public class PublisherServices {

    @Autowired
    private PublisherRepository publisherRepository;

    public ResponseEntity<PublisherEntity> savePublisher(@RequestBody @Valid PublisherRecordDto publisherRecordDto){
        var publisherEntity = new PublisherEntity();
        BeanUtils.copyProperties(publisherRecordDto, publisherEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(publisherRepository.save(publisherEntity));
    }

    public ResponseEntity<List<PublisherEntity>> getAllPublisher(){
        return ResponseEntity.status(HttpStatus.OK).body(publisherRepository.findAll());
    }

    public ResponseEntity<Object> getOnePublisher(int id){
        Optional<PublisherEntity> publisherO = publisherRepository.findById(id);
        if(publisherO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Publisher not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(publisherO.get());
    }

    public ResponseEntity<Object> updatePublisher(int id, @RequestBody @Valid PublisherRecordDto publisherRecordDto){
        Optional<PublisherEntity> publisherO = publisherRepository.findById(id);
        if(publisherO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Publisher not found.");
        }
        var publisherEntity = publisherO.get();
        BeanUtils.copyProperties(publisherRecordDto, publisherEntity);
        return ResponseEntity.status(HttpStatus.OK).body(publisherRepository.save(publisherEntity));
    }

    public ResponseEntity<Object> deletePublisher(int id){
        Optional<PublisherEntity> publisherO = publisherRepository.findById(id);
        if(publisherO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Publisher not found.");
        }
        publisherRepository.delete(publisherO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Publisher deleted successfully.");
    }
}