package com.example.RadioBrowserAPI.service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import com.example.RadioBrowserAPI.config.ApiConfig;
import com.example.RadioBrowserAPI.model.RadioStation;

// Consulta a API externa, trata a resposta e a converte para o modelo RadioStation.
@Service
public class RadioBrowserApiService {

    // O endereço da API vem da configuração centralizada em ApiConfig.
   @Autowired
    private ApiConfig apiConfig;

    // Relaciona as capitais brasileiras aos estados usados como filtro na API.
    private static final Map<String, String> CAPITAL_STATE_MAP = new HashMap<>();

    // Preenche o mapa uma vez quando a classe é carregada.
    static {
        CAPITAL_STATE_MAP.put("Aracaju", "Sergipe");
        CAPITAL_STATE_MAP.put("Boa Vista", "Roraima");
        CAPITAL_STATE_MAP.put("Belo Horizonte", "Minas Gerais");
        CAPITAL_STATE_MAP.put("Belém", "Pará");
        CAPITAL_STATE_MAP.put("Goiânia", "Goiás");
        CAPITAL_STATE_MAP.put("João Pessoa", "Paraíba");
        CAPITAL_STATE_MAP.put("Macapá", "Amapá");
        CAPITAL_STATE_MAP.put("Maceió", "Alagoas");
        CAPITAL_STATE_MAP.put("Manaus", "Amazonas");
        CAPITAL_STATE_MAP.put("Natal", "Rio Grande do Norte");
        CAPITAL_STATE_MAP.put("Porto Velho", "Rondônia");
        CAPITAL_STATE_MAP.put("Porto Alegre", "Rio Grande do Sul");
        CAPITAL_STATE_MAP.put("Recife", "Pernambuco");
        CAPITAL_STATE_MAP.put("Rio Branco", "Acre");
        CAPITAL_STATE_MAP.put("Rio de Janeiro", "Rio de Janeiro");
        CAPITAL_STATE_MAP.put("Salvador", "Bahia");
        CAPITAL_STATE_MAP.put("São Luís", "Maranhão");
        CAPITAL_STATE_MAP.put("São Paulo", "São Paulo");
        CAPITAL_STATE_MAP.put("Curitiba", "Paraná");
        CAPITAL_STATE_MAP.put("Cuiabá", "Mato Grosso");
        CAPITAL_STATE_MAP.put("Campo Grande", "Mato Grosso do Sul");
        CAPITAL_STATE_MAP.put("Fortaleza", "Ceará");
        CAPITAL_STATE_MAP.put("Teresina", "Piauí");
        CAPITAL_STATE_MAP.put("Palmas", "Tocantins");
        CAPITAL_STATE_MAP.put("Vitória", "Espírito Santo");
        CAPITAL_STATE_MAP.put("Florianópolis", "Santa Catarina");
        CAPITAL_STATE_MAP.put("Brasília", "Distrito Federal");
    }

    // Busca estações para os filtros informados e devolve a lista ordenada por votos.
    public List<RadioStation> listRadioStations(String state, String city) {

        // Se a cidade for uma capital, substitui o estado pelo correspondente no mapa.
        if (city != null && CAPITAL_STATE_MAP.containsKey(city)) {
            state = CAPITAL_STATE_MAP.get(city);
        }

        // Substitui filtros ausentes pelos valores padrão da página inicial.
        if (state == null || state.isEmpty()) {
            state = "Minas Gerais";
        }
        if (city == null || city.isEmpty()) {
            city = "Belo Horizonte";
        }

        // Monta a URL com os filtros de país, estado e cidade exigidos pela pesquisa.
        String url = apiConfig.getSearchUrl() + "?country=Brazil&state=" + state + "&city=" + city;

        // Cria um cliente HTTP para enviar a requisição à API externa.
        RestTemplate restTemplate = new RestTemplate();

        try {
            // Recebe o JSON como uma lista de maps, em que cada map representa uma estação.
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.getForEntity(url, (Class<List<Map<String, Object>>>) (Object) List.class);

            // Converte os mapas em objetos do projeto e ordena do maior para o menor número de votos.
            List<RadioStation> radioStations = extractRadioStations(response.getBody());
            radioStations.sort(Comparator.comparingInt(RadioStation::getVotes).reversed());

            return radioStations;

        } catch (HttpServerErrorException e) {
            // Trata respostas de erro do servidor remoto, destacando o caso HTTP 502.
            if (e.getStatusCode().value() == 502) {
                System.err.println("Erro: Serviço indisponível. Por favor, tente novamente mais tarde.");
            } else {
                System.err.println("Erro: Não foi possível obter as estações de rádio devido a um problema no servidor.");
            }
            // Devolve lista vazia para que a página possa ser exibida sem resultados.
            return Collections.emptyList();

        } catch (Exception e) {
            // Trata falhas inesperadas, como problemas de conexão ou conversão da resposta.
            System.err.println("Erro: Ocorreu um problema inesperado ao buscar as estações de rádio.");
            return Collections.emptyList();
        }
    }

