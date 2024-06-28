package org.example.Modelo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SingleBusTest {
    private SingleBus singleBus;
    private int capacidad;
    private String busId;

    @BeforeEach
    void setUp() {
        capacidad = 20;  // Define la capacidad del bus para las pruebas
        busId = "BUS-001";
        singleBus = new SingleBus(capacidad, busId);
    }

    @Test
    void testInicializacionCorrecta() {
        assertEquals(capacidad, singleBus.getAsientos().size(), "Debe tener exactamente " + capacidad + " asientos.");
        assertEquals(busId, singleBus.getId(), "El ID del bus debe ser " + busId + ".");
    }

    @Test
    void testTipoAsientos() {
        // Comprueba que todos los asientos sean de tipo "Semi Cama" y estén libres
        singleBus.getAsientos().forEach(asiento -> {
            assertEquals("Semi Cama", asiento.getCategoria(), "Todos los asientos deben ser Semi Cama.");
            assertTrue(asiento.isEstado(), "Todos los asientos deben estar inicialmente libres.");
        });
    }

    @Test
    void testIdConsistency() {
        // Verifica que el ID obtenido mediante getId sea consistente y correcto
        assertEquals(busId, singleBus.getId(), "El ID del bus debe ser consistente y correcto.");
    }
}
