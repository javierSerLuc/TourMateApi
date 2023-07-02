package com.tfg.TourMateApi.services.impl;

import com.tfg.TourMateApi.models.Ruta;
import com.tfg.TourMateApi.services.DecisionMatrixService;

import java.util.ArrayList;
import java.util.List;

public class DecisionMatrixServiceImpl implements DecisionMatrixService {
    private List<List<Double>> matrizEvaluacion;
    private List<List<Double>> matrizDecision;
    private List<List<Double>> matrizPonderada;
    private List<Double> vectorPesos;
    private final int NUMCRITERIOS = 2;
    private final int IDISTANCIA = 0;
    private final int IPOISVISITADOS = 1;
    private int numRutas;


    public DecisionMatrixServiceImpl() {
        matrizEvaluacion = new ArrayList<>();
        matrizDecision = new ArrayList<>();
        vectorPesos = new ArrayList<>();
        matrizPonderada = new ArrayList<>();
    }

    @Override
    public List<List<Double>> getDecisionMatrix(List<Ruta> rutas) {
        /**/
        numRutas = rutas.size();
        for(int i = 0; i < numRutas ;i++){
            matrizEvaluacion.add(new ArrayList<>());
        }
        inicializarVectorPesos();


        //Evaluar
        evaluarMatriz(rutas);

        //normalizar
        normalizarMatriz();

        //ponderar
        ponderarMatriz();

        return matrizPonderada;
    }

    private void evaluarMatriz(List<Ruta> rutas){
        //evaluar cada criterio
        /*
                -Distancia *
                -lugares visitados *
                -tipo de service
                -coste
                -accesibilidad
        */
        evaluarCriterioDistancia(rutas);
        evaluarCriterioPoisVisitados(rutas);

    }

    private void  evaluarCriterioDistancia (List<Ruta> rutas){

        for(int i = 0; i < rutas.size();i++){
            matrizEvaluacion.get(i).set(IDISTANCIA,rutas.get(i).getDistancia());
        }
    }
    private void evaluarCriterioPoisVisitados (List<Ruta> rutas){
        for(int i = 0; i < rutas.size();i++){
            matrizEvaluacion.get(i).set(IPOISVISITADOS, (double) rutas.get(i).getPois().size());
        }
    }

    private void normalizarMatriz(){
        matrizDecision = matrizEvaluacion;
        for(int i = 0; i < numRutas;i++){
            for(int j = 0; j < NUMCRITERIOS; j++){
                matrizDecision.get(i).set(j,getValorNormalizado(i,j));
            }
        }
    }

    private Double getValorNormalizado(int i,int j){
        Double sumatorioCuadrado = 0.0;
        for(int k= 0; k < numRutas; k++){
            sumatorioCuadrado += Math.pow(matrizEvaluacion.get(k).get(j),2) ;
        }

        return matrizEvaluacion.get(i).get(j) / (Math.sqrt(sumatorioCuadrado));
    }


    private void ponderarMatriz(){
        matrizPonderada = matrizDecision;

        for(int i = 0; i < numRutas;i++){
            for(int j = 0; j < NUMCRITERIOS;j++){
                matrizPonderada.get(i).set(j,matrizDecision.get(i).get(j)*vectorPesos.get(j));
            }
        }
    }
    private void inicializarVectorPesos(){
        vectorPesos.add(0.75);
        vectorPesos.add(0.25);
    }
}
