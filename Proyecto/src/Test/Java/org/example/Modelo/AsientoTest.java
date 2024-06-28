package org.example.Modelo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AsientoTest {
    private Asiento asiento;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        asiento = new Asiento(1, "Económica", true);
        cliente = new Cliente("Juan", "Pérez", "12345678-9", "juan.perez@example.com");
    }

    @Test
    void testCrearAsiento() {
        assertAll("Constructor Asiento",
                () -> assertEquals(1, asiento.getNumero(), "El número del asiento no coincide."),
                () -> assertEquals("Económica", asiento.getCategoria(), "La categoría del asiento no coincide."),
                () -> assertTrue(asiento.isEstado(), "El estado inicial del asiento debe ser verdadero."),
                () -> assertNull(asiento.getCliente(), "Inicialmente no debería haber un cliente asignado.")
        );
    }

    @Test
    void testAsignarCliente() {
        asiento.setCliente(cliente);
        assertAll("Asignar Cliente",
                () -> assertNotNull(asiento.getCliente(), "Debería haber un cliente asignado."),
                () -> assertEquals("Juan", asiento.getCliente().getNombre(), "El nombre del cliente no coincide."),
                () -> assertEquals("juan.perez@example.com", asiento.getCliente().getEmail(), "El email del cliente no coincide.")
        );
    }

    @Test
    void testCambiarEstadoAsiento() {
        asiento.setEstado(false);
        assertFalse(asiento.isEstado(), "El estado del asiento debería cambiar a falso.");
    }
}
