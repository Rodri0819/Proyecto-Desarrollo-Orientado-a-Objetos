package org.example.Modelo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

class RutaTest {
    private Ruta ruta;
    private Bus bus;
    private Date fecha;
    private LocalTime hora;

    @BeforeEach
    void setUp() {
        // Preparar los objetos necesarios para la prueba
        Ubicaciones origen = Ubicaciones.SANTIAGO;
        Ubicaciones destino = Ubicaciones.CONCEPCION;
        fecha = new Date(); // Utiliza la fecha actual para la prueba
        hora = LocalTime.of(15, 30); // Hora específica para la prueba
        int precio = 10000;

        // Mock o stub del bus, dependiendo de la implementación
        bus = new Bus(40, "BUS-123") {
            @Override
            public Bus clone() {
                return super.clone();
            }
        };

        // Crear una ruta para la prueba
        ruta = new Ruta(origen, destino, fecha, hora, bus, precio);
    }

    @Test
    void testGetOrigen() {
        assertEquals(Ubicaciones.SANTIAGO, ruta.getOrigen(), "El origen debe ser Santiago.");
    }

    @Test
    void testGetDestino() {
        assertEquals(Ubicaciones.CONCEPCION, ruta.getDestino(), "El destino debe ser Concepción.");
    }

    @Test
    void testGetFecha() {
        assertEquals(fecha, ruta.getFecha(), "La fecha debe ser la misma que se inicializó.");
    }

    @Test
    void testGetHora() {
        assertEquals(hora, ruta.getHora(), "La hora debe ser la misma que se inicializó.");
    }

    @Test
    void testGetBus() {
        assertEquals(bus, ruta.getBus(), "El bus debe ser el mismo que se inicializó.");
    }

    @Test
    void testGetAsientos() {
        List<Asiento> asientos = ruta.getAsientos();
        assertNotNull(asientos, "Debería obtener una lista de asientos del bus.");
        // Verificar condiciones adicionales sobre los asientos si es necesario.
    }

    @Test
    void testGetPrecio() {
        assertEquals(10000, ruta.getPrecio(), "El precio debe ser de 10000.");
    }
}
