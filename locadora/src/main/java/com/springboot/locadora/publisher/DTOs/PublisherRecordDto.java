package com.springboot.locadora.publisher.DTOs;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PublisherRecordDto(
        @NotBlank String name,
        @Email @NotBlank String email,
        @NotNull String telephone,
        @NotBlank String site) {
}