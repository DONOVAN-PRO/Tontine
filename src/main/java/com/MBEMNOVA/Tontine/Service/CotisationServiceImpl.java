package com.MBEMNOVA.Tontine.Service;

import com.MBEMNOVA.Tontine.DTO.TourActuelDTO; // 1. Import du DTO
import com.MBEMNOVA.Tontine.Entity.Cotisation;
import com.MBEMNOVA.Tontine.Entity.Membre;
import com.MBEMNOVA.Tontine.Entity.Tontine;
import com.MBEMNOVA.Tontine.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service // 2. @Service va ici
@RequiredArgsConstructor
public class CotisationServiceImpl implements CotisationService {

    private final TontineRepository tontineRepository;
    private final MembreRepository membreRepository;
    private final CotisationRepository cotisationRepository;

    @Override
    public String payerCotisation(Long tontineId, String mail) {
        Tontine tontine = tontineRepository.findById(tontineId).orElseThrow(() -> new RuntimeException("Tontine non trouvee"));
        Membre membre = membreRepository.findByMail(mail).orElseThrow(() -> new RuntimeException("Membre non trouve"));
        long toursPayes = cotisationRepository.findByTontine(tontine).stream().filter(Cotisation::getStatutPaiement).count();
        int tourActuel = (int) toursPayes + 1;
        Cotisation cotisation = cotisationRepository.findByTontineAndNumeroTour(tontine, tourActuel).stream()
                .filter(c -> c.getMembre() != null && c.getMembre().getId().equals(membre.getId()))
                .findFirst().orElseThrow(() -> new RuntimeException("Ce n'est pas votre tour de payer"));
        if (cotisation.getStatutPaiement()) return "Deja paye pour ce tour";
        cotisation.setStatutPaiement(true);
        cotisation.setDatePaiement(LocalDate.now());
        cotisationRepository.save(cotisation);
        return "Paiement enregistre pour le tour " + tourActuel;
    }

    @Override
    public TourActuelDTO getTourActuel(Long tontineId) {
        Tontine tontine = tontineRepository.findById(tontineId).orElseThrow(() -> new RuntimeException("Tontine non trouvee"));
        long toursPayes = cotisationRepository.findByTontine(tontine).stream().filter(Cotisation::getStatutPaiement).count();
        int tourActuel = (int) toursPayes + 1;
        Cotisation beneficiaire = cotisationRepository.findByTontineAndNumeroTour(tontine, tourActuel).stream()
                .filter(c -> c.getMembre() != null).findFirst().orElse(null);
        return new TourActuelDTO(tourActuel, beneficiaire); // 3. Utilise le DTO séparé
    }

    @Override
    public List<Cotisation> getCotisations(Long tontineId) {
        Tontine tontine = tontineRepository.findById(tontineId).orElseThrow(() -> new RuntimeException("Tontine non trouvee"));
        return cotisationRepository.findByTontine(tontine);
    }
}