package com.tfg.TourMateApi.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Ruta {
    private List<Poi> pois;
    private List<List<Double>> path;

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


}
