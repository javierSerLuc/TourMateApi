package com.tfg.TourMateApi.services.impl;

import com.tfg.TourMateApi.models.Poi;
import com.tfg.TourMateApi.models.Ruta;
import com.tfg.TourMateApi.services.CargarPoisService;
import com.tfg.TourMateApi.services.GenearRutasService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("ORS")
public class GenerarRutasServiceORS implements GenearRutasService {
    private CargarPoisService cargarPoisService;

    public GenerarRutasServiceORS(CargarPoisService cargarPoisService) {
        this.cargarPoisService = cargarPoisService;
    }

    @Override
    public List<Ruta> generarRutas(int num) {
        //Set datos previos
        List<Ruta> rutas = new ArrayList<>();

        LocalTime dateInicioRuta = LocalTime.of(9,0);
        LocalTime dateFinRuta = LocalTime.of(12,0);
        DayOfWeek diaRuta = DayOfWeek.TUESDAY;

        //obtener pois
        List<Poi> allPois = this.cargarPoisService.cargarPois(1);

        //generar rutas
        for(int i = 0; i < num; i++){
            //call ORS
            

            //añado ruta
            Ruta r = new Ruta()
        }



        //devolver Lista
        return rutas;
    }

    private List<Poi> generarRutaORS(List<Poi> pois){
        //Instaniciar datos
            //crear jobs
            //crear vehicle


        //Call

        //procesar response
    }
}
