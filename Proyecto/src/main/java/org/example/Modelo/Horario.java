package org.example.Modelo;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase crea un metodo para generar una lista de horarios
 * basados en una hora inicial(8:00), una hora final(22:00) y un intervalo en minutos(30 minutos).
 * Esta clase es útil para generar horarios en intervalos regulares dentro de un rango de tiempo específico.
 */
public class Horario {

    /**
     * Genera una lista de horarios desde una hora inicial hasta una hora final,
     * con un intervalo específico en minutos entre cada horario.
     * @param horaInicial la hora inicial desde donde comenzar a generar horarios.
     * @param horaFinal la hora final hasta donde generar horarios.
     * @param intervaloMinutos el intervalo en minutos entre cada horario.
     * @return una lista de horarios generados.
     * @throws IllegalArgumentException si el intervalo es menor o igual a cero o mayor a 1440 minutos.
     */
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
