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

        return cargarPoisJson(instancia);
    }

    private List<Poi> cargarPoisJson(int instancia){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File(elegirDataSet(instancia));
            JsonNode jsonNode = objectMapper.readTree(file);
            JsonNode poisProperty = jsonNode.get("pois");
            Poi[] poisArray = objectMapper.convertValue(poisProperty, Poi[].class);
            return Arrays.asList(poisArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String elegirDataSet(int instancia){
        String dataSet;
        switch (instancia){
            case 1:
                dataSet = "src/main/resources/poisJson/1-response-51.json";
                break;
            default:
                dataSet = "src/main/resources/poisJson/response-100.json";
                break;

        }
        return dataSet;

    }
}
