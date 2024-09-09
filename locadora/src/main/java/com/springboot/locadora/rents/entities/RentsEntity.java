package com.springboot.locadora.rents.entities;

import com.springboot.locadora.books.entities.BooksEntity;
import com.springboot.locadora.renters.entities.RenterEntity;
import com.springboot.locadora.rents.enums.RentStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_rents")
public class RentsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private BooksEntity book;

    @ManyToOne
    @JoinColumn(name = "renter_id")
    private RenterEntity renter;

    private LocalDate devolutionDate;

    private LocalDate rentDate;

    private LocalDate deadLine;

    @Enumerated(EnumType.STRING)
    private RentStatusEnum status;

    public RentsEntity(BooksEntity book, RenterEntity renter, LocalDate deadLine) {
        this.renter = renter;
        this.book = book;
        this.deadLine = deadLine;
        this.rentDate = LocalDate.now();
        this.status = determineStatus(deadLine, devolutionDate, rentDate);
    }

    private RentStatusEnum determineStatus(LocalDate deadLine, LocalDate devolutionDate, LocalDate rentDate) {
        LocalDate now = LocalDate.now();

        if (devolutionDate != null) {
            return devolutionDate.isAfter(deadLine) ? RentStatusEnum.DELIVERED_WITH_DELAY : RentStatusEnum.IN_TIME;
        } else {
            return now.isAfter(deadLine) ? RentStatusEnum.LATE : RentStatusEnum.RENTED;
        }
    }

    public String getBookName() {
        return book != null ? book.getName() : null;
    }

    public String getRenterName() {
        return renter != null ? renter.getName() : null;
    }
}
