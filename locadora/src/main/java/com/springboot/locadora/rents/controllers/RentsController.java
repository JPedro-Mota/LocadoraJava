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

    @PutMapping("/rent/{id}")
    public ResponseEntity<Object> delivered(
            @PathVariable int id, @RequestBody @Valid UpdateRentsRecordDTO data) {
        return rentServices.delivered(id, data);
    }

    @PutMapping("/rent/update/{id}")
    public ResponseEntity<Object> update(
            @PathVariable int id, @RequestBody @Valid UpdateRentsRecordDTO data) {
        return rentServices.update(id,data);
    }
}