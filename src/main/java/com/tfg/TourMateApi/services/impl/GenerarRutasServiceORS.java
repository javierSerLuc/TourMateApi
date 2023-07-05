package com.tfg.TourMateApi.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tfg.TourMateApi.models.EspecificacionFechaRuta;
import com.tfg.TourMateApi.models.Job;
import com.tfg.TourMateApi.models.Poi;
import com.tfg.TourMateApi.models.Ruta;
import com.tfg.TourMateApi.services.CargarPoisService;
import com.tfg.TourMateApi.services.GenearRutasService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

@Service
@Qualifier("ORS")
public class GenerarRutasServiceORS implements GenearRutasService {
    private CargarPoisService cargarPoisService;

    public GenerarRutasServiceORS(CargarPoisService cargarPoisService) {
        this.cargarPoisService = cargarPoisService;
    }

    @Override
    public List<Ruta> generarRutas(int num, EspecificacionFechaRuta especificacionFechaRuta) {
        //Set datos previos
        List<Ruta> rutas = new ArrayList<>();

       /* LocalTime dateInicioRuta = LocalTime.of(9,0);
        LocalTime dateFinRuta = LocalTime.of(19,0);
        DayOfWeek diaRuta = DayOfWeek.TUESDAY;*/

        LocalTime dateInicioRuta = especificacionFechaRuta.getDateInicioRuta(); /*LocalTime.of(9,0);*/
        LocalTime dateFinRuta = especificacionFechaRuta.getDateFinRuta();/*LocalTime.of(12,0);*/
        DayOfWeek diaRuta = especificacionFechaRuta.getDia();

        //obtener pois
        List<Poi> allPois = this.cargarPoisService.cargarPois(1);

        //generar rutas
        for(int i = 0; i < num; i++){
            //call ORS


            //añado ruta
            Collections.shuffle(allPois);
            Ruta r = new Ruta(generarRutaORS(allPois,dateInicioRuta,dateFinRuta,diaRuta));
            rutas.add(r);
        }



        //devolver Lista
        return rutas;
    }

    private List<Poi> generarRutaORS(List<Poi> pois, LocalTime initTime, LocalTime endTime, DayOfWeek dia){
        List<Poi> poisVisitados = new ArrayList<>();
        //Instaniciar datos
        List<Job> jobs = new ArrayList<>();
            //crear jobs de solo los pois abiertos ese dia
        for(int i = 0; i < pois.size();i++){
            if(pois.get(i).isPoiOpenDay(dia)){
                jobs.add(new Job(pois.get(i),i));
            }
        }
        if(jobs.size() > 70){
            Random random = new Random();
            while(jobs.size() > 55){
                int indiceEliminar = random.nextInt(jobs.size());
                jobs.remove(indiceEliminar);
            }
        }
            //crear vehicle
        List<Map<String, Object>> vehicles = new ArrayList<>();
        Map<String, Object> vehicle1 = new HashMap<>();
        vehicle1.put("id", 1);
        vehicle1.put("profile", "foot-walking");
        vehicle1.put("start", new double[]{jobs.get(0).getLocation().get(0), jobs.get(0).getLocation().get(1)});
        vehicle1.put("end", new double[]{jobs.get(0).getLocation().get(0), jobs.get(0).getLocation().get(1)});
        vehicle1.put("capacity", new int[]{pois.size()});
        vehicle1.put("skills", new int[]{1, 14});
        vehicle1.put("time_window", new int[]{initTime.toSecondOfDay(), endTime.toSecondOfDay()});
        
        vehicles.add(vehicle1);


        //Call
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> requestBody = new HashMap<>();
        String payload;
        ObjectMapper objectMapper = new ObjectMapper();


        try {
            requestBody.put("jobs", jobs);
            requestBody.put("vehicles", vehicles);
            payload = objectMapper.writeValueAsString(requestBody);
        } catch (Exception e) {
            System.err.println("Error al convertir el objeto a JSON: " + e.getMessage());
            return null;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.valueOf("application/geo+json")));
        headers.set("Authorization", "5b3ce3597851110001cf62482294f531796c48e7b388be734057b794");

        HttpEntity<String> requestEntity = new HttpEntity<>(payload, headers);

        String apiUrl = "https://api.openrouteservice.org/optimization";

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity, String.class);

        //procesar response
        String body = responseEntity.getBody();
        try{
            JsonNode jsonNode = objectMapper.readTree(body);

            JsonNode stepsNode = jsonNode
                    .path("routes")
                    .path(0)
                    .path("steps");

            List<Integer> jobIds = new ArrayList<>();

            for (JsonNode stepNode : stepsNode) {
                String stepType = stepNode.path("type").asText();

                if (stepType.equals("job")) {
                    int jobId = stepNode.path("id").asInt();
                    jobIds.add(jobId);
                }
            }


            for(int i = 0; i < jobIds.size(); i++){
                poisVisitados.add(pois.get(jobIds.get(i)));
               //poisAVisitar1.add(pois.get(jobs.get(jobIds.get(i)).getId()));
               //Poi poiAVisitar = pois.get(jobs.get(jobIds.get(i)).getId());
               //poisAVisitar2.add(poiAVisitar);
                //poisVisitados.add(poiAVisitar);
            }

            return poisVisitados;


            //List<List<Double>> path = new ArrayList<>();;


        }
        catch (Exception e){
            return null;
        }
    }
}
