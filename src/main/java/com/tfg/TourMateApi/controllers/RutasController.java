package com.tfg.TourMateApi.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tfg.TourMateApi.models.Poi;
import com.tfg.TourMateApi.models.Ruta;
import com.tfg.TourMateApi.services.CargarPoisService;
import com.tfg.TourMateApi.services.GenerarTimeMatrixService;
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
            File file = new File("src/main/resources/poisJson/1-response-51.json");
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
