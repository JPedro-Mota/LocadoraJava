package com.springboot.locadora.rents.validations;

import com.springboot.locadora.books.entities.BooksEntity;
import com.springboot.locadora.books.repositories.BooksRepository;
import com.springboot.locadora.renters.entities.RenterEntity;
import com.springboot.locadora.renters.repositories.RenterRepository;
import com.springboot.locadora.rents.DTOs.CreateRentsRecordDTO;
import com.springboot.locadora.rents.DTOs.UpdateRentsRecordDTO;
import com.springboot.locadora.rents.entities.RentsEntity;
import com.springboot.locadora.rents.enums.RentStatusEnum;
import com.springboot.locadora.rents.repositories.RentRepository;
import com.springboot.locadora.users.validations.CustomValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class RentValidation {

    @Autowired
    RenterRepository renterRepository;

    @Autowired
    BooksRepository booksRepository;

    @Autowired
    RentRepository rentRepository;

    public void validateRenterId(CreateRentsRecordDTO data) {

        if (renterRepository.findById(data.renterId()).isEmpty()) {
            throw new CustomValidationException("Renter not found");
        }

        RenterEntity renter = renterRepository.findById(data.renterId()).get();

        if (renter.isDeleted()) {
            throw new CustomValidationException("Renter not found");
        }
    }

    public void validateRenterIdUpdate(UpdateRentsRecordDTO data){
        if (renterRepository.findById(data.renterId()).isEmpty()){
            throw new CustomValidationException("Renter not found");
        }
    }

    public void validateBookId(CreateRentsRecordDTO data){
        if (booksRepository.findById(data.bookId()).isEmpty()){
            throw new CustomValidationException("Book not found");
        }

        BooksEntity book = booksRepository.findById(data.bookId()).get();

        if (book.isDeleted()){
            throw new CustomValidationException("Book not found");
        }
    }

    public void validateBookIdUpdate(UpdateRentsRecordDTO data){
        if (booksRepository.findById(data.bookId()).isEmpty()){
            throw new CustomValidationException("Book not found");
        }
    }

    public void validateDeadLine(CreateRentsRecordDTO data){
        if (data.deadLine().isAfter(LocalDate.now().plusDays(30))){
            throw new CustomValidationException("Deadline cannot be more 30 days.");
        } else if (data.deadLine().isBefore(LocalDate.now())) {
            throw new CustomValidationException("The deadline cannot be in the past.");
        }
    }

    public void validateDeadLineUpdate(UpdateRentsRecordDTO data) {
        if (data.deadLine().isAfter(LocalDate.now().plusDays(30))) {
            throw new CustomValidationException("Deadline cannot be more than 30 days from today.");
        } else if (data.deadLine().isBefore(LocalDate.now())) {
            throw new CustomValidationException("The deadline cannot be in the past.");
        }
    }


    public void validateBookTotalQuantity(BooksEntity data){
        if (data.getTotalQuantity() <= 0){
            throw new CustomValidationException("There are no books available");
        }
    }

    public void validateRentRepeated(CreateRentsRecordDTO data){
        if (rentRepository.existsByRenterIdAndBookIdAndStatus(data.renterId(), data.bookId(), RentStatusEnum.RENTED)){
            throw new CustomValidationException("Renter already has this book rented.");
        }
    }

    public void validateRentLate(CreateRentsRecordDTO data){
        if (rentRepository.existsByRenterIdAndStatus(data.renterId(), RentStatusEnum.LATE)){
            throw new CustomValidationException("Renter has late rent.");
        }
    }

    public void setRentStatus(RentsEntity rent){
        if (rent.getDevolutionDate() == null){

            if (rent.getDeadLine().isBefore(LocalDate.now())) {
                rent.setStatus(RentStatusEnum.LATE);
                rentRepository.save(rent);
            } else if (rent.getDevolutionDate() == null) {
                rent.setStatus(RentStatusEnum.RENTED);
                rentRepository.save(rent);
            }

        } else {

            if (rent.getDevolutionDate().isAfter(rent.getDeadLine())) {
                rent.setStatus(RentStatusEnum.DELIVERED_WITH_DELAY);
                rentRepository.save(rent);
            } else {
                rent.setStatus(RentStatusEnum.IN_TIME);
                rentRepository.save(rent);
            }
        }
    }
}
