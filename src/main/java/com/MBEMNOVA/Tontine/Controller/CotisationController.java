package com.MBEMNOVA.Tontine.Controller;

import com.MBEMNOVA.Tontine.DTO.TourActuelDTO; // CORRIGÉ: Import du DTO
import com.MBEMNOVA.Tontine.Entity.Cotisation;
import com.MBEMNOVA.Tontine.Service.CotisationService;
// SUPPRIME: import com.MBEMNOVA.Tontine.Service.CotisationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tontines")
@RequiredArgsConstructor
public class CotisationController {

    private final CotisationService cotisationService;

    // 1. PAYER SA COTISATION POUR LE TOUR ACTUEL
    @PostMapping("/{id}/payer")
    public ResponseEntity<String> payerCotisation(@PathVariable Long id, Authentication authentication) {
        String message = cotisationService.payerCotisation(id, authentication.getName());
        return ResponseEntity.ok(message);
    }

    // 2. VOIR QUI DOIT RECEVOIR CE TOUR
    @GetMapping("/{id}/tour-actuel")
    public ResponseEntity<TourActuelDTO> getTourActuel(@PathVariable Long id) { // CORRIGÉ
        return ResponseEntity.ok(cotisationService.getTourActuel(id));
    }

    // 3. VOIR L'HISTORIQUE DE PAIEMENT
    @GetMapping("/{id}/cotisations")
    public ResponseEntity<List<Cotisation>> getCotisations(@PathVariable Long id) {
        return ResponseEntity.ok(cotisationService.getCotisations(id));
    }
}