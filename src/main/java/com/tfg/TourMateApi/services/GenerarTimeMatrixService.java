package com.tfg.TourMateApi.services;

import com.tfg.TourMateApi.models.Poi;

import java.util.List;

public interface GenerarTimeMatrixService {
    public List<List<Double>> generarMatrizTiempo(List<Poi> pois);
}
