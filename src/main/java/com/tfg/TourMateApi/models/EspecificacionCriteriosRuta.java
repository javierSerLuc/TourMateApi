package com.tfg.TourMateApi.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EspecificacionCriteriosRuta {
    private int criteriosRelevantes;
    private int Distancia;
    private int numPois;
    private int coste;
    private int accesibilidad;

    //TODO: especificacion servicio
}
