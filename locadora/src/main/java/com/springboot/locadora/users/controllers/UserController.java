package com.springboot.locadora.users.controllers;

import com.springboot.locadora.users.DTOs.UserRecordDto;
import com.springboot.locadora.users.entities.UserEntity;
import com.springboot.locadora.users.repositories.UserRepository;
import com.springboot.locadora.users.services.UserServices;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserServices userServices;

    @PostMapping("/users")
    public ResponseEntity<UserEntity> saveUser(@RequestBody @Valid UserRecordDto userRecordDto){
        var userEntity = new UserEntity();
        BeanUtils.copyProperties(userRecordDto, userEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(userEntity));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value="id") int id){
        Optional<UserEntity> userO = userRepository.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userO.get());

    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value="id") int id, @RequestBody @Valid UserRecordDto  userRecordDto){
        Optional<UserEntity> userO = userRepository.findById(id);
        var userEntity = userO.get();
        BeanUtils.copyProperties(userRecordDto, userEntity);
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(userEntity));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value="id") int id){
    return ResponseEntity.status(HttpStatus.OK).body("User deleted sucessfull.");
    }

}


