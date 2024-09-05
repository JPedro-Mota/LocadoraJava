package com.springboot.locadora.publisher.DTOs;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.URL;

@Builder
public record PublisherRecordDTO(
        int id,
        @NotBlank String name,
        @Email @NotBlank String email,
        @NotNull int telephone,
        @URL  String site) {
}