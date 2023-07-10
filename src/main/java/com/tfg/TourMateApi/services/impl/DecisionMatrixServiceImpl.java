package com.tfg.TourMateApi.services.impl;

import com.tfg.TourMateApi.models.Criterios;
import com.tfg.TourMateApi.models.EspecificacionCriteriosRuta;
import com.tfg.TourMateApi.models.Ruta;
import com.tfg.TourMateApi.services.DecisionMatrixService;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DecisionMatrixServiceImpl implements DecisionMatrixService {
    private List<List<Double>> matrizEvaluacion;
    private List<List<Double>> matrizDecision;
    private List<List<Double>> matrizPonderada;
    private List<Double> vectorPesos;

    private Criterios criterios;

    private int numRutas;


    public DecisionMatrixServiceImpl() {
        criterios = new Criterios();
        matrizEvaluacion = new ArrayList<>();
        matrizDecision = new ArrayList<>();
        vectorPesos = new ArrayList<>();
        matrizPonderada = new ArrayList<>();
    }

    @Override
    public List<List<Double>> getDecisionMatrix(List<Ruta> rutas, EspecificacionCriteriosRuta especificacionCriteriosRuta) {
        /**/
        matrizEvaluacion.clear();
        matrizDecision.clear();
        vectorPesos.clear();
        matrizPonderada.clear();

        matrizEvaluacion = new ArrayList<>();
        matrizDecision = new ArrayList<>();
        vectorPesos = new ArrayList<>();
        matrizPonderada = new ArrayList<>();



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
        /*BUENOS*/
        evaluarCriterioPoisVisitados(rutas);
        /*MALOS*/
        evaluarCriterioDistancia(rutas);


    }

    private void  evaluarCriterioDistancia (List<Ruta> rutas){

        for(int i = 0; i < rutas.size();i++){
            matrizEvaluacion.get(i).add(criterios.getIDISTANCIA(), rutas.get(i).getDistancia());
        }
    }
    private void evaluarCriterioPoisVisitados (List<Ruta> rutas){
        for(int i = 0; i < rutas.size();i++){
            matrizEvaluacion.get(i).add(criterios.getIPOISVISITADOS(), (double) rutas.get(i).getPois().size());
        }
    }

    private void normalizarMatriz(){
        for (List<Double> sublist : matrizEvaluacion) {
            List<Double> sublistCopia = new ArrayList<>(sublist); // Copiar sublist
            matrizDecision.add(sublistCopia); // Agregar sublist copiada a la lista principal
        }

        for(int i = 0; i < numRutas;i++){
            for(int j = 0; j < criterios.getNUMCRITERIOS(); j++){
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
        for (List<Double> sublist : matrizDecision) {
            List<Double> sublistCopia = new ArrayList<>(sublist); // Copiar sublist
            matrizPonderada.add(sublistCopia); // Agregar sublist copiada a la lista principal
        }
        //matrizPonderada = new ArrayList<>(matrizDecision);

        for(int i = 0; i < numRutas;i++){
            for(int j = 0; j < criterios.getNUMCRITERIOS();j++){
                matrizPonderada.get(i).set(j,matrizDecision.get(i).get(j)*vectorPesos.get(j));
            }
        }
    }
    private void inicializarVectorPesos(){
        vectorPesos.add(0.75);
        vectorPesos.add(0.25);
        //vectorPesos.add(1.0);
        //vectorPesos.add(0.0);
    }
}
