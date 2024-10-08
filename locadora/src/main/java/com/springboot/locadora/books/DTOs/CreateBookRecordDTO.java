package com.springboot.locadora.books.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record CreateBookRecordDTO(
        @NotBlank String name,
        @NotBlank String author,
        @NotNull @JsonFormat(pattern = "yyyy-MM-dd") LocalDate launchDate,
        @NotNull int totalQuantity,
        @NotNull int publisherId) {
}