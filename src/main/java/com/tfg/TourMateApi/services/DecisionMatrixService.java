package com.tfg.TourMateApi.services;

import com.tfg.TourMateApi.models.Ruta;

import java.util.List;

public interface DecisionMatrixService {
    List<List<Double>> getDecisionMatrix(List<Ruta> rutas);

}
