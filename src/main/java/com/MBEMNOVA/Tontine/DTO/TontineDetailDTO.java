package com.MBEMNOVA.Tontine.DTO;

import com.MBEMNOVA.Tontine.Entity.Cotisation;
import com.MBEMNOVA.Tontine.Entity.MembreTontine;
import com.MBEMNOVA.Tontine.Entity.Tontine;

import java.util.List;

public record TontineDetailDTO(
        Tontine tontine,
        List<MembreTontine> membres,
        List<Cotisation> cotisations
) {}