package com.tfg.TourMateApi.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tfg.TourMateApi.Deserialices.DayOfWeekDeserializer;
import com.tfg.TourMateApi.Deserialices.LocalTimeDeserializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class EspecificacionFechaRuta {
    private String ciudad;
    @JsonDeserialize(using = DayOfWeekDeserializer.class)
    private DayOfWeek dia;
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime dateInicioRuta;
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime dateFinRuta;
    private String poiInicio;


}
