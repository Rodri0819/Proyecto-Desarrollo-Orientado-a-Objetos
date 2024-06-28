package org.example.Modelo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClienteTest {
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("Juan", "Pérez", "12345678-9", "juan.perez@example.com");
    }

    @Test
    void testConstructor() {
        assertAll("Constructor",
                () -> assertEquals("Juan", cliente.getNombre(), "El nombre no fue inicializado correctamente."),
                () -> assertEquals("Pérez", cliente.getApellido(), "El apellido no fue inicializado correctamente."),
                () -> assertEquals("12345678-9", cliente.getRut(), "El RUT no fue inicializado correctamente."),
                () -> assertEquals("juan.perez@example.com", cliente.getEmail(), "El email no fue inicializado correctamente.")
        );
    }

    @Test
    void testSetNombre() {
        cliente.setNombre("Carlos");
        assertEquals("Carlos", cliente.getNombre(), "El método setNombre no funciona como se espera.");
    }

    @Test
    void testSetApellido() {
        cliente.setApellido("Rodríguez");
        assertEquals("Rodríguez", cliente.getApellido(), "El método setApellido no funciona como se espera.");
    }

    @Test
    void testSetRut() {
        cliente.setRut("98765432-1");
        assertEquals("98765432-1", cliente.getRut(), "El método setRut no funciona como se espera.");
    }

    @Test
    void testSetEmail() {
        cliente.setEmail("carlos.rodriguez@example.com");
        assertEquals("carlos.rodriguez@example.com", cliente.getEmail(), "El método setEmail no funciona como se espera.");
    }
}
