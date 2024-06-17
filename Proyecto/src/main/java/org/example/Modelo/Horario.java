package org.example.Modelo;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Horario {
    public static List<LocalTime> generarHorarios(LocalTime horaInicial, LocalTime horaFinal, int intervaloMinutos) {
        if (intervaloMinutos <= 0 || intervaloMinutos > 1440) {
            throw new IllegalArgumentException("El intervalo debe ser mayor que cero y menor que 1440");
        }

        List<LocalTime> horarios = new ArrayList<>();
        LocalTime currentTime = horaInicial;

        while (!currentTime.isAfter(horaFinal)) {
            horarios.add(currentTime);
            currentTime = currentTime.plusMinutes(intervaloMinutos);
        }

        return horarios;
    }
}
