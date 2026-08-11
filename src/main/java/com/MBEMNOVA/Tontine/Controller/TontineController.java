package com.MBEMNOVA.Tontine.Controller;

import com.MBEMNOVA.Tontine.DTO.TontineDetailDTO; // CORRIGÉ: Import du DTO
import com.MBEMNOVA.Tontine.DTO.TontineRequest;
import com.MBEMNOVA.Tontine.Entity.Tontine;
import com.MBEMNOVA.Tontine.Service.TontineService;
// SUPPRIME: import com.MBEMNOVA.Tontine.Service.TontineServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tontines")
@RequiredArgsConstructor
public class TontineController {

    private final TontineService tontineService;

    // 1. CREER UNE TONTINE
    @PostMapping
    public ResponseEntity<Tontine> createTontine(@Valid @RequestBody TontineRequest request, Authentication authentication) {
        Tontine tontine = tontineService.creerTontine(request, authentication.getName());
        return ResponseEntity.ok(tontine);
    }

    // 2. LISTER MES TONTINES
    @GetMapping
    public ResponseEntity<List<Tontine>> getMesTontines(Authentication authentication) {
        List<Tontine> mesTontines = tontineService.getMesTontines(authentication.getName());
        return ResponseEntity.ok(mesTontines);
    }

    // 3. REJOINDRE UNE TONTINE
    @PostMapping("/{id}/rejoindre")
    public ResponseEntity<String> rejoindreTontine(@PathVariable Long id, Authentication authentication) {
        String message = tontineService.rejoindreTontine(id, authentication.getName());
        return ResponseEntity.ok(message);
    }

    // 4. DETAIL D'UNE TONTINE
    @GetMapping("/{id}")
    public ResponseEntity<TontineDetailDTO> getTontineDetail(@PathVariable Long id) { // CORRIGÉ
        return ResponseEntity.ok(tontineService.getTontineDetail(id));
    }
}