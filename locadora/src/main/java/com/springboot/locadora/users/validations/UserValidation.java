package com.springboot.locadora.users.validations;

import com.springboot.locadora.renters.DTOs.CreateRenterRecordDTO;
import com.springboot.locadora.renters.DTOs.UpdateRenterRecordDTO;
import com.springboot.locadora.users.DTOs.UpdateUserRecordDTO;
import com.springboot.locadora.users.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UserValidation {

    private final UserRepository userRepository;

    public void validateEmail(CreateRenterRecordDTO data) {
        if (userRepository.findByEmail(data.email()) != null) {
            throw new CustomValidationException("Email already in use");
        }
    }

    public void validateUpdateEmail(UpdateUserRecordDTO data) {
        if (userRepository.findByEmail(data.email()) != null) {
            throw new CustomValidationException("Email already in use.");
        }
    }

    public void validateUpdateRenterEmail(UpdateRenterRecordDTO data) {
        if (userRepository.findByEmail(data.email()) != null) {
            throw new CustomValidationException("Email already in use.");
        }
    }
}
