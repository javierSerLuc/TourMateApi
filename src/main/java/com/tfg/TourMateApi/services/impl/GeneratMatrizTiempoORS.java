package com.tfg.TourMateApi.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tfg.TourMateApi.models.Poi;
import com.tfg.TourMateApi.services.GenerarTimeMatrixService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneratMatrizTiempoORS implements GenerarTimeMatrixService {
    @Override
    public List<List<Double>> generarMatrizTiempo(List<Poi> pois) {
        ResponseEntity<String> apiRespone = callORMApi(pois);
        return procesarMatriz(apiRespone);
    }

    private ResponseEntity<String> callORMApi(List<Poi> pois){
        RestTemplate restTemplate = new RestTemplate();
        //List<Poi> pois = cargarPoisService.cargarPois(1);

        List<List<Double>> coordinates = new ArrayList<>();
        for (Poi poi : pois) {
            List<Double> coordinate = new ArrayList<>();
            coordinate.add(Double.parseDouble(poi.getLng()));
            coordinate.add(Double.parseDouble(poi.getLat()));
            coordinates.add(coordinate);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String payload;
        Map<String, Object> requestBody = new HashMap<>();
        try {

            requestBody.put("locations", coordinates);
            requestBody.put("metrics", List.of("duration"));
            payload = objectMapper.writeValueAsString(requestBody);
        } catch (Exception e) {
            System.err.println("Error al convertir el objeto a JSON: " + e.getMessage());
            return null;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "5b3ce3597851110001cf62482294f531796c48e7b388be734057b794");

        HttpEntity<String> requestEntity = new HttpEntity<>(payload, headers);

        String apiUrl = "https://api.openrouteservice.org/v2/matrix/foot-walking";

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity, String.class);
        return responseEntity;
    }

    private List<List<Double>> procesarMatriz (ResponseEntity<String> response){
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonNode jsonNode = objectMapper.readTree(String.valueOf(response));
            JsonNode durationsNode = jsonNode.get("durations");

            List<List<Double>> durationsList = new ArrayList<>();

            for (int i = 0; i < durationsNode.size(); i++) {
                JsonNode rowNode = durationsNode.get(i);
                //if (rowNode.isArray()) {
                    List<Double> rowList = new ArrayList<>();

                    for (int j = 0; j < rowNode.size(); j++) {
                        JsonNode durationNode = rowNode.get(j);
                        double duration = durationNode.asDouble();
                        rowList.add(duration);
                    }

                    durationsList.add(rowList);
                //}
            }

            return durationsList;
        } catch (Exception e){
            return null;
        }

    }
}
