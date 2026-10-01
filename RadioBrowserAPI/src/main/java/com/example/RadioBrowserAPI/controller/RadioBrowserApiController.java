package com.example.RadioBrowserAPI.controller;

import java.util.List;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.RadioBrowserAPI.model.RadioStation;
import com.example.RadioBrowserAPI.service.RadioBrowserApiService;
import com.example.RadioBrowserAPI.service.FavoriteService;

// Controlador responsável por lidar com as requisições relacionadas à API Radio Browser.
@Controller 
public class RadioBrowserApiController {

    // Injeção de dependência do serviço que consulta a API externa.
    @Autowired
    private RadioBrowserApiService radioBrowserApiService;

    // Injeção de dependência do serviço que gerencia os favoritos.
    @Autowired 
    private FavoriteService favoriteService;

    // Construtor para injeção de dependência do serviço RadioBrowserApiService.
    public RadioBrowserApiController(RadioBrowserApiService radioBrowserApiService) {
        this.radioBrowserApiService = radioBrowserApiService;
    }

    // Mapeia requisições GET para /home, retornando a página inicial com a lista de estações.
    @GetMapping("/home") 
    public String listRadioStations(
        @RequestParam(name = "state", defaultValue = "Minas Gerais") String state, 
        @RequestParam(name = "city", defaultValue = "Belo Horizonte") String city, 
        Model model) {
        
        // Busca estações de rádio usando o service, filtrando por estado e cidade.
        List<RadioStation> radioStations = radioBrowserApiService.listRadioStations(state, city); 

        // Marca quais estações já são favoritas
        radioStations.forEach(station ->
                station.setFavorite(favoriteService.isFavorite(station.getStationuuid())));

        // Sort estável: favoritas primeiro, preservando a ordenação por votos já aplicada
        // dentro de cada grupo (favoritas e não favoritas), já que List.sort é estável.
        radioStations.sort(Comparator.comparing(RadioStation::isFavorite).reversed());

        
        // Adiciona a lista de estações e a cidade ao modelo para renderização na página HTML.
        model.addAttribute("stations", radioStations);
        model.addAttribute("city", city);

        // Retorna o nome da view (home.html) que será renderizada.
        return "home";
    }

    // Mapeia requisições POST para /favorites/toggle, permitindo alternar o status de favorito de uma estação.
    @PostMapping("/favorites/toggle") 
    public String toggleFavorite(
        @RequestParam String stationuuid,
        @RequestParam(defaultValue = "Belo Horizonte") String city,
        RedirectAttributes redirectAttributes) {

        // Alterna o status de favorito da estação usando o serviço FavoriteService.    
        favoriteService.toggle(stationuuid);

        // Redireciona de volta para a página inicial, mantendo o filtro de cidade.
        redirectAttributes.addAttribute("city", city);
        return "redirect:/home";
    }
}
