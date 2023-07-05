package com.tfg.TourMateApi.services.impl;

import com.tfg.TourMateApi.models.EspecificacionRuta;
import com.tfg.TourMateApi.models.Ruta;
import com.tfg.TourMateApi.services.GenearRutasService;
import com.tfg.TourMateApi.services.GenerarPathsService;
import com.tfg.TourMateApi.services.RutasTuristicasService;
import com.tfg.TourMateApi.services.TopsisService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RutasTuristicasServiceImpl implements RutasTuristicasService {
    private GenearRutasService genearRutasService;
    private GenerarPathsService generarPathsService;
    private TopsisService topsisService;

    public RutasTuristicasServiceImpl(@Qualifier("Greedy") GenearRutasService genearRutasService, GenerarPathsService generarPathsService,TopsisService topsisService) {
        this.genearRutasService = genearRutasService;
        this.generarPathsService = generarPathsService;
        this.topsisService = topsisService;
    }

    @Override
    public List<Ruta> getRutas(int numRutas, EspecificacionRuta especificacionRuta) {

        //Instaciar objetos

        //generar Rutas

        //para cada ruta de la lista le añado el campo de path
        List<Ruta> rutas = genearRutasService.generarRutas(numRutas,especificacionRuta.getEspecificacionFechaRuta());
        rutas = generarPathsService.generarPath(rutas);



        //Ordenarlas
        List<Ruta> rutasOrdenadas = topsisService.ordenacionTopsis(rutas);

        //Devolverlas
        //return genearRutasService.generarRutas(numRutas);
        //return rutas;
        return rutasOrdenadas;
    }
}
