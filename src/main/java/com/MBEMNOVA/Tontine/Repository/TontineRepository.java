package com.MBEMNOVA.Tontine.Repository;

import com.MBEMNOVA.Tontine.Entity.Membre;
import com.MBEMNOVA.Tontine.Entity.Tontine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TontineRepository extends JpaRepository<Tontine, Long> {
    List<Tontine> findByCreateur(Membre createur); // mes tontines créées
}