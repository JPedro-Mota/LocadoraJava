package com.springboot.locadora.rents.DTOs;

import com.springboot.locadora.rents.entities.RentsEntity;

import java.time.LocalDate;

public record RentsWithNamesDTO(
        int id,
        String bookName,
        String renterName,
        LocalDate deadLine
) {
    public static RentsWithNamesDTO fromEntity(RentsEntity rent) {
        return new RentsWithNamesDTO(
                rent.getId(),
                rent.getBook().getName(),
                rent.getRenter().getName(),
                rent.getDeadLine()
        );
    }
}
