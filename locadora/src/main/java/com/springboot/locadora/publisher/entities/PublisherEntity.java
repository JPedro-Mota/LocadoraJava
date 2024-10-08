package com.springboot.locadora.publisher.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_publisher")
public class PublisherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @Email
    private String email;

    private int telephone;

    private String site;

    private boolean isDeleted;

    public PublisherEntity(String name, String email, int telephone, String site){
        this.name = name;
        this.email = email;
        this.telephone = telephone;
        this.site = site;
        this.isDeleted = false;
    }
}