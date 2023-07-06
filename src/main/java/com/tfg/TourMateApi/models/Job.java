package com.tfg.TourMateApi.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Job {
    private int id;
    private int service;
    private List<Integer> skills;
    private List<Double> location;
    private List<Integer> amount;
    private List<List<Integer>> time_window;

    public Job(Poi poi,int id){
        this.skills = new ArrayList<>();
        this.location = new ArrayList<>();
        this.time_window = new ArrayList<>();
        this.amount = new ArrayList<>();
        amount.add(1);

        this.id = id;
        this.service = (int) poi.getTiempoVisita() * 60;
        skills.add(1);
        this.location.add(Double.parseDouble(poi.getLng()));
        this.location.add(Double.parseDouble(poi.getLat()));

        this.time_window = procesarVentanaTiempo(poi.getHorario());

    }

    private List<List<Integer>> procesarVentanaTiempo (List<String> horario){
        List<List<Integer>> ventanasTiempo = new ArrayList<>();
        List<Integer> ventana1 = new ArrayList<>();
        if(horario.get(0) == "Mo-Su 00:00-23:59"){

            ventana1.add(0);
            ventana1.add(86399);
            ventanasTiempo.add(ventana1);
            return ventanasTiempo;
        }

        ventana1 = procesarVentanaParcial(horario.get(0));
        ventanasTiempo.add(ventana1);
        if(horario.size() > 1){
            List<Integer> ventana2 = new ArrayList<>();
            ventana2 = procesarVentanaParcial(horario.get(1));
            ventanasTiempo.add(ventana2);
        }
        return ventanasTiempo;

    }

    private List<Integer >procesarVentanaParcial (String ventana){
        List<Integer> ventanaTiempo = new ArrayList<>();
        String[] parts = ventana.split(" ");
        String[] days = parts[0].split("-");
        String[] hours = parts[1].split("-");

        if (hours[0].split(":")[0].length() == 1) {
            hours[0] = "0" + hours[0];
        }

        // Verificar si el POI está abierto en la hora actual
        LocalTime startTime = LocalTime.parse(hours[0], DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime endTime = LocalTime.parse(hours[1], DateTimeFormatter.ofPattern("HH:mm"));

        ventanaTiempo.add(startTime.toSecondOfDay());
        ventanaTiempo.add(endTime.toSecondOfDay());

        return ventanaTiempo;

    }


}
