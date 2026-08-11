package com.MBEMNOVA.Tontine.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cotisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Numero de tour obligatoire")
    private Integer numeroTour; // Tour 1, 2, 3...

    @NotNull(message = "Montant obligatoire")
    private Double montant;

    private Boolean statutPaiement = false; // false = non payé, true = payé

    private LocalDate datePaiement;

    @ManyToOne
    @JoinColumn(name = "membre_id")
    private Membre membre;

    @ManyToOne
    @JoinColumn(name = "tontine_id")
    private Tontine tontine;
}