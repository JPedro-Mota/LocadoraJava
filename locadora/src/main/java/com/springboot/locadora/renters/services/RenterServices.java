package com.springboot.locadora.renters.services;

import com.springboot.locadora.renters.DTOs.CreateRenterRecordDTO;
import com.springboot.locadora.renters.DTOs.UpdateRenterRecordDTO;
import com.springboot.locadora.renters.entities.RenterEntity;
import com.springboot.locadora.renters.repositories.RenterRepository;
import com.springboot.locadora.renters.validations.RenterValidation;
import com.springboot.locadora.users.services.ModelNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RenterServices {

    @Autowired
    private RenterRepository renterRepository;

    @Autowired
    private RenterValidation renterValidation;

    public ResponseEntity<Void> create(@Valid CreateRenterRecordDTO data){

        renterValidation.validateEmail(data);
        renterValidation.validateName(data);

        RenterEntity newRenter = new RenterEntity(data.name(), data.email(), data.cpf(), data.telephone(), data.address());
        renterRepository.save(newRenter);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    public List<RenterEntity> findAll(){
        List<RenterEntity> renters = renterRepository.findAllByIsDeletedFalse();
        if (renters.isEmpty()) throw new ModelNotFoundException();
        return renters;
    }

    public Optional<RenterEntity> findById(int id) {
        return renterRepository.findById(id);
    }

    public ResponseEntity<Object> update(int id, @Valid UpdateRenterRecordDTO updateRenterRecordDTO){
        Optional<RenterEntity> response = renterRepository.findById(id);
        if(response.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Renter not found");

        renterValidation.validateUpdateEmail(updateRenterRecordDTO);
        renterValidation.validateUpdateName(updateRenterRecordDTO);
        RenterEntity renterEntity = response.get();
        BeanUtils.copyProperties(updateRenterRecordDTO, renterEntity);
        return ResponseEntity.status(HttpStatus.OK).body(renterRepository.save(renterEntity));
    }

    public ResponseEntity<Object> delete(int id){
        Optional<RenterEntity> response = renterRepository.findById(id);
        if(response.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Renter not found");
        }

        renterValidation.validateDeleteRenter(id);

        RenterEntity renter = response.get();

        renter.setDeleted(true);

        renterRepository.save(renter);

        return ResponseEntity.status(HttpStatus.OK).body("Renter deleted successfully");
    }

}
