package com.MBEMNOVA.Tontine.Repository;

import com.MBEMNOVA.Tontine.Entity.Membre;
import com.MBEMNOVA.Tontine.Entity.MembreTontine;
import com.MBEMNOVA.Tontine.Entity.Tontine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MembreTontineRepository extends JpaRepository<MembreTontine, Long> {
    List<MembreTontine> findByTontine(Tontine tontine); // tous les membres d'une tontine
    List<MembreTontine> findByMembre(Membre membre); // toutes les tontines d'un membre
    Optional<MembreTontine> findByTontineAndMembre(Tontine tontine, Membre membre); // vérifier si déjà membre
    Optional<MembreTontine> findByTontineAndTourAttribution(Tontine tontine, Integer tour); // qui reçoit à ce tour
}