    // Converte os dados JSON para RadioStation, que é o formato usado pelo restante do projeto.
    private List<RadioStation> extractRadioStations(List<Map<String, Object>> stationsData) {
        List<RadioStation> radioStations = new ArrayList<>();

        // A resposta pode ser nula; nesse caso, a lista criada permanece vazia.
        if (stationsData != null) {
            for (Map<String, Object> stationMap : stationsData) {
                // Cria um modelo vazio e copia os campos textuais e identificadores do JSON.
                RadioStation station = new RadioStation();

                station.setChangeuuid((String) stationMap.get("changeuuid"));
                station.setStationuuid((String) stationMap.get("stationuuid"));
                station.setServeruuid((String) stationMap.get("serveruuid"));
                station.setName((String) stationMap.get("name"));
                station.setUrl((String) stationMap.get("url"));
                station.setUrlResolved((String) stationMap.get("url_resolved"));
                station.setHomepage((String) stationMap.get("homepage"));
                // Usa uma imagem local se a API não fornecer um favicon válido.
                station.setFavicon(stationMap.get("favicon") == null
                        || ((String) stationMap.get("favicon")).isEmpty()
                        ? "images/aradio.webp"
                        : (String) stationMap.get("favicon"));
                station.setTags((String) stationMap.get("tags"));
                station.setCountry((String) stationMap.get("country"));
                station.setCountrycode((String) stationMap.get("countrycode"));
                station.setIso3166_2((String) stationMap.get("iso_3166_2"));
                station.setState((String) stationMap.get("state"));
                station.setLanguage((String) stationMap.get("language"));
                station.setLanguagecodes((String) stationMap.get("languagecodes"));

                // Copia métricas e detalhes técnicos; números ausentes recebem zero.
                station.setVotes(stationMap.get("votes") != null ? ((Number) stationMap.get("votes")).intValue() : 0);
                station.setLastchangetime((String) stationMap.get("lastchangetime"));
                station.setLastchangetimeIso8601((String) stationMap.get("lastchangetime_iso8601"));
                station.setCodec((String) stationMap.get("codec"));
                station.setBitrate(stationMap.get("bitrate") != null ? ((Number) stationMap.get("bitrate")).intValue() : 0);
                station.setHls(stationMap.get("hls") != null ? ((Number) stationMap.get("hls")).intValue() : 0);
                station.setLastcheckok(stationMap.get("lastcheckok") != null ? ((Number) stationMap.get("lastcheckok")).intValue() : 0);
                station.setLastchecktime((String) stationMap.get("lastchecktime"));
                station.setLastchecktimeIso8601((String) stationMap.get("lastchecktime_iso8601"));
                station.setLastcheckoktime((String) stationMap.get("lastcheckoktime"));
                station.setLastcheckoktimeIso8601((String) stationMap.get("lastcheckoktime_iso8601"));
                station.setLastlocalchecktime((String) stationMap.get("lastlocalchecktime"));
                station.setLastlocalchecktimeIso8601((String) stationMap.get("lastlocalchecktime_iso8601"));
                station.setClicktimestamp((String) stationMap.get("clicktimestamp"));
                station.setClicktimestampIso8601((String) stationMap.get("clicktimestamp_iso8601"));
                station.setClickcount(stationMap.get("clickcount") != null ? ((Number) stationMap.get("clickcount")).intValue() : 0);
                station.setClicktrend(stationMap.get("clicktrend") != null ? ((Number) stationMap.get("clicktrend")).intValue() : 0);
                station.setSslError(stationMap.get("ssl_error") != null ? ((Number) stationMap.get("ssl_error")).intValue() : 0);
                // Copia coordenadas e indicador de informações estendidas, com padrão se ausentes.
                station.setGeoLat(stationMap.get("geo_lat") != null ? ((Number) stationMap.get("geo_lat")).doubleValue() : 0.0);
                station.setGeoLong(stationMap.get("geo_long") != null ? ((Number) stationMap.get("geo_long")).doubleValue() : 0.0);
                station.setHasExtendedInfo(stationMap.get("has_extended_info") != null ? (Boolean) stationMap.get("has_extended_info") : false);

                // Acrescenta a estação completa à lista que será retornada ao controller.
                radioStations.add(station);
            }
        }

        return radioStations;
    }
}
