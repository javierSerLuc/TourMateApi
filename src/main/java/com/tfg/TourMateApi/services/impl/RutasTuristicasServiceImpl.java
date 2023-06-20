package com.tfg.TourMateApi.services.impl;

import com.tfg.TourMateApi.models.Ruta;
import com.tfg.TourMateApi.services.GenearRutasService;
import com.tfg.TourMateApi.services.GenerarPathsService;
import com.tfg.TourMateApi.services.RutasTuristicasService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RutasTuristicasServiceImpl implements RutasTuristicasService {
    private GenearRutasService genearRutasService;
    private GenerarPathsService generarPathsService;

    public RutasTuristicasServiceImpl(GenearRutasService genearRutasService,GenerarPathsService generarPathsService) {
        this.genearRutasService = genearRutasService;
        this.generarPathsService = generarPathsService;
    }

    @Override
    public List<Ruta> getRutas(int numRutas) {

        //Instaciar objetos

        //generar Rutas

        //para cada ruta de la lista le añado el campo de path
        List<Ruta> rutas = genearRutasService.generarRutas(numRutas);
        rutas = generarPathsService.generarPath(rutas);



        //Ordenarlas

        //Devolverlas
        //return genearRutasService.generarRutas(numRutas);
        return rutas;
    }
}
