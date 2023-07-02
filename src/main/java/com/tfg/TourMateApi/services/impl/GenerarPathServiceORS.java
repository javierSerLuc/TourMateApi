package com.tfg.TourMateApi.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tfg.TourMateApi.models.Poi;
import com.tfg.TourMateApi.models.Ruta;
import com.tfg.TourMateApi.services.GenerarPathsService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GenerarPathServiceORS implements GenerarPathsService {
    @Override
    public List<Ruta> generarPath(List<Ruta> rutas) {
        //For each
        for(Ruta ruta : rutas){
            generarPath(ruta);
        }
            //llamada api ruta
        return rutas;
    }

    private void generarPath(Ruta ruta){
        ruta.setpath(procesarPath(getPathORM(ruta),ruta));
    }

    private ResponseEntity<String> getPathORM(Ruta ruta){
        RestTemplate restTemplate = new RestTemplate();


        List<List<Double>> coordinates = new ArrayList<>();
        for (Poi poi : ruta.getPois()) {
            List<Double> coordinate = new ArrayList<>();
            coordinate.add(Double.parseDouble(poi.getLng()));
            coordinate.add(Double.parseDouble(poi.getLat()));
            coordinates.add(coordinate);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String payload="";
        Map<String, Object> requestBody = new HashMap<>();
        try {

            requestBody.put("coordinates", coordinates);
            requestBody.put("geometry", "true");
            payload = objectMapper.writeValueAsString(requestBody);
        } catch (Exception e) {
            System.err.println("Error al convertir el objeto a JSON: " + e.getMessage());
            return null;

        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.valueOf("application/geo+json")));
        headers.set("Authorization", "5b3ce3597851110001cf62482294f531796c48e7b388be734057b794");

        HttpEntity<String> requestEntity = new HttpEntity<>(payload, headers);

        String apiUrl = "https://api.openrouteservice.org/v2/directions/foot-walking/geojson";

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity, String.class);
        return responseEntity;
    }

    private List<List<Double>> procesarPath(ResponseEntity<String> response,Ruta ruta){
        String body = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();

        try{
            JsonNode jsonNode = objectMapper.readTree(body);

            JsonNode pathNode = jsonNode
                    .path("features")
                    .path(0)
                    .path("geometry")
                    .path("coordinates");


            //List<List<Double>> path = new ArrayList<>();;
            List<List<Double>> path = objectMapper.readValue(pathNode.toString(), new TypeReference<List<List<Double>>>() {});

            JsonNode distanteNode = jsonNode
                    .path("features")
                    .path(0)
                    .path("properties")
                    .path("summary")
                    .path("distance");
            ruta.setDistancia(objectMapper.readValue(distanteNode.toString(),new TypeReference<Double>(){}));

            /*for(int i = 0; i < pathNode.size();i++){
                JsonNode pointnode = pathNode.get(i);
                 List<Double> minipath = new ArrayList<>();
                 for(int j = 0; j < pointnode.size();j++){
                     JsonNode coordinateNode = pointnode.get(i);
                     double coordinate = coordinateNode.asDouble();
                     minipath.add(coordinate);

                 }
                 path.add(minipath);
            }*/

            return path;

        }
        catch (Exception e){
            return null;
        }

    }
}
