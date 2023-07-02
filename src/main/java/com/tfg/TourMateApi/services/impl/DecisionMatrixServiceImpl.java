package com.tfg.TourMateApi.services.impl;

import com.tfg.TourMateApi.models.Ruta;
import com.tfg.TourMateApi.services.DecisionMatrixService;

import java.util.ArrayList;
import java.util.List;

public class DecisionMatrixServiceImpl implements DecisionMatrixService {
    List<List<Double>> matrizEvaluacion;
    final int IDISTANCIA = 0;
    //constantes para indices de criterios

    public DecisionMatrixServiceImpl() {
        matrizEvaluacion = new ArrayList<>();
    }

    @Override
    public List<List<Double>> getDecisionMatrix(List<Ruta> rutas) {
        //Evaluar
        evaluarMatriz(rutas);
        //normalizar

        //ponderar
        return null;
    }

    private void evaluarMatriz(List<Ruta> rutas){
        //evaluar cada criterio
        /*
                -Distancia
                -lugares visitados
                -tipo de service
                -coste
                -accesibilidad
        */
        evaluarCriterioDistancia(rutas);
    }

    private void  evaluarCriterioDistancia (List<Ruta> rutas){

        for(int i = 0; i < rutas.size();i++){
            matrizEvaluacion.get(i).set(IDISTANCIA,rutas.get(i).getDistancia());
        }
    }
}
