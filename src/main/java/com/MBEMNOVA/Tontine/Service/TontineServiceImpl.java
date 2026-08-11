package com.MBEMNOVA.Tontine.Service;

import com.MBEMNOVA.Tontine.DTO.TontineDetailDTO; // 1. Import du DTO
import com.MBEMNOVA.Tontine.DTO.TontineRequest;
import com.MBEMNOVA.Tontine.Entity.*;
import com.MBEMNOVA.Tontine.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service // 2. @Service va ici
@RequiredArgsConstructor
public class TontineServiceImpl implements TontineService {

    private final TontineRepository tontineRepository;
    private final MembreRepository membreRepository;
    private final MembreTontineRepository membreTontineRepository;
    private final CotisationRepository cotisationRepository;

    @Override
    @Transactional // Pour que tout soit sauvé d'un coup
    public Tontine creerTontine(TontineRequest request, String mailCreateur) {
        Membre createur = membreRepository.findByMail(mailCreateur).orElseThrow(() -> new RuntimeException("Membre non trouve"));
        Tontine tontine = new Tontine();
        tontine.setNom(request.nom());
        tontine.setMontant(request.montant());
        tontine.setFrequence(request.frequence());
        tontine.setNombreMembres(request.nombreMembres());
        tontine.setDateDebut(LocalDate.now());
        tontine.setCreateur(createur);
        Tontine savedTontine = tontineRepository.save(tontine);

        MembreTontine mt = new MembreTontine();
        mt.setMembre(createur);
        mt.setTontine(savedTontine);
        mt.setTourAttribution(1);
        membreTontineRepository.save(mt);

        for (int i = 1; i <= tontine.getNombreMembres(); i++) {
            Cotisation c = new Cotisation();
            c.setTontine(savedTontine);
            c.setNumeroTour(i);
            c.setMontant(tontine.getMontant());
            c.setStatutPaiement(false);
            cotisationRepository.save(c);
        }
        return savedTontine;
    }

    @Override
    public List<Tontine> getMesTontines(String mail) {
        Membre membre = membreRepository.findByMail(mail).orElseThrow(() -> new RuntimeException("Membre non trouve"));
        return membreTontineRepository.findByMembre(membre).stream().map(MembreTontine::getTontine).toList();
    }

    @Override
    @Transactional
    public String rejoindreTontine(Long tontineId, String mail) {
        Tontine tontine = tontineRepository.findById(tontineId).orElseThrow(() -> new RuntimeException("Tontine non trouvee"));
        Membre membre = membreRepository.findByMail(mail).orElseThrow(() -> new RuntimeException("Membre non trouve"));
        if (membreTontineRepository.findByTontineAndMembre(tontine, membre).isPresent()) return "Deja membre";
        int nbMembresActuels = membreTontineRepository.findByTontine(tontine).size();
        if (nbMembresActuels >= tontine.getNombreMembres()) return "Tontine complete";
        MembreTontine mt = new MembreTontine();
        mt.setMembre(membre);
        mt.setTontine(tontine);
        mt.setTourAttribution(nbMembresActuels + 1);
        membreTontineRepository.save(mt);
        cotisationRepository.findByTontineAndNumeroTour(tontine, mt.getTourAttribution())
                .forEach(c -> { c.setMembre(membre); cotisationRepository.save(c); });
        return "Rejoint. Votre tour: " + mt.getTourAttribution();
    }

    @Override
    public TontineDetailDTO getTontineDetail(Long id) {
        Tontine tontine = tontineRepository.findById(id).orElseThrow(() -> new RuntimeException("Tontine non trouvee"));
        List<MembreTontine> membres = membreTontineRepository.findByTontine(tontine);
        List<Cotisation> cotisations = cotisationRepository.findByTontine(tontine);
        return new TontineDetailDTO(tontine, membres, cotisations); // 3. Utilise le DTO séparé
    }
}