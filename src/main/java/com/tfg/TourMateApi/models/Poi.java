package com.tfg.TourMateApi.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Poi {
    private String nombre;
    private String servicio;
    private String lat;
    private String lng;
    private double tiempoVisita;
    private List<String> horario;

    public Poi(String nombre, String servicio, String lng, String lat, double tiempoVisita, String horario) {
        this.nombre = nombre;
        this.servicio = servicio;
        this.lat = lat;
        this.lng = lng;
        this.tiempoVisita = tiempoVisita;
        this.horario = new ArrayList<>();
        this.setFranjaHoraria(horario);
    }

    private void setFranjaHoraria(String Horario){

        if(Objects.equals(Horario, "['24/7']")){
            this.horario.add("Mo-Su 00:00-23:59");
            
        }
        else{
            //Eliminate the first and last character of the string : []
            Horario = Horario.substring(1, Horario.length() - 1);
            //Eliminate all the characters '
            Horario = Horario.replace("'", "");
            // splice string Horario by the colon ['Mo-Fr 13:30-18:00', 'Sa-Su 09:00-11:00']
            String[] horario = Horario.split(", ");
            Collections.addAll(this.horario, horario);
        }

    }

    public boolean isPOIOpen(DayOfWeek dayOfWeek, LocalTime currentTime){
        if(this.horario.size() == 1){
            return isPOIOpen(dayOfWeek, currentTime, horario.get(0));
        }
        return isPOIOpen(dayOfWeek, currentTime, horario.get(0)) && isPOIOpen(dayOfWeek, currentTime, horario.get(1));
    }


    

    private static boolean isPOIOpen(DayOfWeek dayOfWeek, LocalTime currentTime, String openingHours) {
        // Formato esperado de las horas de apertura: "Mo-Fr 13:30-18:00"
        String[] parts = openingHours.split(" ");
        String[] days = parts[0].split("-");
        String[] hours = parts[1].split("-");
        
        
    
        // Verificar si el POI abre los días adecuados
        DayOfWeek startDay = convertDayOfWeek(days[0]);
        DayOfWeek endDay = convertDayOfWeek(days[1]);
        
        if (dayOfWeek.compareTo(startDay) < 0 || dayOfWeek.compareTo(endDay) > 0) {
            return false;
        }

        // Verificar si el POI está abierto en la hora actual
        LocalTime startTime = LocalTime.parse(hours[0], DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime endTime = LocalTime.parse(hours[1], DateTimeFormatter.ofPattern("HH:mm"));
        return !currentTime.isBefore(startTime) && !currentTime.isAfter(endTime);
   
    
    
    }

    private static DayOfWeek convertDayOfWeek(String day) {
        String normalizedDay = day.toLowerCase();

        return switch (normalizedDay) {
            case "mo" -> DayOfWeek.MONDAY;
            case "tu" -> DayOfWeek.TUESDAY;
            case "we" -> DayOfWeek.WEDNESDAY;
            case "th" -> DayOfWeek.THURSDAY;
            case "fr" -> DayOfWeek.FRIDAY;
            case "sa" -> DayOfWeek.SATURDAY;
            case "su" -> DayOfWeek.SUNDAY;
            default -> throw new IllegalArgumentException("Invalid day of week: " + day);
        };
    }



    


    
    
}
