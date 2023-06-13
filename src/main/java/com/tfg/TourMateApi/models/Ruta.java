package com.tfg.TourMateApi.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Ruta {
    private List<Poi> pois;

    public Ruta() {
        this.pois = new ArrayList<>();

    }
    public Ruta(List<Poi> pois) {
        //this.pois = new ArrayList<>();
        this.pois = pois;
    }

    public void addPoi(Poi poi){
        this.pois.add(poi);
    }


}
