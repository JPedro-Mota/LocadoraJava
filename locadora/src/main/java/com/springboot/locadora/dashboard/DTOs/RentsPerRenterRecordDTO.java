package com.springboot.locadora.dashboard.DTOs;

import lombok.Builder;

@Builder
public record RentsPerRenterRecordDTO(
        String name,
        int rentsQuantity,
        int rentsActive)  {
}
