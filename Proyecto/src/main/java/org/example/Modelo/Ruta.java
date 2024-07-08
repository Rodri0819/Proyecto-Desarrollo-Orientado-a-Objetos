package org.example.Modelo;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * La clase representa una ruta de viaje con información sobre el origen,
 * destino, fecha, hora, bus y precio del viaje.
 * Esta clase se utiliza para gestionar y proporcionar detalles sobre las rutas de los buses.
 */
public class Ruta {
    private Ubicaciones origen;
    private Ubicaciones destino;
    private Date fecha;
    private LocalTime hora;
    private Bus bus;
    private int precio;

    /**
     * Crea una instancia de {@code Ruta} con el origen, destino, fecha, hora, bus y precio especificados.
     * @param origen la ubicación de origen de la ruta.
     * @param destino la ubicación de destino de la ruta.
     * @param fecha la fecha de la ruta.
     * @param hora la hora de la ruta.
     * @param bus el bus asignado a la ruta.
     * @param precio el precio del viaje.
     */
    public Ruta(Ubicaciones origen, Ubicaciones destino, Date fecha, LocalTime hora, Bus bus, int precio) {
        this.origen = origen;
        this.destino = destino;
        this.fecha = fecha;
        this.hora = hora;
        this.bus = bus;
        this.precio = precio;
    }

    /**
     * Obtiene la ubicación de origen de la ruta.
     * @return la ubicación de origen.
     */
    public Ubicaciones getOrigen() {
        return origen;
    }

    /**
     * Obtiene la ubicación de destino de la ruta.
     * @return la ubicación de destino.
     */
    public Ubicaciones getDestino() {
        return destino;
    }

    /**
     * Obtiene la fecha de la ruta.
     * @return la fecha de la ruta.
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Obtiene la hora de la ruta.
     * @return la hora de la ruta.
     */
    public LocalTime getHora() {
        return hora;
    }

    /**
     * Obtiene el bus asignado a la ruta.
     * @return el bus asignado.
     */
    public Bus getBus() {
        return bus;
    }

    /**
     * Obtiene el precio del viaje.
     * @return el precio del viaje.
     */
    public int getPrecio() {
        return precio;
    }

    /**
     * Obtiene la hora de la ruta en formato de cadena.
     * @return la hora de la ruta formateada como "HH:mm".
     */
    public String getHoraFormateada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return this.hora.format(formatter);
    }
}
