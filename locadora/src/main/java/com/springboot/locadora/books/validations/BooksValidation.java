package com.springboot.locadora.books.validations;

import com.springboot.locadora.books.DTOs.CreateBookRecordDTO;
import com.springboot.locadora.books.DTOs.UpdateBookRecordDTO;
import com.springboot.locadora.books.repositories.BooksRepository;
import com.springboot.locadora.publisher.entities.PublisherEntity;
import com.springboot.locadora.publisher.repositories.PublisherRepository;
import com.springboot.locadora.rents.enums.RentStatusEnum;
import com.springboot.locadora.rents.repositories.RentRepository;
import com.springboot.locadora.users.validations.CustomValidationException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@AllArgsConstructor
@Component
public class BooksValidation {

    @Autowired
    BooksRepository booksRepository;

    @Autowired
    RentRepository rentRepository;

    @Autowired
    PublisherRepository publisherRepository;

    public void validationPublisherBook(CreateBookRecordDTO data){
        PublisherEntity publisher = publisherRepository.findById(data.publisherId()).get();

        if(publisher.isDeleted()){
            throw new CustomValidationException("Publisher not exists");
        }
    }

    public void validLaunchDate(@Valid CreateBookRecordDTO data){
        if (data.launchDate().isAfter(LocalDate.now())){
            throw new CustomValidationException("Launch Date cannot be in future");
        }
    }

    public void validLaunchDateUpdate(UpdateBookRecordDTO data){
        if (data.launchDate().isAfter(LocalDate.now())){
            throw new CustomValidationException("Launch Date cannot be in future");
        }
    }

    public void validTotalQuantity(@Valid CreateBookRecordDTO data){
        if (data.totalQuantity() <= 0){
            throw new CustomValidationException("The total quantity cannot be less than 1");
        }
    }

    public void validTotalQuantityUpdate(@Valid UpdateBookRecordDTO data){
        if (data.totalQuantity() <= 0){
            throw new CustomValidationException("The total quantity cannot be less than 1");
        }
    }

    public void validDeleteBook(int id){
        boolean hasActiveRent = rentRepository.existsByBookIdAndStatus(id, RentStatusEnum.RENTED);
        if (hasActiveRent) {
            throw new CustomValidationException("The book cannot be deleted because it has an active rental.");
        }
    }
}
