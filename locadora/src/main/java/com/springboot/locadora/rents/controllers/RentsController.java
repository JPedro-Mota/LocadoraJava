package com.springboot.locadora.rents.controllers;

import com.springboot.locadora.renters.entities.RenterEntity;
import com.springboot.locadora.rents.DTOs.CreateRentsRecordDTO;
import com.springboot.locadora.rents.DTOs.RentsRecordDTO;
import com.springboot.locadora.rents.DTOs.RentsWithNamesDTO;
import com.springboot.locadora.rents.DTOs.UpdateRentsRecordDTO;
import com.springboot.locadora.rents.entities.RentsEntity;
import com.springboot.locadora.rents.mappers.RentMapper;
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

    @Autowired
    private RentMapper rentMapper;

    @PostMapping("/rents")
    public ResponseEntity<Void> create(@RequestBody @Valid CreateRentsRecordDTO data) {
        return rentServices.create(data);
    }

    @GetMapping("/rents")
    public ResponseEntity<List<RentsWithNamesDTO>> getAllRents() {
        return rentServices.findAll();
    }

    @GetMapping("/rents/{id}")
    public ResponseEntity<RenterEntity> getById(@PathVariable(value = "id") int id){
        return ResponseEntity.status(HttpStatus.OK).body((rentServices.findById(id).get()));
    }

    @GetMapping("/books/{id}/name")
    public ResponseEntity<String> getBookName(@PathVariable int id) {
        String bookName = rentServices.getBookNameById(id);
        return ResponseEntity.status(HttpStatus.OK).body(bookName);
    }

    @GetMapping("/renters/{id}/name")
    public ResponseEntity<String> getRenterName(@PathVariable int id) {
        String renterName = rentServices.getRenterNameById(id);
        return ResponseEntity.status(HttpStatus.OK).body(renterName);
    }

    @PutMapping("/rents/{id}")
    public ResponseEntity<Object> delivered(
            @PathVariable int id, @RequestBody @Valid UpdateRentsRecordDTO data) {
        return rentServices.delivered(id, data);
    }

    @PutMapping("/rents/update/{id}")
    public ResponseEntity<Object> update(
            @PathVariable int id, @RequestBody @Valid UpdateRentsRecordDTO data) {
        return rentServices.update(id,data);
    }
}