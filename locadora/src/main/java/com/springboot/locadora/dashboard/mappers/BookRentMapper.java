package com.springboot.locadora.dashboard.mappers;

import com.springboot.locadora.books.entities.BooksEntity;
import com.springboot.locadora.dashboard.DTOs.BooksMoreRentedDTO;
import com.springboot.locadora.renters.DTOs.RenterRecordDTO;
import com.springboot.locadora.renters.entities.RenterEntity;
import com.springboot.locadora.rents.repositories.RentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookRentMapper {

    @Autowired
    private RentRepository rentRepository;

    public List<BooksMoreRentedDTO> toBooksMoreRentedList(List<BooksEntity> bookList) {
        return bookList.stream()
                .map(this::toBooksMoreRented)
                .sorted((b1, b2) -> Integer.compare(b2.totalRents(), b1.totalRents()))
                .limit(3)
                .collect(Collectors.toList());
    }

    private BooksMoreRentedDTO toBooksMoreRented(BooksEntity book) {
        int rentCount = rentRepository.findAllByBookId(book.getId()).size();
        return new BooksMoreRentedDTO(book.getName(), rentCount);
    }
}
