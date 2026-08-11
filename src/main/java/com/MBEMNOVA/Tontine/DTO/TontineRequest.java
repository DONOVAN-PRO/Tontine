package com.MBEMNOVA.Tontine.DTO;

public record TontineRequest(
        String nom,
        Double montant,
        String frequence,
        Integer nombreMembres
){}