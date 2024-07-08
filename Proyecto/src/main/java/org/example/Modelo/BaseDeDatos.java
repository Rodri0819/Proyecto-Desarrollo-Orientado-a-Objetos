package org.example.Modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * La clase simula una base de datos.
 * Esta clase sigue el patrón Singleton para asegurar que solo exista una instancia de la base de datos.
 */
public class BaseDeDatos {
    private static BaseDeDatos instancia;
    private List<Ruta> rutas;
    private List<Bus> buses;
    private RutaFactory rutaFactory;
    private String usuario;
    private String contraseña;

    /**
     * Constructor privado para implementar el patrón Singleton.
     * Inicializa las credenciales de usuario, las listas de rutas y buses, y carga los datos iniciales.
     */
    private BaseDeDatos() {
        usuario = "admin";
        contraseña = "1234";
        rutas = new ArrayList<>();
        buses = new ArrayList<>();
        cargarDatos();
        rutaFactory = new RutaFactory(buses);
        generarRutas();
    }

    /**
     * Devuelve la instancia única de la base de datos.
     * @return la instancia de {@code BaseDeDatos}.
     */
    public static BaseDeDatos getInstance() {
        if (instancia == null) {
            instancia = new BaseDeDatos();
        }
        return instancia;
    }

    /**
     * Carga los datos iniciales de buses en la base de datos.
     * Lanza una excepción en tiempo de ejecución si ocurre un error al cargar los datos.
     */
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

    /**
     * Genera rutas iniciales utilizando la {@code RutaFactory} y las agrega a la lista de rutas.
     */
    private void generarRutas() {
        rutas.addAll(rutaFactory.crearRutas(Ubicaciones.LOS_ANGELES, Ubicaciones.SANTIAGO, 7));
        rutas.addAll(rutaFactory.crearRutas(Ubicaciones.LOS_ANGELES, Ubicaciones.CONCEPCION, 7));
        rutas.addAll(rutaFactory.crearRutas(Ubicaciones.LOS_ANGELES, Ubicaciones.CURANILAHUE, 7));
    }

    /**
     * Obtiene una lista de rutas que coinciden con el origen, destino y fecha especificados.
     *
     * @param origen el origen de la ruta.
     * @param destino el destino de la ruta.
     * @param fecha la fecha de la ruta.
     * @return una lista de rutas que coinciden con los criterios de búsqueda.
     */
    public List<Ruta> obtenerRutas(Ubicaciones origen, Ubicaciones destino, Date fecha) {
        List<Ruta> rutasEncontradas = new ArrayList<>();
        for (Ruta ruta : rutas) {
            if (ruta.getOrigen() == origen && ruta.getDestino() == destino && esMismaFecha(ruta.getFecha(), fecha)) {
                rutasEncontradas.add(ruta);
            }
        }
        return rutasEncontradas;
    }

    /**
     * Comprueba si dos fechas corresponden al mismo día.
     *
     * @param fecha1 la primera fecha.
     * @param fecha2 la segunda fecha.
     * @return {@code true} si las fechas son del mismo día, {@code false} en caso contrario.
     */
    private boolean esMismaFecha(Date fecha1, Date fecha2) {
        return fecha1.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate().equals(fecha2.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
    }

    /**
     * Obtiene la lista de buses disponibles.
     *
     * @return una lista de buses.
     */
    public List<Bus> getBuses() {
        return buses;
    }

    /**
     * Obtiene un bus por su identificador.
     *
     * @param id el identificador del bus.
     * @return el bus con el identificador especificado, o {@code null} si no se encuentra.
     */
    public Bus getBusPorId(String id) {
        for (Bus bus : buses) {
            if (bus.getId().equals(id)) {
                return bus;
            }
        }
        return null;
    }

    /**
     * Agrega una ruta a la lista de rutas.
     * Lanza una excepción si la ruta es nula o si ya existe en la lista.
     *
     * @param ruta la ruta a agregar.
     * @throws IllegalArgumentException si la ruta es nula.
     * @throws IllegalStateException si la ruta ya existe en la lista.
     */
    public void agregarRuta(Ruta ruta) {
        if (ruta == null) {
            throw new IllegalArgumentException("La ruta no puede ser nula");
        }
        if (rutas.contains(ruta)) {
            throw new IllegalStateException("La ruta ya existe");
        }
        rutas.add(ruta);
    }

    /**
     * Verifica las credenciales del usuario.
     *
     * @param usuario el nombre de usuario.
     * @param contraseña la contraseña.
     * @return {@code true} si las credenciales son correctas, {@code false} en caso contrario.
     */
    public boolean verificarCredenciales(String usuario, String contraseña) {
        return this.usuario.equals(usuario) && this.contraseña.equals(contraseña);
    }
}
