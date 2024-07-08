package org.example.Modelo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
class BusTest {
    private Bus bus;

    @BeforeEach
    void setUp() {
        bus = new Bus(2, "BUS-123") {
            @Override
            public Bus clone() {
                return super.clone(); // Utiliza la implementación de clonación de la superclase
            }
        };

        bus.agregarAsiento(new Asiento(1, "Económica", true));
        bus.agregarAsiento(new Asiento(2, "Económica", true));
    }

    @Test
    void testAgregarAsientoExitoso() {
        assertEquals(2, bus.getAsientos().size(), "Debería haber dos asientos en el bus.");
    }

    @Test
    void testAgregarAsientoExcedido() {
        Exception excepcion = assertThrows(IllegalStateException.class, () -> {
            bus.agregarAsiento(new Asiento(3, "Económica", true));
        }, "Debería lanzar una excepción al superar la capacidad del bus.");
        assertEquals("Capacidad del bus excedida", excepcion.getMessage());
    }

    @Test
    void testReservarAsiento() {
        bus.reservarAsiento(1);
        assertFalse(bus.getAsiento(1).isEstado(), "El asiento debería estar reservado.");
    }

    @Test
    void testReservarAsientoYaReservado() {
        bus.reservarAsiento(1); // Primera reserva
        Exception excepcion = assertThrows(IllegalStateException.class, () -> {
            bus.reservarAsiento(1); // Intento de segunda reserva
        }, "Debería lanzar una excepción al intentar reservar un asiento ya reservado.");
        assertEquals("El asiento 1 ya está reservado.", excepcion.getMessage());
    }

    @Test
    void testLiberarAsiento() {
        bus.reservarAsiento(1); // Reserva primero
        bus.liberarAsiento(1); // Luego libera
        assertTrue(bus.getAsiento(1).isEstado(), "El asiento debería estar libre.");
    }

    @Test
    void testLiberarAsientoYaLibre() {
        Exception excepcion = assertThrows(IllegalStateException.class, () -> {
            bus.liberarAsiento(1); // Intento de liberar un asiento ya libre
        }, "Debería lanzar una excepción al intentar liberar un asiento que ya está libre.");
        assertEquals("El asiento 1 ya está libre.", excepcion.getMessage());
    }

    @Test
    void testClonarBus() {
        Bus clonedBus = bus.clone();
        assertNotSame(bus, clonedBus, "El bus clonado no debe ser el mismo objeto que el original.");
        assertEquals(bus.getId(), clonedBus.getId(), "El ID del bus clonado debería ser el mismo que el original.");
        assertEquals(bus.getCapacidad(), clonedBus.getCapacidad(), "La capacidad del bus clonado debería ser la misma que la del original.");
    }
}
