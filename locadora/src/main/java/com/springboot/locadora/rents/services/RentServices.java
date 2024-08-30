package com.springboot.locadora.rents.services;

import com.springboot.locadora.books.DTOs.UpdateBookRecordDTO;
import com.springboot.locadora.books.entities.BooksEntity;
import com.springboot.locadora.books.repositories.BooksRepository;
import com.springboot.locadora.publisher.entities.PublisherEntity;
import com.springboot.locadora.renters.entities.RenterEntity;
import com.springboot.locadora.renters.repositories.RenterRepository;
import com.springboot.locadora.rents.DTOs.CreateRentsRecordDTO;
import com.springboot.locadora.rents.DTOs.UpdateRentsRecordDTO;
import com.springboot.locadora.rents.entities.RentsEntity;
import com.springboot.locadora.rents.enums.RentStatusEnum;
import com.springboot.locadora.rents.repositories.RentRepository;
import com.springboot.locadora.users.services.ModelNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentServices {

    @Autowired
    private static RentRepository rentRepository;

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private RenterRepository renterRepository;

    public ResponseEntity<Void> create (@Valid CreateRentsRecordDTO data){

        RenterEntity renter = renterRepository.findById(data.renterId())
                .orElseThrow(() -> new IllegalArgumentException("Renter not found"));

        BooksEntity book = booksRepository.findById(data.bookId())
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        RentsEntity newRent = new RentsEntity(book, renter, data.deadLine());
        newRent.setStatus(RentStatusEnum.RENTED);
        rentRepository.save(newRent);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<List<RentsEntity>> findAll(){
        List<RentsEntity> rents = rentRepository.findAll();
        if (rents.isEmpty()) throw new ModelNotFoundException();
        return ResponseEntity.ok(rents);
    }

    public Optional<RenterEntity> findById (int id){
        return renterRepository.findById(id);
    }

    public static ResponseEntity<Object> updateRent(int id, @Valid UpdateRentsRecordDTO rentsRecordDto) {
        Optional<RentsEntity> rentOptional = rentRepository.findById(id);
        if (rentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rent not found.");
        }
        var rentEntity = rentOptional.get();
        BeanUtils.copyProperties(rentsRecordDto, rentEntity);
        return ResponseEntity.status(HttpStatus.OK).body(rentRepository.save(rentEntity));
    }

    public ResponseEntity<Object> deleteRent(int id) {
        Optional<RentsEntity> rentOptional = rentRepository.findById(id);
        if (rentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rent not found.");
        }
        rentRepository.delete(rentOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Rent deleted successfully.");
    }
}
