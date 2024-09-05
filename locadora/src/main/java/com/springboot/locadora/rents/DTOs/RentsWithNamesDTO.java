package com.springboot.locadora.rents.DTOs;

import com.springboot.locadora.rents.entities.RentsEntity;

import java.time.LocalDate;

public record RentsWithNamesDTO(
        int id,
        String bookName,
        String renterName,
        LocalDate deadLine,
        String status
) {
    public static RentsWithNamesDTO fromEntity(RentsEntity rent) {
        return new RentsWithNamesDTO(
                rent.getId(),
                rent.getBook().getName(), // Assumindo que BooksEntity tem um método getName()
                rent.getRenter().getName(), // Assumindo que RenterEntity tem um método getName()
                rent.getDeadLine(),
                rent.getStatus().name()
        );
    }
}
