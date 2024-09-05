package com.springboot.locadora.publisher.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public record CreatePublisherRecordDTO(
        @NotBlank String name,
        @NotBlank String email,
        @NotNull int telephone,
        @URL  String site) {


}
