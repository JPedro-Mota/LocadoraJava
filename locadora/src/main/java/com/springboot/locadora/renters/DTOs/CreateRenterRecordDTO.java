package com.springboot.locadora.renters.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record CreateRenterRecordDTO(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank String telephone,
        @NotBlank String address,
        @CPF String cpf) {
}