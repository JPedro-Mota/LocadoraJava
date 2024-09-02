package com.springboot.locadora.publisher.DTOs;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PublisherRecordDTO(
        int id,
        @NotBlank String name,
        @Email @NotBlank String email,
        @NotNull String telephone,
        @NotBlank String site) {
}