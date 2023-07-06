package com.tfg.TourMateApi.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EspecificacionRuta {
    private EspecificacionCriteriosRuta especificacionCriteriosRuta;
    private EspecificacionFechaRuta especificacionFechaRuta;
}
