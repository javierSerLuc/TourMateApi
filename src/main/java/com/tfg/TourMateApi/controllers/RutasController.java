package com.tfg.TourMateApi.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tfg.TourMateApi.models.Poi;
import com.tfg.TourMateApi.models.Ruta;
import com.tfg.TourMateApi.services.RutasTuristicasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@RestController
public class RutasController {

    private RutasTuristicasService rutasTuristicasService;

    public RutasController(RutasTuristicasService rutasTuristicasService) {
        this.rutasTuristicasService = rutasTuristicasService;
    }

    @GetMapping("/api/getRutas")
    public List<Ruta> genRutas(){
        //Ruta rutaDemo = new Ruta(cargarPoisJson());
        return rutasTuristicasService.getRutas();
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
