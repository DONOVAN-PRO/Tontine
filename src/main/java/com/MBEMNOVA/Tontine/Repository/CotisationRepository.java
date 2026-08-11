package com.MBEMNOVA.Tontine.Repository;


import com.MBEMNOVA.Tontine.Entity.Cotisation;
import com.MBEMNOVA.Tontine.Entity.Membre;
import com.MBEMNOVA.Tontine.Entity.Tontine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CotisationRepository extends JpaRepository<Cotisation, Long> {
    List<Cotisation> findByTontine(Tontine tontine); // historique d'une tontine
    List<Cotisation> findByMembreAndTontine(Membre membre, Tontine tontine); // paiements d'un membre dans une tontine
    List<Cotisation> findByTontineAndNumeroTour(Tontine tontine, Integer numeroTour); // tous les paiements du tour N
}