package com.example.RadioBrowserAPI.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

// Configuração centralizada da URL base da API externa, lida do application.properties.
@Configuration 
public class ApiConfig {

    // O Spring injeta aqui o valor de radio.api.base.url em application.properties.
    @Value("${radio.api.base.url}")
    private String baseUrl;

    // Entrega o endereço configurado ao serviço que realiza a pesquisa.
    public String getSearchUrl() {
        return baseUrl;
    }

}
