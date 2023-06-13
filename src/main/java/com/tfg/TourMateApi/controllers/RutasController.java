package com.tfg.TourMateApi.controllers;

import com.tfg.TourMateApi.models.Poi;
import com.tfg.TourMateApi.models.Ruta;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RutasController {

    @GetMapping("/api/getRutas")
    public Ruta genRutas(){
        Poi demo1 = new Poi("Nido del Búho","bar","-36.095.761","371.899.118",	50.0,	"['Mo-Sa 10:00-14:00']");
        Poi demo2 = new Poi("Buzón de Correos", "post_box", "-36.128.731", "371.815.584", 23.0, "['24/7']");
        //return ResponseEntity.ok("End Point encontrado");
        Ruta rutaDemo = new Ruta();
        rutaDemo.addPoi(demo1);
        rutaDemo.addPoi(demo2);
        return rutaDemo;
        //return demo;
    }

}
