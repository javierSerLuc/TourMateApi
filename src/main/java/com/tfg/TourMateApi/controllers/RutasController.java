package com.tfg.TourMateApi.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tfg.TourMateApi.models.EspecificacionFechaRuta;
import com.tfg.TourMateApi.models.EspecificacionRuta;
import com.tfg.TourMateApi.models.Poi;
import com.tfg.TourMateApi.models.Ruta;
import com.tfg.TourMateApi.services.CargarPoisService;
import com.tfg.TourMateApi.services.GenerarTimeMatrixService;
import com.tfg.TourMateApi.services.RutasTuristicasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.*;

@RestController
public class RutasController {

    private RutasTuristicasService rutasTuristicasService;



    public RutasController(RutasTuristicasService rutasTuristicasService) {
        this.rutasTuristicasService = rutasTuristicasService;
    }

    @GetMapping("/api/getRutas/{numRutas}")
    public ResponseEntity<Object> genRutas(@PathVariable int numRutas){
        //Ruta rutaDemo = new Ruta(cargarPoisJson());
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("numeroRutas",numRutas);
        respuesta.put("rutas",rutasTuristicasService.getRutas(numRutas)) ;

        return new ResponseEntity<>(respuesta,(HttpStatus)HttpStatus.OK);
    }

    @GetMapping("/api/getPois")
    public Ruta genPois(){
        Ruta rutaDemo = new Ruta(cargarPoisJson());
        return rutaDemo;
        //return rutasTuristicasService.getRutas();
    }

    @PostMapping("api/prDates")
    public EspecificacionRuta genPr(@RequestBody EspecificacionRuta especificacionRuta){
        return especificacionRuta;
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
