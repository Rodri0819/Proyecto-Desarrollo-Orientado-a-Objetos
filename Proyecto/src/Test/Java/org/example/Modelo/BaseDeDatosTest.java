package org.example.Modelo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

class BaseDeDatosTest {
    private BaseDeDatos baseDeDatos;

    @BeforeEach
    void setUp() {
        baseDeDatos = BaseDeDatos.getInstance();
    }

    @Test
    void testSingleton() {
        BaseDeDatos otraInstancia = BaseDeDatos.getInstance();
        assertSame(baseDeDatos, otraInstancia, "Debería retornar la misma instancia de BaseDeDatos");
    }

    @Test
    void testVerificarCredenciales() {
        assertTrue(baseDeDatos.verificarCredenciales("admin", "1234"), "Las credenciales deberían ser correctas");
        assertFalse(baseDeDatos.verificarCredenciales("usuario", "contraseña"), "Las credenciales deberían ser incorrectas");
    }

    @Test
    void testObtenerRutas() {
        Date fecha = new Date();
        List<Ruta> rutas = baseDeDatos.obtenerRutas(Ubicaciones.LOS_ANGELES, Ubicaciones.SANTIAGO, fecha);
        assertFalse(rutas.isEmpty(), "Debería encontrar rutas para las ubicaciones y fecha dadas");
    }

    @Test
    void testAgregarRuta() {
        Ruta ruta = new Ruta(Ubicaciones.SANTIAGO, Ubicaciones.CONCEPCION, new Date(), LocalTime.now(), null, 10000);
        baseDeDatos.agregarRuta(ruta);
        List<Ruta> rutas = baseDeDatos.obtenerRutas(Ubicaciones.SANTIAGO, Ubicaciones.CONCEPCION, ruta.getFecha());
        assertTrue(rutas.contains(ruta), "La ruta agregada debería estar en la lista de rutas encontradas");
    }

    @Test
    void testGetBusPorId() {
        Bus bus = baseDeDatos.getBusPorId("MicroBus");
        assertNotNull(bus, "Debería encontrar un bus con el tipo especificado");
        assertNull(baseDeDatos.getBusPorId("TIPO_NO_EXISTE"), "No debería encontrar un bus con un tipo inexistente");
    }
}
