package com.springboot.locadora.renters.DTOs;

import lombok.Builder;

@Builder
public record RenterRecordDTO(
        int id,
        String name,
        String email,
        String cpf,
        String address,
        String telephone)  {
}
