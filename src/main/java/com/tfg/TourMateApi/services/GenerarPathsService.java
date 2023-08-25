package com.tfg.TourMateApi.services;

import com.tfg.TourMateApi.models.Ruta;

import java.util.List;

public interface GenerarPathsService {
    List<Ruta> generarPath(List<Ruta> rutas, String vehicle);
}
