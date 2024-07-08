package org.example.Modelo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

class PasajeTest {
    private Ruta ruta;
    private Cliente cliente;
    private Asiento asientoSeleccionado;
    private Pasaje pasaje;
    private String nombreArchivo = "test_pasaje_report.txt";

    @BeforeEach
    void setUp() {
        Bus bus = new Bus(10, "BUS-100") {
            @Override
            public Bus clone() {
                return super.clone();
            }
        };
        ruta = new Ruta(Ubicaciones.SANTIAGO, Ubicaciones.CONCEPCION, new Date(), java.time.LocalTime.now(), bus, 10000);
        cliente = new Cliente("Juan", "Pérez", "12345678-9", "juan.perez@example.com");
        asientoSeleccionado = new Asiento(1, "Económica", true);

        pasaje = new Pasaje(ruta, cliente, asientoSeleccionado, nombreArchivo);
    }

    @Test
    void testGenerarInforme() throws IOException {
        pasaje.generarInforme();
        assertTrue(Files.exists(Path.of(nombreArchivo)), "El archivo de informe debe existir.");

        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String content = reader.lines().reduce("", String::concat);
            assertTrue(content.contains("Cliente: Juan Pérez"), "El informe debe contener el nombre del cliente.");
            assertTrue(content.contains("Precio Total:"), "El informe debe contener la línea de precio total.");
        }
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(nombreArchivo));
    }
}
