package com.springboot.locadora.rents.controllers;

import com.springboot.locadora.renters.entities.RenterEntity;
import com.springboot.locadora.rents.DTOs.CreateRentsRecordDTO;
import com.springboot.locadora.rents.DTOs.UpdateRentsRecordDTO;
import com.springboot.locadora.rents.entities.RentsEntity;
import com.springboot.locadora.rents.services.RentServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RentsController {

    @Autowired
    private RentServices rentServices;

    @PostMapping("/rents")
    public ResponseEntity<Void> create(@RequestBody @Valid CreateRentsRecordDTO data) {
        return rentServices.create(data);
    }

    @GetMapping("/rents")
    public ResponseEntity<List<RentsEntity>> getAllRents() {
        return rentServices.findAll();
    }

    @GetMapping("/rents/{id}")
    public ResponseEntity<RenterEntity> getById(@PathVariable(value = "id") int id){
        return ResponseEntity.status(HttpStatus.OK).body((rentServices.findById(id).get()));
    }

    @PutMapping("/rents/{id}")
    public ResponseEntity<Object> update(@PathVariable(value="id") int id, @RequestBody @Valid UpdateRentsRecordDTO updateRentsRecordDTO){
        return RentServices.updateRent(id, updateRentsRecordDTO);
    }

    @DeleteMapping("/rents/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value="id") int id){
        return rentServices.deleteRent(id);
    }
}