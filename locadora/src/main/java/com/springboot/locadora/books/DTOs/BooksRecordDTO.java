package com.springboot.locadora.books.DTOs;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.springboot.locadora.publisher.entities.PublisherEntity;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record BooksRecordDTO(
        int id,
        String name,
        String author,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate launchDate,
        int totalQuantity,
        PublisherEntity publisher) {
}