package com.springboot.locadora.books.services;

import com.springboot.locadora.books.DTOs.CreateBookRecordDTO;
import com.springboot.locadora.books.DTOs.UpdateBookRecordDTO;
import com.springboot.locadora.books.entities.BooksEntity;
import com.springboot.locadora.books.repositories.BooksRepository;
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

    public ResponseEntity<Void> create (@Valid CreateBookRecordDTO data){
        if (booksRepository.findByName(data.name()) !=null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        PublisherEntity publisher = publisherRepository.findById(data.publisherId())
                .orElseThrow(() -> new IllegalArgumentException("Publisher not found"));

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

    public ResponseEntity<Object> updateBooks(int id, @Valid UpdateBookRecordDTO booksRecordDto) {
        Optional<BooksEntity> booksO = booksRepository.findById(id);
        if (booksO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
        }
        var booksEntity = booksO.get();
        BeanUtils.copyProperties(booksRecordDto, booksEntity);
        return ResponseEntity.status(HttpStatus.OK).body(booksRepository.save(booksEntity));
    }

    public ResponseEntity<Object> deleteBooks(int id) {
        Optional<BooksEntity> booksO = booksRepository.findById(id);
        if (booksO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
        }
        booksRepository.delete(booksO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Book deleted successfully.");
    }
}
