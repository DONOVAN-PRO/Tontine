package com.MBEMNOVA.Tontine.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tontine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nom de la tontine obligatoire")
    private String nom;

    @NotNull(message = "Montant obligatoire")
    private Double montant;

    @NotBlank(message = "Fréquence obligatoire")
    private String frequence;

    @NotNull(message = "Date de debut obligatoire")
    private LocalDate dateDebut;

    @NotNull(message = "Nombre de membres obligatoire")
    private Integer nombreMembres;

    @ManyToOne
    @JoinColumn(name = "createur_id")
    private Membre createur; // maintenant ça ne sera plus souligné
}