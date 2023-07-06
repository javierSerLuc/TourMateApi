package com.tfg.TourMateApi.Deserialices;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.DayOfWeek;

public class DayOfWeekDeserializer extends JsonDeserializer<DayOfWeek> {
    @Override
    public DayOfWeek deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String dayOfWeekString = jsonParser.getText();

        // Lógica de conversión personalizada
        if (dayOfWeekString.equalsIgnoreCase("Lunes")) {
            return DayOfWeek.MONDAY;
        } else if (dayOfWeekString.equalsIgnoreCase("Martes")) {
            return DayOfWeek.TUESDAY;
        } else if (dayOfWeekString.equalsIgnoreCase("Miércoles")) {
            return DayOfWeek.WEDNESDAY;
        } else if (dayOfWeekString.equalsIgnoreCase("Jueves")) {
            return DayOfWeek.THURSDAY;
        } else if (dayOfWeekString.equalsIgnoreCase("Viernes")) {
            return DayOfWeek.FRIDAY;
        } else if (dayOfWeekString.equalsIgnoreCase("Sábado")) {
            return DayOfWeek.SATURDAY;
        } else if (dayOfWeekString.equalsIgnoreCase("Domingo")) {
            return DayOfWeek.SUNDAY;
        }

        throw new IOException("Invalid day of week: " + dayOfWeekString);
    }
}
