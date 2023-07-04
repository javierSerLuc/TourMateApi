package com.tfg.TourMateApi.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Ruta implements Comparable<Ruta> {
    private List<Poi> pois;
    private List<List<Double>> path;
    private Double distancia;
    private Double coeficienteRS;

    public Ruta() {
        this.pois = new ArrayList<>();
        this.path = new ArrayList<>();

    }
    public Ruta(List<Poi> pois) {
        //this.pois = new ArrayList<>();
        this.pois = pois;
        this.path = new ArrayList<>();
    }

    public void addPoi(Poi poi){
        this.pois.add(poi);
    }

    public void setpath(List<List<Double>> path){
        this.path = path;
    }
    public void setDistancia(Double distancia){this.distancia = distancia;}
    public void setCoeficienteRS(Double coeficienteRS){this.coeficienteRS = coeficienteRS;}


    @Override
    public int compareTo(Ruta otraRuta) {
        if (this.coeficienteRS > otraRuta.getCoeficienteRS()) {
            return -1; // Indica que el objeto actual es mayor
        } else if (this.coeficienteRS < otraRuta.getCoeficienteRS()) {
            return 1; // Indica que el objeto actual es menor
        } else {
            return 0; // Indica que ambos objetos son iguales
        }
    }
}
