package com.springboot.locadora.users.services;

import com.springboot.locadora.users.DTOs.UserRecordDto;
import com.springboot.locadora.users.entities.UserEntity;
import com.springboot.locadora.users.repositories.UserRepository;
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
public class UserServices{
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<UserEntity> saveUser(@RequestBody @Valid UserRecordDto userRecordDto){
        var userEntity = new UserEntity();
        BeanUtils.copyProperties(userRecordDto, userEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(userEntity));
    }

    public ResponseEntity<List<UserEntity>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
    }

    public ResponseEntity<Object> getOneUser(@PathVariable(value="id") int id){
        Optional<UserEntity> userO = userRepository.findById(id);
        if(userO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(userO.get());


    }

    public ResponseEntity<Object> updateUser(@PathVariable(value="id") int id, @RequestBody @Valid UserRecordDto  userRecordDto){
        Optional<UserEntity> userO = userRepository.findById(id);
        if(userO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        var userEntity = userO.get();
        BeanUtils.copyProperties(userRecordDto, userEntity);
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(userEntity));
    }

    public ResponseEntity<Object> deleteUser(@PathVariable(value="id") int id){
        Optional<UserEntity> userO = userRepository.findById(id);
        if(userO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
        userRepository.delete(userO.get());
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successful.");
    }

}


