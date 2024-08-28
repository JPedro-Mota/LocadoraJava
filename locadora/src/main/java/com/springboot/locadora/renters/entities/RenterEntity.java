package com.springboot.locadora.renters.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_renter")
public class RenterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String email;

    @CPF
    private String cpf;

    @NotBlank
    private String telephone;

    private String address;

    public RenterEntity(String name, String email, @CPF String cpf, @NotBlank String telephone, String address){
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.telephone = telephone;
        this.address = address;
    }
}
