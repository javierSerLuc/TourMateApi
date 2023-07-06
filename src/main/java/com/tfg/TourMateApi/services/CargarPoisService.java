package com.tfg.TourMateApi.services;

import com.tfg.TourMateApi.models.EspecificacionFechaRuta;
import com.tfg.TourMateApi.models.Poi;

import java.util.List;

public interface CargarPoisService {
    public List<Poi> cargarPois(int instancia);
    public List<Poi> cargarPoisAbiertos(int instancia, EspecificacionFechaRuta especificacionFechaRuta);
}
