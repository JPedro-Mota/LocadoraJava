package com.springboot.locadora.rents.services;

import com.springboot.locadora.books.entities.BooksEntity;
import com.springboot.locadora.books.repositories.BooksRepository;
import com.springboot.locadora.renters.entities.RenterEntity;
import com.springboot.locadora.renters.repositories.RenterRepository;
import com.springboot.locadora.rents.DTOs.CreateRentsRecordDTO;
import com.springboot.locadora.rents.DTOs.RentsWithNamesDTO;
import com.springboot.locadora.rents.DTOs.UpdateRentsRecordDTO;
import com.springboot.locadora.rents.entities.RentsEntity;
import com.springboot.locadora.rents.enums.RentStatusEnum;
import com.springboot.locadora.rents.repositories.RentRepository;
import com.springboot.locadora.rents.validations.RentValidation;
import com.springboot.locadora.users.services.ModelNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RentServices {

    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private RenterRepository renterRepository;

    @Autowired
    private RentValidation rentValidation;

    public ResponseEntity<Void> create(@Valid CreateRentsRecordDTO data) {

        rentValidation.validateDeadLine(data);
        rentValidation.validateRenterId(data);
        rentValidation.validateBookId(data);

        RenterEntity renter = renterRepository.findById(data.renterId())
                .orElseThrow(() -> new IllegalArgumentException("Renter not found"));

        BooksEntity book = booksRepository.findById(data.bookId())
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        RentsEntity newRent = new RentsEntity(book, renter, data.deadLine());
        newRent.setStatus(RentStatusEnum.RENTED);
        rentRepository.save(newRent);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public List<RentsEntity> findAll() {
        List<RentsEntity> rents = rentRepository.findAll();
        if (rents.isEmpty()) throw new ModelNotFoundException();
       for (RentsEntity rent : rents){
           rentValidation.setRentStatus(rent);
       }
        return rents;
    }

    public Optional<RenterEntity> findById(int id) {
        return renterRepository.findById(id);
    }

    public String getBookNameById(int id) {
        return booksRepository.findBookNameById(id);
    }


    public String getRenterNameById(int id) {
        return renterRepository.findRenterNameById(id);
    }


    public ResponseEntity<Object> delivered(int id, @Valid UpdateRentsRecordDTO rentsRecordDto) {
        Optional<RentsEntity> rentOptional = rentRepository.findById(id);
        if (rentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rent not found.");
        }
        RentsEntity rent = rentOptional.get();

        rent.setDevolutionDate(LocalDate.now());

        rentValidation.setRentStatus(rent);

        rentRepository.save(rent);
        return ResponseEntity.status(HttpStatus.OK).body(rent);
    }

    public ResponseEntity<Object> update(int id, @Valid UpdateRentsRecordDTO updateRentRecordDTO) {
        Optional<RentsEntity> rentOptional = rentRepository.findById(id);
        if (rentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rent not found");
        }

        rentValidation.validateRenterIdUpdate(updateRentRecordDTO);
        RenterEntity renter = renterRepository.findById(updateRentRecordDTO.renterId()).get();

        rentValidation.validateBookIdUpdate(updateRentRecordDTO);
        BooksEntity book = booksRepository.findById(updateRentRecordDTO.bookId()).get();

        rentValidation.validateDeadLineUpdate(updateRentRecordDTO);
        rentValidation.validateBookTotalQuantity(book);

        RentsEntity rentEntity = rentOptional.get();
        rentEntity.setBook(book);
        rentEntity.setRenter(renter);

        rentRepository.save(rentEntity);

        return ResponseEntity.status(HttpStatus.OK).body("Rent updated successfully");
    }
}
