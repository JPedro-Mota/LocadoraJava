package com.springboot.locadora.rents.controllers;

import com.springboot.locadora.books.DTOs.CreateBookRecordDTO;
import com.springboot.locadora.books.DTOs.UpdateBookRecordDTO;
import com.springboot.locadora.books.entities.BooksEntity;
import com.springboot.locadora.books.services.BooksServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BooksController {

    @Autowired
    private BooksServices booksServices;

    @PostMapping("/books")
    public ResponseEntity<Void> create(@RequestBody @Valid CreateBookRecordDTO data) {
        return booksServices.create(data);
    }

    @GetMapping("/books")
    public ResponseEntity<List<BooksEntity>> getAllBooks() {
        return booksServices.findAll();
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BooksEntity> getById(@PathVariable(value = "id") int id){
        return ResponseEntity.status(HttpStatus.OK).body((booksServices.findById(id).get()));
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<Object> update(@PathVariable(value="id") int id, @RequestBody @Valid UpdateBookRecordDTO updateBookRecordDTO){
        return booksServices.updateBooks(id, updateBookRecordDTO);
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value="id") int id){
        return booksServices.deleteBooks(id);
    }
}