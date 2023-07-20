package com.tfg.TourMateApi.services.impl;

import com.tfg.TourMateApi.models.EspecificacionFechaRuta;
import com.tfg.TourMateApi.models.Poi;
import com.tfg.TourMateApi.models.Ruta;
import com.tfg.TourMateApi.services.CargarPoisService;
import com.tfg.TourMateApi.services.GenearRutasService;
import com.tfg.TourMateApi.services.GenerarTimeMatrixService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
@Qualifier("Greedy")
public class GenerarRutasServiceGreedy implements GenearRutasService {
    private CargarPoisService cargarPoisService;
    private GenerarTimeMatrixService generarTimeMatrixService;

    @Value("${instancia}")
    private int instanciaPois;

    public GenerarRutasServiceGreedy(CargarPoisService cargarPoisService, GenerarTimeMatrixService generarTimeMatrixService) {
        this.cargarPoisService = cargarPoisService;
        this.generarTimeMatrixService = generarTimeMatrixService;
    }

    @Override
    public List<Ruta> generarRutas(int num, EspecificacionFechaRuta especificacionFechaRuta) {

        List<Ruta> rutas = new ArrayList<>();
        LocalTime dateInicioRuta = especificacionFechaRuta.getDateInicioRuta(); /*LocalTime.of(9,0);*/
        LocalTime dateFinRuta = especificacionFechaRuta.getDateFinRuta();/*LocalTime.of(12,0);*/
        DayOfWeek diaRuta = especificacionFechaRuta.getDia();

        //Obtener Pois
        List<Poi> allPois = cargarPoisService.cargarPois(instanciaPois);

        Poi poiInicial = null;
        for(Poi poi : allPois){
            if(Objects.equals(poi.getNombre(), especificacionFechaRuta.getPoiInicio())){
                poiInicial = poi;
                break;
            }
        }

        if (poiInicial == null) {
            Collections.shuffle(allPois);
            for(Poi poi: allPois){
                if(poi.isPOIOpen(diaRuta,dateInicioRuta)){
                    poiInicial = poi;
                    break;
                }
            }

        }

        List<Poi> allPoisInicializado = new ArrayList<>(allPois);
        if(poiInicial != null){

            allPoisInicializado.remove(poiInicial);
            allPoisInicializado.add(0, poiInicial);
        }

        //?-List<Poi> poisToVisit = new ArrayList<>(allPoisInicializado);
        //?-poisToVisit.remove(0);

        //Obneter matriz de tiempos
        List<List<Double>> matrizTiempos = this.generarTimeMatrixService.generarMatrizTiempo(allPoisInicializado);
        //Aplicar Algoritmo

        for(int i = 0; i < num;i++){
            //cambiar el vector y el primero
            rutas.add(busquedaGreedy(allPoisInicializado.get(0),allPoisInicializado,matrizTiempos,dateInicioRuta,dateFinRuta,diaRuta));
        }

        //Devolver Rutas

        return rutas;
    }

    /*private Ruta busquedaGreedy( List<Poi> pois, List<List<Double>> matrizTiempos, LocalTime initTime, LocalTime endTime, DayOfWeek dia){
        Poi initPoi = pois.get(0);
        int initPointIndex = 0;
        Ruta ruta = new Ruta();
        ruta.addPoi(pois.get(initPointIndex));
        LocalTime fechaActual = initTime;
        List<Poi> poisRestantes = new ArrayList<>(pois);

        Collections.shuffle(poisRestantes);

        int initPointShuffledIndex = poisRestantes.indexOf(initPoi);
        Collections.swap(poisRestantes, 0, initPointShuffledIndex);

        while (!poisRestantes.isEmpty() && fechaActual.isBefore(endTime)){
            int indiceActual = poisRestantes.indexOf(initPoi);
            int indiceSiguiente = indiceActual;
            Double tiempoMinimoEntrePois = matrizTiempos.get(indiceActual).get(indiceSiguiente) + poisRestantes.get(indiceSiguiente).getTiempoVisita() * 60;

            for(int i = 0; i < poisRestantes.size();i++){
                Double tiempoCaminoVisita = matrizTiempos.get(indiceActual).get(i) + poisRestantes.get(i).getTiempoVisita() * 60;
                if(tiempoCaminoVisita < tiempoMinimoEntrePois && poisRestantes.get(i).isPOIOpen(dia,fechaActual)){
                    tiempoMinimoEntrePois = matrizTiempos.get(indiceActual).get(i);
                    indiceSiguiente = i;
                }
            }

            Poi siguientePoi = poisRestantes.get(indiceSiguiente);
            ruta.addPoi(siguientePoi);
            poisRestantes.remove(siguientePoi);
            fechaActual = fechaActual.plusSeconds((tiempoMinimoEntrePois.longValue()));

        }

        return ruta;
    }*/
    private Ruta busquedaGreedy(Poi initPoi, List<Poi> pois, List<List<Double>> matrizTiempos, LocalTime initTime, LocalTime endTime, DayOfWeek dia){
        Poi poiActual = initPoi;
        Ruta ruta = new Ruta();
        ruta.addPoi(initPoi);
        LocalTime fechaActual = initTime; //TODO : añadir tiempo visita del primero

        List<Poi> poisRestantes = new ArrayList<>(pois);
        poisRestantes.remove(0);
        Collections.shuffle(poisRestantes);

        //int indiceActual = 0;
        //int indiceSiguiente = 0; //puede ser 1
        while (!poisRestantes.isEmpty() && fechaActual.isBefore(endTime) ){

            Poi siguientePoi = poisRestantes.get(0);
            Double tiempoMinimoEntrePois = matrizTiempos.get(pois.indexOf(ruta.getPois().get(ruta.getPois().size() -1))).get(pois.indexOf(siguientePoi)) + siguientePoi.getTiempoVisita() * 60;

            for(int i = 0; i < poisRestantes.size();i++){
                Double tiempoCaminoVisita = matrizTiempos.get(pois.indexOf(ruta.getPois().get(ruta.getPois().size() -1))).get(pois.indexOf(poisRestantes.get(i))) + poisRestantes.get(i).getTiempoVisita() * 60;
                if(tiempoCaminoVisita < tiempoMinimoEntrePois && poisRestantes.get(i).isPOIOpen(dia,fechaActual)){
                    tiempoMinimoEntrePois = matrizTiempos.get(pois.indexOf(ruta.getPois().get(ruta.getPois().size() -1))).get(pois.indexOf(poisRestantes.get(i)));
                    //indiceSiguiente = i;
                    siguientePoi = poisRestantes.get(i);
                }

            }
            ruta.addPoi(siguientePoi);
            poisRestantes.remove(siguientePoi);
            //poiActual = siguientePoi;
            //indiceActual = indiceSiguiente;
            fechaActual = fechaActual.plusSeconds((tiempoMinimoEntrePois.longValue()));
            long tiempoVisita = (long) (siguientePoi.getTiempoVisita() * 60);
            fechaActual = fechaActual.plusSeconds(tiempoVisita);

        }




        return ruta;
    }
}
