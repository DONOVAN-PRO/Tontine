package com.MBEMNOVA.Tontine.Controller; // CORRIGÉ

import com.MBEMNOVA.Tontine.Entity.Membre; // CORRIGÉ
import com.MBEMNOVA.Tontine.Repository.MembreRepository; // CORRIGÉ
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MembreRepository membreRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Membre membre) {
        if (membreRepository.existsByMail(membre.getMail())) {
            return ResponseEntity.badRequest().body("Email deja utilise");
        }
        membre.setPassword(passwordEncoder.encode(membre.getPassword()));
        membreRepository.save(membre);
        return ResponseEntity.ok("Membre enregistre. Connecte-toi avec Basic Auth");
    }
}