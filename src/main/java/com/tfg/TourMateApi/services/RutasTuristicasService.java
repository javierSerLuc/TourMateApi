package com.tfg.TourMateApi.services;

import com.tfg.TourMateApi.models.EspecificacionRuta;
import com.tfg.TourMateApi.models.Ruta;

import java.util.List;

public interface RutasTuristicasService {
    public List<Ruta> getRutas(int numRutas, EspecificacionRuta especificacionRuta);
}
