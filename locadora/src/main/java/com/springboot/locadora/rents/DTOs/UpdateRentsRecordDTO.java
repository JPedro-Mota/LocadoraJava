package com.springboot.locadora.rents.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UpdateRentsRecordDTO(
    @NotBlank String name,
    @NotBlank String author,
    @NotNull @JsonFormat(pattern = "yyyy-MM-dd") LocalDate launchDate,
    @NotNull int totalQuantity,
    @NotNull int publisherId){

}
