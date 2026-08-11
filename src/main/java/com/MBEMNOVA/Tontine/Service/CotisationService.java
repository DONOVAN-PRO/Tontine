package com.MBEMNOVA.Tontine.Service;

import com.MBEMNOVA.Tontine.DTO.TourActuelDTO; // 1. Import du DTO
import com.MBEMNOVA.Tontine.Entity.Cotisation;
import java.util.List;

public interface CotisationService {
    String payerCotisation(Long tontineId, String mail);
    TourActuelDTO getTourActuel(Long tontineId); // 2. Utilise le DTO séparé
    List<Cotisation> getCotisations(Long tontineId);
}