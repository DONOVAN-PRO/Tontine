package com.MBEMNOVA.Tontine.Controller;

import com.MBEMNOVA.Tontine.DTO.TontineDetailDTO;
import com.MBEMNOVA.Tontine.DTO.TontineRequest;
import com.MBEMNOVA.Tontine.Entity.Tontine;
import com.MBEMNOVA.Tontine.Service.TontineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tontines")
@RequiredArgsConstructor
@Tag(name = "Tontines", description = "Création, consultation et adhésion aux tontines")
public class TontineController {

    private final TontineService tontineService;

    @Operation(
            summary = "Créer une tontine",
            description = "Crée une nouvelle tontine dont le membre connecté devient automatiquement le créateur/administrateur."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tontine créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides (champ manquant ou incorrect)")
    })
    @PostMapping
    public ResponseEntity<Tontine> createTontine(
            @Valid @RequestBody TontineRequest request,
            Authentication authentication) {
        Tontine tontine = tontineService.creerTontine(request, authentication.getName());
        return ResponseEntity.ok(tontine);
    }

    @Operation(
            summary = "Lister mes tontines",
            description = "Renvoie toutes les tontines auxquelles le membre connecté appartient, en tant que créateur ou participant."
    )
    @ApiResponse(responseCode = "200", description = "Liste des tontines renvoyée avec succès")
    @GetMapping
    public ResponseEntity<List<Tontine>> getMesTontines(Authentication authentication) {
        List<Tontine> mesTontines = tontineService.getMesTontines(authentication.getName());
        return ResponseEntity.ok(mesTontines);
    }

    @Operation(
            summary = "Rejoindre une tontine",
            description = "Ajoute le membre connecté comme participant à la tontine spécifiée."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Adhésion réussie"),
            @ApiResponse(responseCode = "400", description = "Membre déjà inscrit à cette tontine, ou tontine complète"),
            @ApiResponse(responseCode = "404", description = "Tontine introuvable")
    })
    @PostMapping("/{id}/rejoindre")
    public ResponseEntity<String> rejoindreTontine(
            @Parameter(description = "Identifiant de la tontine") @PathVariable Long id,
            Authentication authentication) {
        String message = tontineService.rejoindreTontine(id, authentication.getName());
        return ResponseEntity.ok(message);
    }

    @Operation(
            summary = "Voir le détail d'une tontine",
            description = "Renvoie les informations complètes d'une tontine : membres, montant, fréquence, tour actuel, etc."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Détail de la tontine renvoyé avec succès"),
            @ApiResponse(responseCode = "404", description = "Tontine introuvable")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TontineDetailDTO> getTontineDetail(
            @Parameter(description = "Identifiant de la tontine") @PathVariable Long id) {
        return ResponseEntity.ok(tontineService.getTontineDetail(id));
    }
}