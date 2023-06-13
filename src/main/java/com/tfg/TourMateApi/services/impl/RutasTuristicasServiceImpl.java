package com.tfg.TourMateApi.services.impl;

import com.tfg.TourMateApi.models.Ruta;
import com.tfg.TourMateApi.services.GenearRutasService;
import com.tfg.TourMateApi.services.RutasTuristicasService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RutasTuristicasServiceImpl implements RutasTuristicasService {
    private GenearRutasService genearRutasService;

    public RutasTuristicasServiceImpl(GenearRutasService genearRutasService) {
        this.genearRutasService = genearRutasService;
    }

    @Override
    public List<Ruta> getRutas() {

        //Instaciar objetos

        //generar Rutas

        //Ordenarlas

        //Devolverlas
        return genearRutasService.generarRutas(1);
    }
}
