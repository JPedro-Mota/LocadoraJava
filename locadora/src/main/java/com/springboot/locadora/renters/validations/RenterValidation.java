package com.springboot.locadora.renters.validations;


import com.springboot.locadora.renters.DTOs.CreateRenterRecordDTO;
import com.springboot.locadora.renters.DTOs.UpdateRenterRecordDTO;
import com.springboot.locadora.renters.repositories.RenterRepository;
import com.springboot.locadora.rents.enums.RentStatusEnum;
import com.springboot.locadora.rents.repositories.RentRepository;
import com.springboot.locadora.users.validations.CustomValidationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class RenterValidation {

    private final RenterRepository renterRepository;

    private RentRepository rentRepository;

    public void validateName(CreateRenterRecordDTO data) {
        if (renterRepository.findByName(data.name()) != null) {
            throw new CustomValidationException(("Name already in use"));
        }
    }

    public void validateEmail(CreateRenterRecordDTO data) {
        if (renterRepository.findByEmail(data.email()) != null) {
            throw new CustomValidationException("Email already in use");
        }
    }

    public void validateUpdateName(UpdateRenterRecordDTO data) {
        if (renterRepository.findByName(data.name()) != null) {
            throw new CustomValidationException(("Name already in use"));
        }
    }

    public void validateUpdateEmail(UpdateRenterRecordDTO data) {
        if (renterRepository.findByEmail(data.email()) != null) {
            throw new CustomValidationException(("Email already in use"));
        }
    }

    public void validateDeleteRenter(int id) {
        if (rentRepository.existsByRenterIdAndStatus(id, RentStatusEnum.RENTED)) {
            throw new CustomValidationException("Cannot delete renter. There are books currently rented out.");
        }
    }
}
