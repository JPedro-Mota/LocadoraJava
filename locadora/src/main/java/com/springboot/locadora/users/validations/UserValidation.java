package com.springboot.locadora.users.validations;

import com.springboot.locadora.renters.DTOs.UpdateRenterRecordDTO;
import com.springboot.locadora.users.DTOs.CreateUserRecordDTO;
import com.springboot.locadora.users.DTOs.UpdateUserRecordDTO;
import com.springboot.locadora.users.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UserValidation {

    private final UserRepository userRepository;

    public void validateName(CreateUserRecordDTO data){
        if (userRepository.findByName(data.name()) != null){
            throw new CustomValidationException("Name already in use");
        }
    }

    public void validateEmail(CreateUserRecordDTO data) {
        if (userRepository.findByEmail(data.email()) != null) {
            throw new CustomValidationException("Email already in use");
        }
    }


}
