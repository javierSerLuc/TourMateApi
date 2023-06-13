package com.tfg.TourMateApi.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tfg.TourMateApi.models.Poi;
import com.tfg.TourMateApi.services.CargarPoisService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Service
public class CargarPoisServiceImpl implements CargarPoisService {
    @Override
    public List<Poi> cargarPois(int instancia) {

        return cargarPoisJson();
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
