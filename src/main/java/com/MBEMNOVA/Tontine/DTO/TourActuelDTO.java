package com.MBEMNOVA.Tontine.DTO;

import com.MBEMNOVA.Tontine.Entity.Cotisation;

public record TourActuelDTO(
        int numeroTour,
        Cotisation beneficiaire
) {}