package com.example.RadioBrowserAPI.service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

// Serviço que guarda favoritos em memória enquanto a aplicação está em execução.
// O conjunto é compartilhado entre requisições, mas não é persistido após reiniciar.
@Service
public class FavoriteService {
    
    // Conjunto de UUIDs das estações marcadas como favoritas, thread-safe.
    private final Set<String> favoriteStationUuids = ConcurrentHashMap.newKeySet();

    // Verifica se a estação com o UUID fornecido está marcada como favorita.
    public boolean isFavorite(String stationUuid) {
        return stationUuid != null && favoriteStationUuids.contains(stationUuid);
    }

    // Ignora identificadores nulos, que não podem representar uma estação válida.
    public void toggle(String stationUuid) {
        if (stationUuid == null) return;

        // Adiciona a estação aos favoritos se não estiver presente; caso contrário, remove-a.
        if (!favoriteStationUuids.add(stationUuid)) {
            favoriteStationUuids.remove(stationUuid);
        }
    }
}
