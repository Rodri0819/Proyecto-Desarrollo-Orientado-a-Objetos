package org.example.Modelo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DoubleBusTest {
    private DoubleBus doubleBus;
    private int capacidad;

    @BeforeEach
    void setUp() {
        capacidad = 10;
        doubleBus = new DoubleBus(capacidad, "MicroBus");
    }

    @Test
    void testInicializacionCorrecta() {
        assertAll("Inicialización de DoubleBus",
                () -> assertEquals(capacidad, doubleBus.getAsientos().size(), "Debe haber exactamente " + capacidad + " asientos en el bus."),
                () -> assertEquals("MicroBus", doubleBus.getId(), "El tipo de transporte debe ser MicroBus.")
        );
    }

    @Test
    void testTipoAsientos() {
        int mitadCapacidad = ((capacidad-1) / 2) + 4 - ((capacidad-1) / 2) % 4;
        for (int i = 0; i < mitadCapacidad; i++) {
            assertEquals("Salón Cama", doubleBus.getAsientos().get(i).getCategoria(), "Los primeros asientos deben ser de 'Salón Cama'.");
        }
        for (int i = mitadCapacidad; i < capacidad; i++) {
            assertEquals("Semi Cama", doubleBus.getAsientos().get(i).getCategoria(), "Los últimos asientos deben ser de 'Semi Cama'.");
        }
    }

    @Test
    void testAsientosInicialmenteLibres() {
        assertTrue(doubleBus.getAsientos().stream().allMatch(Asiento::isEstado), "Todos los asientos deben estar inicialmente libres.");
    }
}
