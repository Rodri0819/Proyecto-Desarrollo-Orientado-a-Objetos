package org.example.Modelo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DoubleBusTest {
    private DoubleBus doubleBus;
    private int capacidad;

    @BeforeEach
    void setUp() {
        capacidad = 10; // Elegir un número par para dividir fácilmente en mitades
        doubleBus = new DoubleBus(capacidad, "DB-100");
    }

    @Test
    void testInicializacionCorrecta() {
        assertAll("Inicialización de DoubleBus",
                () -> assertEquals(capacidad, doubleBus.getAsientos().size(), "Debe haber exactamente " + capacidad + " asientos en el bus."),
                () -> assertEquals("DB-100", doubleBus.getId(), "El ID del bus debe ser DB-100.")
        );
    }

    @Test
    void testTipoAsientos() {
        int mitadCapacidad = capacidad / 2;
        for (int i = 0; i < mitadCapacidad; i++) {
            assertEquals("Salón Cama", doubleBus.getAsientos().get(i).getCategoria(), "Los primeros " + mitadCapacidad + " asientos deben ser de 'Salón Cama'.");
        }
        for (int i = mitadCapacidad; i < capacidad; i++) {
            assertEquals("Semi Cama", doubleBus.getAsientos().get(i).getCategoria(), "Los últimos " + (capacidad - mitadCapacidad) + " asientos deben ser de 'Semi Cama'.");
        }
    }

    @Test
    void testAsientosInicialmenteLibres() {
        assertTrue(doubleBus.getAsientos().stream().allMatch(Asiento::isEstado), "Todos los asientos deben estar inicialmente libres.");
    }
}
