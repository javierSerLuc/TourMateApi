package com.tfg.TourMateApi.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Criterios {
    private final int NUMCRITERIOS = 4;

    private final int CRITERIOSBUENOS = 2;
    //BUENOS
    private final int IPOISVISITADOS = 0;
    private final int IACCESIBILIDAD = 1;

    //MALOS
    private final int IDISTANCIA = 2;
    private final int ICOSTE = 3;

}
