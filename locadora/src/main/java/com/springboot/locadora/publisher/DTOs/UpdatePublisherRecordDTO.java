package com.springboot.locadora.publisher.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public record UpdatePublisherRecordDTO(
        @NotBlank(message = "The name cannot be empty") String name,
        @NotBlank(message = "The email cannot be empty") @Email(message = "Invalid email.")  String email,
        @NotNull (message = "Telephone cannot be null") int telephone,
        @URL(message = "Invalid URL") String site) {
}
