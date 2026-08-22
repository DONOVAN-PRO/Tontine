package com.MBEMNOVA.Tontine.Controller;

import com.MBEMNOVA.Tontine.DTO.TourActuelDTO;
import com.MBEMNOVA.Tontine.Entity.Cotisation;
import com.MBEMNOVA.Tontine.Service.CotisationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tontines")
@RequiredArgsConstructor
@Tag(name = "Cotisations", description = "Paiement des cotisations et suivi des tours de tontine")
public class CotisationController {

    private final CotisationService cotisationService;

    @Operation(
            summary = "Payer sa cotisation",
            description = "Enregistre le paiement de la cotisation du membre connecté pour le tour actuel de la tontine."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cotisation payée avec succès"),
            @ApiResponse(responseCode = "400", description = "Cotisation déjà payée pour ce tour, ou membre non éligible"),
            @ApiResponse(responseCode = "404", description = "Tontine introuvable, ou membre non membre de cette tontine")
    })
    @PostMapping("/{id}/payer")
    public ResponseEntity<String> payerCotisation(
            @Parameter(description = "Identifiant de la tontine") @PathVariable Long id,
            Authentication authentication) {
        String message = cotisationService.payerCotisation(id, authentication.getName());
        return ResponseEntity.ok(message);
    }

    @Operation(
            summary = "Voir le tour actuel de la tontine",
            description = "Renvoie le membre bénéficiaire du tour en cours, ainsi que l'état des cotisations pour ce tour."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tour actuel renvoyé avec succès"),
            @ApiResponse(responseCode = "404", description = "Tontine introuvable")
    })
    @GetMapping("/{id}/tour-actuel")
    public ResponseEntity<TourActuelDTO> getTourActuel(
            @Parameter(description = "Identifiant de la tontine") @PathVariable Long id) {
        return ResponseEntity.ok(cotisationService.getTourActuel(id));
    }

    @Operation(
            summary = "Lister l'historique des cotisations",
            description = "Renvoie l'ensemble des cotisations enregistrées pour cette tontine, tous membres et tours confondus."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Historique renvoyé avec succès"),
            @ApiResponse(responseCode = "404", description = "Tontine introuvable")
    })
    @GetMapping("/{id}/cotisations")
    public ResponseEntity<List<Cotisation>> getCotisations(
            @Parameter(description = "Identifiant de la tontine") @PathVariable Long id) {
        return ResponseEntity.ok(cotisationService.getCotisations(id));
    }
}