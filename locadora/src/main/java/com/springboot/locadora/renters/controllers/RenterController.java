package com.springboot.locadora.renters.controllers;

import com.springboot.locadora.renters.DTOs.CreateRenterRecordDTO;
import com.springboot.locadora.renters.DTOs.RenterRecordDTO;
import com.springboot.locadora.renters.DTOs.UpdateRenterRecordDTO;
import com.springboot.locadora.renters.mappers.RenterMapper;
import com.springboot.locadora.renters.services.RenterServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RenterController {

    @Autowired
    RenterMapper renterMapper;

    @Autowired
    RenterServices renterServices;

    @PostMapping("/renter")
    public ResponseEntity<Void> create(@RequestBody @Valid CreateRenterRecordDTO data) {
        return renterServices.create(data);
    }

    @GetMapping("/renter")
    public ResponseEntity<List<RenterRecordDTO>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(renterMapper.toRenterRecordList(renterServices.findAll()));
    }

    @GetMapping("/renter/{id}")
    public ResponseEntity<RenterRecordDTO> getById(@PathVariable(value = "id") int id) {
        return ResponseEntity.status(HttpStatus.OK).body(renterMapper.toRenterResponse(renterServices.findById(id).get()));
    }

    @PutMapping("/renter/{id}")
    public ResponseEntity<Object> update(@PathVariable(value="id") int id, @RequestBody @Valid UpdateRenterRecordDTO updateRenterRequestDTO){
        return renterServices.update(id, updateRenterRequestDTO);
    }

    @DeleteMapping("/renter/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value="id") int id){
        return renterServices.delete(id);
    }
}