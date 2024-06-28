package org.example.Modelo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class RutaFactoryTest {
    private RutaFactory rutaFactory;
    private List<Bus> buses;

    @BeforeEach
    void setUp() {
        buses = new ArrayList<>();
        Bus bus = Mockito.mock(Bus.class);
        Mockito.when(bus.clone()).thenReturn(bus);
        Mockito.when(bus.getAsientos()).thenReturn(new ArrayList<Asiento>() {{
            add(new Asiento(1, "Semi Cama", true));
            add(new Asiento(2, "Salón Cama", true));
        }});
        buses.add(bus);
        rutaFactory = new RutaFactory(buses);
    }

    @Test
    void testCrearRutas() {
        List<Ruta> rutas = rutaFactory.crearRutas(Ubicaciones.SANTIAGO, Ubicaciones.CONCEPCION, 1);
        assertFalse(rutas.isEmpty(), "Debería crear al menos una ruta");
        assertEquals(Ubicaciones.SANTIAGO, rutas.get(0).getOrigen(), "El origen de la ruta debe ser Santiago");
        assertEquals(Ubicaciones.CONCEPCION, rutas.get(0).getDestino(), "El destino de la ruta debe ser Concepción");
    }

    @Test
    void testCalcularPrecioBase() {
        int precio = rutaFactory.calcularPrecioBase(Ubicaciones.SANTIAGO, Ubicaciones.CONCEPCION);
        assertEquals(11200, precio, "El precio base debe ser de 11200");
    }

    @Test
    void testCalcularPrecioAsiento() {
        int precioSemiCama = rutaFactory.calcularPrecioAsiento("Semi Cama");
        int precioSalonCama = rutaFactory.calcularPrecioAsiento("Salón Cama");

        assertAll("verificar precios de asientos",
                () -> assertEquals(5000, precioSemiCama, "El precio de Semi Cama debe ser 5000"),
                () -> assertEquals(15000, precioSalonCama, "El precio de Salón Cama debe ser 15000")
        );
    }

    @Test
    void testCalcularPrecioTotal() {
        Bus bus = buses.get(0);
        int precioTotal = rutaFactory.calcularPrecioTotal(Ubicaciones.SANTIAGO, Ubicaciones.CONCEPCION, bus);
        assertTrue(precioTotal > 0, "El precio total debe ser mayor que cero");
    }
}
