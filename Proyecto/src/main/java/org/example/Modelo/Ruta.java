package org.example.Modelo;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class  Ruta {
    private Ubicaciones origen;
    private Ubicaciones destino;
    private Date fecha;
    private LocalTime hora;
    private Bus bus;
    private int precio;

    public Ruta(Ubicaciones origen, Ubicaciones destino, Date fecha, LocalTime hora, Bus bus, int precio) {
        this.origen = origen;
        this.destino = destino;
        this.fecha = fecha;
        this.hora = hora;
        this.bus = bus;
        this.precio = precio;
    }

    public Ubicaciones getOrigen() {
        return origen;
    }

    public Ubicaciones getDestino() {
        return destino;
    }

    public Date getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public Bus getBus() {
        return bus;
    }

    public int getPrecio() {
        return precio;
    }

    public String getHoraFormateada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return this.hora.format(formatter);
    }
}
