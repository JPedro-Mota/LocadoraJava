package com.springboot.locadora.publisher.DTOs;

import jakarta.validation.constraints.NotBlank;

public record CreatePublisherRecordDTO(
        @NotBlank String name,
        @NotBlank String email,
        @NotBlank String telephone,
        @NotBlank String site) {


}
