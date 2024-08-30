package com.springboot.locadora.rents.DTOs;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.springboot.locadora.books.entities.BooksEntity;
import com.springboot.locadora.publisher.entities.PublisherEntity;
import com.springboot.locadora.renters.entities.RenterEntity;
import com.springboot.locadora.rents.enums.RentStatusEnum;
import lombok.Builder;

import java.time.LocalDate;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RentsRecordDTO(
        int id,
        RenterEntity renter,
        BooksEntity book,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate deadLine,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate devolutionDate,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate rentDate,
        RentStatusEnum status
) {
}