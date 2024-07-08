package org.example.Modelo;

import java.time.LocalTime;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BaseDeDatos {
    private static BaseDeDatos instancia;
    private List<Ruta> rutas;
    private List<Bus> buses;
    private RutaFactory rutaFactory;
    private String usuario;
    private String contraseña;

    private BaseDeDatos() {
        usuario = "admin";
        contraseña = "1234";
        rutas = new ArrayList<>();
        buses = new ArrayList<>();
        cargarDatos();
        rutaFactory = new RutaFactory(buses);
        generarRutas();
    }

    public static BaseDeDatos getInstance() {
        if (instancia == null) {
            instancia = new BaseDeDatos();
        }
        return instancia;
    }

    private void cargarDatos() {
            try {
                // Intentar cargar buses
                Bus bus1 = new SingleBus(24, "MicroBus");
                Bus bus2 = new DoubleBus(30, "Bus");
                Bus bus3 = new SingleBus(40, "Articulado");

                buses.add(bus1);
                buses.add(bus2);
                buses.add(bus3);
            } catch (Exception e) {
                throw new RuntimeException("Error al cargar datos de buses", e);
            }
        }


        private void generarRutas() {
        rutas.addAll(rutaFactory.crearRutas(Ubicaciones.LOS_ANGELES, Ubicaciones.SANTIAGO, 7));
        rutas.addAll(rutaFactory.crearRutas(Ubicaciones.LOS_ANGELES, Ubicaciones.CONCEPCION, 7));
        rutas.addAll(rutaFactory.crearRutas(Ubicaciones.LOS_ANGELES, Ubicaciones.CURANILAHUE, 7));
    }

    public List<Ruta> obtenerRutas(Ubicaciones origen, Ubicaciones destino, Date fecha) {
        List<Ruta> rutasEncontradas = new ArrayList<>();
        for (Ruta ruta : rutas) {
            if (ruta.getOrigen() == origen && ruta.getDestino() == destino && esMismaFecha(ruta.getFecha(), fecha)) {
                rutasEncontradas.add(ruta);
            }
        }
        return rutasEncontradas;
    }

    private boolean esMismaFecha(Date fecha1, Date fecha2) {
        return fecha1.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate().equals(fecha2.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
    }

    public List<Bus> getBuses() {
        return buses;
    }

    public Bus getBusPorId(String id) {
        for (Bus bus : buses) {
            if (bus.getId().equals(id)) {
                return bus;
            }
        }
        return null;
    }

    public void agregarRuta(Ruta ruta) {
        if (ruta == null) {
            throw new IllegalArgumentException("La ruta no puede ser nula");
        }
        if (rutas.contains(ruta)) {
            throw new IllegalStateException("La ruta ya existe");
        }
        rutas.add(ruta);
    }

    public boolean verificarCredenciales(String usuario, String contraseña) {
        return this.usuario.equals(usuario) && this.contraseña.equals(contraseña);
    }
}
