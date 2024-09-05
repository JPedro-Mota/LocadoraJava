package com.springboot.locadora.publisher.services;

import com.springboot.locadora.publisher.DTOs.CreatePublisherRecordDTO;
import com.springboot.locadora.publisher.DTOs.PublisherRecordDTO;
import com.springboot.locadora.publisher.DTOs.UpdatePublisherRecordDTO;
import com.springboot.locadora.publisher.entities.PublisherEntity;
import com.springboot.locadora.publisher.repositories.PublisherRepository;
import com.springboot.locadora.users.services.ModelNotFoundException;
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

    public ResponseEntity<PublisherEntity> create(@Valid CreatePublisherRecordDTO data){

        PublisherEntity newPublisher = new PublisherEntity(data.name(), data.email(), data.telephone(), data.site());
        PublisherEntity savedPublisher = publisherRepository.save(newPublisher);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedPublisher);
    }

    public List<PublisherEntity> findAll(){
       List<PublisherEntity> publishers = publisherRepository.findAllByIsDeletedFalse();
       if (publishers.isEmpty()) throw new ModelNotFoundException();
       return publishers;
    }

    public Optional<PublisherEntity> findById(int id){
        return publisherRepository.findById(id);
    }

    public ResponseEntity<Object> update(int id, @Valid UpdatePublisherRecordDTO updatePublisherRecordDto){
        Optional<PublisherEntity> response = publisherRepository.findById(id);
        if(response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Publisher not found.");
        }
        var publisherEntity = response.get();
        BeanUtils.copyProperties(updatePublisherRecordDto, publisherEntity);
        return ResponseEntity.status(HttpStatus.OK).body(publisherRepository.save(publisherEntity));
    }

    public ResponseEntity<Object> delete(int id){
        Optional<PublisherEntity> response = publisherRepository.findById(id);
        if(response.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Publisher not found.");
        }
        PublisherEntity publisher = response.get();
        publisher.setDeleted(true);
        publisherRepository.save(publisher);
        return ResponseEntity.status(HttpStatus.OK).body("Publisher deleted sucessfully.");
    }
}