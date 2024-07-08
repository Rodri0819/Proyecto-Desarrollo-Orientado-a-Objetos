package org.example.Modelo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import java.util.List;

class HorarioTest {

    @Test
    void testGenerarHorariosCorrectamente() {
        LocalTime horaInicial = LocalTime.of(9, 0);
        LocalTime horaFinal = LocalTime.of(10, 0);
        int intervaloMinutos = 20;

        List<LocalTime> horarios = Horario.generarHorarios(horaInicial, horaFinal, intervaloMinutos);
        assertAll("Generar horarios correctamente",
                () -> assertEquals(4, horarios.size(), "Debería generar 4 horarios"),
                () -> assertEquals(LocalTime.of(9, 0), horarios.get(0), "El primer horario debería ser a las 9:00"),
                () -> assertEquals(LocalTime.of(9, 20), horarios.get(1), "El segundo horario debería ser a las 9:20"),
                () -> assertEquals(LocalTime.of(9, 40), horarios.get(2), "El tercer horario debería ser a las 9:40"),
                () -> assertEquals(LocalTime.of(10, 0), horarios.get(3), "El cuarto horario debería ser a las 10:00")
        );
    }

    @Test
    void testIntervaloCeroOMenor() {
        LocalTime horaInicial = LocalTime.of(9, 0);
        LocalTime horaFinal = LocalTime.of(10, 0);
        int intervaloMinutos = 0;

        Exception excepcion = assertThrows(IllegalArgumentException.class,
                () -> Horario.generarHorarios(horaInicial, horaFinal, intervaloMinutos),
                "Debería lanzar una excepción si el intervalo es cero o negativo"
        );
        assertEquals("El intervalo debe ser mayor que cero y menor que 1440", excepcion.getMessage());
    }

    @Test
    void testIntervaloExcesivamenteGrande() {
        LocalTime horaInicial = LocalTime.of(9, 0);
        LocalTime horaFinal = LocalTime.of(10, 0);
        int intervaloMinutos = 1441;

        Exception excepcion = assertThrows(IllegalArgumentException.class,
                () -> Horario.generarHorarios(horaInicial, horaFinal, intervaloMinutos),
                "Debería lanzar una excepción si el intervalo supera 1440 minutos"
        );
        assertEquals("El intervalo debe ser mayor que cero y menor que 1440", excepcion.getMessage());
    }

    @Test
    void testHoraFinalAntesDeHoraInicial() {
        LocalTime horaInicial = LocalTime.of(10, 0);
        LocalTime horaFinal = LocalTime.of(9, 0);
        int intervaloMinutos = 30;

        List<LocalTime> horarios = Horario.generarHorarios(horaInicial, horaFinal, intervaloMinutos);
        assertTrue(horarios.isEmpty(), "No debería generar ningún horario si la hora final es antes de la hora inicial.");
    }
}
