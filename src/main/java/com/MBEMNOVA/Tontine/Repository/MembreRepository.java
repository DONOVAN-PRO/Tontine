package com.MBEMNOVA.Tontine.Repository;

import com.MBEMNOVA.Tontine.Entity.Membre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembreRepository extends JpaRepository<Membre, Long> {
    Optional<Membre> findByMail(String mail); // pour le login
    Boolean existsByMail(String mail); // pour vérifier si email déjà pris
}