package com.springboot.locadora.rents.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateRentsRecordDTO(
        @NotNull(message = "Renter Id cannot be null") int renterId,
        @NotNull(message = "Book Id cannot be null") int bookId,
        @NotNull(message = "Enter a date") @JsonFormat(pattern = "yyyy-MM-dd") LocalDate deadLine)  {
}