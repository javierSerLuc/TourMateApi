package com.tfg.TourMateApi.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Criterios {
    private final int NUMCRITERIOS = 5;

    private final int CRITERIOSBUENOS = 3;
    //BUENOS
    private final int IPOISVISITADOS = 0;
    private final int IMONUMENTOS = 1;
    private final int IANATURALES = 2;

    //MALOS
    private final int IDISTANCIA = 4;
    private final int ICOSTE = 3;

}
