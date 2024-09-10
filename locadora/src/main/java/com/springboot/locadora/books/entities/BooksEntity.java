package com.springboot.locadora.books.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.springboot.locadora.publisher.entities.PublisherEntity;
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
@Table(name = "tb_books")
public class BooksEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String author;

    private LocalDate launchDate;

    private int totalQuantity;

    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private PublisherEntity publisher;

    public BooksEntity(String name, String author, LocalDate launchDate, int totalQuantity, PublisherEntity publisher){
        this.name = name;
        this.author = author;
        this.launchDate = launchDate;
        this.totalQuantity = totalQuantity;
        this.publisher = publisher;
        this.isDeleted = false;
    }

    public String getPublisherName() {
        return publisher != null ? publisher.getName() : null;
    }

}