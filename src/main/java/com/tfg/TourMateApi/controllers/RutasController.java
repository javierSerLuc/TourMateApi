package com.tfg.TourMateApi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RutasController {

    @GetMapping("/api/getRutas")
    public ResponseEntity genRutas(){
        return ResponseEntity.ok("End Point encontrado");
    }

}
