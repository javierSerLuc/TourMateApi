package com.tfg.TourMateApi.controllers;

import com.tfg.TourMateApi.models.Poi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RutasController {

    @GetMapping("/api/getRutas")
    public Poi genRutas(){
        Poi demo = new Poi("Nido del Búho","bar","-36.095.761","371.899.118",	50.0,	"['Mo-Sa 10:00-14:00']");
        //return ResponseEntity.ok("End Point encontrado");
        return demo;
    }

}
