package com.springboot.locadora.publisher.validations;

import com.springboot.locadora.books.repositories.BooksRepository;
import com.springboot.locadora.publisher.DTOs.CreatePublisherRecordDTO;
import com.springboot.locadora.publisher.DTOs.UpdatePublisherRecordDTO;
import com.springboot.locadora.publisher.repositories.PublisherRepository;
import com.springboot.locadora.rents.enums.RentStatusEnum;
import com.springboot.locadora.rents.repositories.RentRepository;
import com.springboot.locadora.users.validations.CustomValidationException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@AllArgsConstructor
@Component
public class PublisherValidation {

    @Autowired
    private final PublisherRepository publisherRepository;

    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private BooksRepository booksRepository;

    public void validateEmail(CreatePublisherRecordDTO data){
        if (publisherRepository.findByName(data.name()) !=null ){
            throw new CustomValidationException("Name alredy in use.");
        }
    }

    public void validEmail(CreatePublisherRecordDTO data){
        if (publisherRepository.findByName(data.name()) != null ){
            throw new CustomValidationException("Name alredy in use.");
        }
    }

    public void validTelephone(CreatePublisherRecordDTO data) {
        if (publisherRepository.findByTelephone(data.telephone()) != null) {
            throw new CustomValidationException("This telephone is already in use.");
        }
    }

    public void validTelephoneUpdate(UpdatePublisherRecordDTO data){
        if(publisherRepository.findByTelephone(data.telephone()) !=null ){
            throw new CustomValidationException("This telephone is alredy in use.");
        }
    }

    public void validSite(CreatePublisherRecordDTO data){
        if (!Objects.equals(data.site(), "")){
            if (publisherRepository.findBySite(data.site()) != null){
                throw new CustomValidationException("This site is already in use");
            }
        }
    }

    public void validSiteUpdate(UpdatePublisherRecordDTO data){
        if (publisherRepository.findBySite(data.site()) != null){
            throw new CustomValidationException("This site is already in use");
        }
    }

    public void validDeletePublisher(int id) {
        var books = booksRepository.findByPublisherId(id);
        for (var book : books) {
            if (rentRepository.existsByBookIdAndStatus(book.getId(), RentStatusEnum.RENTED)) {
                throw new CustomValidationException("Cannot delete publisher. There are books currently rented out.");
            }
        }
    }

}

