package com.MBEMNOVA.Tontine.Service;

import com.MBEMNOVA.Tontine.DTO.TontineDetailDTO; // 1. Import du DTO
import com.MBEMNOVA.Tontine.DTO.TontineRequest;
import com.MBEMNOVA.Tontine.Entity.Tontine;

import java.util.List;

public interface TontineService {
    Tontine creerTontine(TontineRequest request, String mailCreateur);
    List<Tontine> getMesTontines(String mail);
    String rejoindreTontine(Long tontineId, String mail);
    TontineDetailDTO getTontineDetail(Long id); // 2. Utilise le DTO séparé
}