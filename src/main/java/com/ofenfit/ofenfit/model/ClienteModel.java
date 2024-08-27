package com.ofenfit.ofenfit.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class ClienteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nome;

    @Column(unique = true, nullable = false)
    private String cpf;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(unique = true, nullable = false)
    private String email;
    
    @NotNull
    private boolean congelado;

    @NotNull
    private int diasDeOfensiva;

   

}