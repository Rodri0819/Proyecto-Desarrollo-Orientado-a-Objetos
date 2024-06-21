package org.example.Modelo;

import java.util.Date;
import java.util.List;


public class Ruta {
    private Ubicaciones origen;
    private Ubicaciones destino;
    private Date fecha;
    private String hora;
    private Bus bus;

    public Ruta(Ubicaciones origen, Ubicaciones destino, Date fecha, String hora, Bus bus) {
        this.origen = origen;
        this.destino = destino;
        this.fecha = fecha;
        this.hora = hora;
        this.bus = bus;
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

    public String getHora() {
        return hora;
    }

    public Bus getBus() {
        return bus;
    }

    public List<Asiento> getAsientos() {
        return bus.getAsientos();
    }
}