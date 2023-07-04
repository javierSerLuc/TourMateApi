package com.tfg.TourMateApi.services.impl;

import com.tfg.TourMateApi.models.Criterios;
import com.tfg.TourMateApi.models.Ruta;
import com.tfg.TourMateApi.services.DecisionMatrixService;
import com.tfg.TourMateApi.services.TopsisService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TopsisServiceImpl implements TopsisService {
    private DecisionMatrixService decisionMatrixService;
    private Criterios criterios;
    private List<Double> pis;
    private List<Double> nis;
    private List<List<Double>> distancias;

    public TopsisServiceImpl(DecisionMatrixService decisionMatrixService) {
        this.criterios = new Criterios();
        this.decisionMatrixService = decisionMatrixService;
        pis = new ArrayList<>();
        nis = new ArrayList<>();
        distancias = new ArrayList<>();
    }

    @Override
    public List<Ruta> ordenacionTopsis(List<Ruta> rutas) {
        //inicializar
        List<Ruta> rutasOrdenadas = rutas;

        for(int i = 0; i < rutas.size();i++){
            distancias.add(new ArrayList<>());
        }

        //obtener matriz decision
        List<List<Double>> matrizDecision = decisionMatrixService.getDecisionMatrix(rutas);

        //Pis NIS
        inicializarPisNis(matrizDecision);
        //Distancia
        calcularDistancias(matrizDecision,rutas);
        //RS
        calcularProximidadRelativa(rutas);

        //Ordenar
        Collections.sort(rutasOrdenadas);
        return rutasOrdenadas;
    }

    private void inicializarPisNis(List<List<Double>>matrizDecision){
        inicializarNis(matrizDecision);
        inicializarPis(matrizDecision);
    }

    private void inicializarPis(List<List<Double>>matrizDecision){
        for(int j = 0; j < criterios.getCRITERIOSBUENOS();j++ ){
            //
            Double valorMaximizar = 0.0;
            for(int i = 0; i < matrizDecision.size();i++){
                if(matrizDecision.get(i).get(j) > valorMaximizar)
                    valorMaximizar = matrizDecision.get(i).get(j);
            }
            pis.add(valorMaximizar);
        }

        for(int j = criterios.getCRITERIOSBUENOS(); j < criterios.getNUMCRITERIOS();j++ ){
            //
            Double valorMinimizar = Double.MAX_VALUE;
            for(int i = 0; i < matrizDecision.size();i++){
                if(matrizDecision.get(i).get(j) < valorMinimizar)
                    valorMinimizar = matrizDecision.get(i).get(j);
            }
            pis.add(valorMinimizar);
        }

    }

    private void inicializarNis(List<List<Double>>matrizDecision){
        for(int j = 0; j < criterios.getCRITERIOSBUENOS();j++ ){
            Double valorMinimizar = Double.MAX_VALUE;
            for(int i = 0; i < matrizDecision.size();i++){
                if(matrizDecision.get(i).get(j) < valorMinimizar)
                    valorMinimizar = matrizDecision.get(i).get(j);
            }
            nis.add(valorMinimizar);

        }



        for(int j = criterios.getCRITERIOSBUENOS(); j < criterios.getNUMCRITERIOS();j++ ){
            //
            Double valorMaximizar = 0.0;
            for(int i = 0; i < matrizDecision.size();i++){
                if(matrizDecision.get(i).get(j) > valorMaximizar)
                    valorMaximizar = matrizDecision.get(i).get(j);
            }
            nis.add(valorMaximizar);

        }

    }

    private void calcularDistancias(List<List<Double>>matrizDecision,List<Ruta> rutas){
        for(int i = 0; i < rutas.size();i++ ){
            calcularDistanciaPis(rutas.get(i),i,matrizDecision);
            calcularDistanciaNis(rutas.get(i),i,matrizDecision);

        }
    }

    private void calcularDistanciaPis(Ruta r,int indice,List<List<Double>>matrizDecision){
        Double diferenciaCuadrados = 0.0;
        for(int j = 0; j < criterios.getNUMCRITERIOS();j++){
            diferenciaCuadrados += Math.pow(matrizDecision.get(indice).get(j) - pis.get(j),2);
        }
        distancias.get(indice).add(0,Math.sqrt(diferenciaCuadrados));

    }

    private void calcularDistanciaNis(Ruta r,int indice,List<List<Double>>matrizDecision){
        Double diferenciaCuadrados = 0.0;
        for(int j = 0; j < criterios.getNUMCRITERIOS();j++){
            diferenciaCuadrados += Math.pow(matrizDecision.get(indice).get(j) - nis.get(j),2);
        }
        distancias.get(indice).add(1,Math.sqrt(diferenciaCuadrados));

    }

    private void calcularProximidadRelativa(List<Ruta> rutas){
        for(int i = 0; i < rutas.size();i++){
            Double distanciaPis = distancias.get(i).get(0);
            Double distanciaNis = distancias.get(i).get(1);

            rutas.get(i).setCoeficienteRS(distanciaNis / (distanciaPis + distanciaNis));
        }
    }
}
