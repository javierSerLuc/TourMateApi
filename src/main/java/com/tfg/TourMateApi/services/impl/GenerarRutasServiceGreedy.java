package com.tfg.TourMateApi.services.impl;

import com.tfg.TourMateApi.models.Poi;
import com.tfg.TourMateApi.models.Ruta;
import com.tfg.TourMateApi.services.CargarPoisService;
import com.tfg.TourMateApi.services.GenearRutasService;
import com.tfg.TourMateApi.services.GenerarTimeMatrixService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenerarRutasServiceGreedy implements GenearRutasService {
    private CargarPoisService cargarPoisService;
    private GenerarTimeMatrixService generarTimeMatrixService;

    public GenerarRutasServiceGreedy(CargarPoisService cargarPoisService, GenerarTimeMatrixService generarTimeMatrixService) {
        this.cargarPoisService = cargarPoisService;
        this.generarTimeMatrixService = generarTimeMatrixService;
    }

    @Override
    public List<Ruta> generarRutas(int num) {

        //Obtener Pois
        List<Poi> allPois = cargarPoisService.cargarPois(1);

        //Obneter matriz de tiempos
        List<List<Double>> matrizTiempos = this.generarTimeMatrixService.generarMatrizTiempo(allPois);
        //Aplicar Algoritmo

        //Devolver Rutas
        Ruta rutaDemo = new Ruta(allPois);
        List<Ruta> rutasGeneradas = new ArrayList<>();
        rutasGeneradas.add(rutaDemo);
        return rutasGeneradas;
    }
}
