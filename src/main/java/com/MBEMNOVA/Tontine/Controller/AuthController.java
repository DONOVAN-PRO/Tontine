package com.MBEMNOVA.Tontine.Controller;

import com.MBEMNOVA.Tontine.Entity.Membre;
import com.MBEMNOVA.Tontine.Repository.MembreRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentification", description = "Inscription des membres de la tontine")
public class AuthController {

    private final MembreRepository membreRepository;
    private final PasswordEncoder passwordEncoder;

    @Operation(
            summary = "Inscrire un nouveau membre",
            description = "Crée un compte membre avec un email unique et un mot de passe chiffré. "
                    + "Une fois inscrit, le membre se connecte via Basic Auth avec son email et son mot de passe."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Membre enregistré avec succès"),
            @ApiResponse(responseCode = "400", description = "Email déjà utilisé par un autre membre")
    })
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