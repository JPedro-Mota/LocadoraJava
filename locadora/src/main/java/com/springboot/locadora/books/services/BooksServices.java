package com.springboot.locadora.books.services;

import com.springboot.locadora.books.DTOs.CreateBookRecordDTO;
import com.springboot.locadora.books.DTOs.UpdateBookRecordDTO;
import com.springboot.locadora.books.entities.BooksEntity;
import com.springboot.locadora.books.repositories.BooksRepository;
import com.springboot.locadora.books.validations.BooksValidation;
import com.springboot.locadora.publisher.entities.PublisherEntity;
import com.springboot.locadora.publisher.repositories.PublisherRepository;
import com.springboot.locadora.renters.DTOs.CreateRenterRecordDTO;
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
public class BooksServices {

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private BooksValidation booksValidation;

    public ResponseEntity<Void> create (@Valid CreateBookRecordDTO data){

        booksValidation.validLaunchDate(data);
        booksValidation.validTotalQuantity(data);

        PublisherEntity publisher = publisherRepository.findById(data.publisherId())
                .orElseThrow(() -> new IllegalArgumentException("Publisher not found"));
        booksValidation.validationPublisherBook(data);

        BooksEntity newBook = new BooksEntity(data.name(), data.author(), data.launchDate(), data.totalQuantity(), publisher);
        booksRepository.save(newBook);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<List<BooksEntity>> findAll(){
        List<BooksEntity> books = booksRepository.findAllByIsDeletedFalse();

        if (books.isEmpty()) throw new ModelNotFoundException();
        return ResponseEntity.ok(books);
    }

    public Optional<BooksEntity> findById (int id){
        return booksRepository.findById(id);
    }

    public ResponseEntity<Object> update(int id, @Valid UpdateBookRecordDTO booksRecordDto) {
        Optional<BooksEntity> response = booksRepository.findById(id);

        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
        }

        booksValidation.validTotalQuantityUpdate(booksRecordDto);
        booksValidation.validLaunchDateUpdate(booksRecordDto);

        var booksEntity = response.get();

        BeanUtils.copyProperties(booksRecordDto, booksEntity);

        return ResponseEntity.status(HttpStatus.OK).body(booksRepository.save(booksEntity));
    }

    public ResponseEntity<Object> delete(int id) {
        Optional<BooksEntity> response = booksRepository.findById(id);
        if (response.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");

        booksValidation.validDeleteBook(id);

        BooksEntity book = response.get();

        book.setDeleted(true);

        booksRepository.save(book);

        return ResponseEntity.status(HttpStatus.OK).body("Book deleted successfully.");
    }
}
