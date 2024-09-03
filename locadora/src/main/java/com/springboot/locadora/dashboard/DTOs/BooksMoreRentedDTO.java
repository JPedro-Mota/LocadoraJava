package com.springboot.locadora.dashboard.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record BooksMoreRentedDTO(
       String name,
       int totalRents) {
}