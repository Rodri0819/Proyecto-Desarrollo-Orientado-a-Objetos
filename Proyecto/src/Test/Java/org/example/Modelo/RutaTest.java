package org.example.Modelo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import java.util.Date;

class RutaTest {
    private Ruta ruta;
    private Bus bus;
    private Date fecha;
    private LocalTime hora;

    @BeforeEach
    void setUp() {
        Ubicaciones origen = Ubicaciones.SANTIAGO;
        Ubicaciones destino = Ubicaciones.CONCEPCION;
        fecha = new Date();
        hora = LocalTime.of(15, 30);
        int precio = 10000;

        bus = new Bus(40, "BUS-123") {
            @Override
            public Bus clone() {
                return super.clone();
            }
        };

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
    void testGetPrecio() {
        assertEquals(10000, ruta.getPrecio(), "El precio debe ser de 10000.");
    }
}
