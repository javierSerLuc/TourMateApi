package com.tfg.TourMateApi.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tfg.TourMateApi.models.Poi;
import com.tfg.TourMateApi.models.Ruta;
import com.tfg.TourMateApi.services.CargarPoisService;
import com.tfg.TourMateApi.services.RutasTuristicasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.*;

@RestController
public class RutasController {

    private RutasTuristicasService rutasTuristicasService;

    @Autowired
    private CargarPoisService cargarPoisService;

    public RutasController(RutasTuristicasService rutasTuristicasService) {
        this.rutasTuristicasService = rutasTuristicasService;
    }

    @GetMapping("/api/getRutas")
    public List<Ruta> genRutas(){
        //Ruta rutaDemo = new Ruta(cargarPoisJson());
        return rutasTuristicasService.getRutas();
    }

    @GetMapping("/api/getMatrix")
    public ResponseEntity<String> getMatrix(){
        RestTemplate restTemplate = new RestTemplate();
        List<Poi> pois = cargarPoisService.cargarPois(1);

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

        int statusCode = responseEntity.getStatusCodeValue();
        HttpHeaders responseHeaders = responseEntity.getHeaders();
        String responseBody = responseEntity.getBody();

        return responseEntity;


    }


    public List<Poi> cargarPoisJson(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File("src/main/resources/poisJson/response-100.json");
            JsonNode jsonNode = objectMapper.readTree(file);
            JsonNode poisProperty = jsonNode.get("pois");
            Poi[] poisArray = objectMapper.convertValue(poisProperty, Poi[].class);
            return Arrays.asList(poisArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




}
