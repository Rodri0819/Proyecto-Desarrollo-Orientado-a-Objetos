package org.example.Modelo;

import java.util.Date;
import java.util.List;

public class Ruta {
    private Ubicaciones origen;
    private Ubicaciones destino;
    private Date fecha;
    private List<String> horas;
    private String id;
    private Bus bus;

    public Ruta(Ubicaciones origen, Ubicaciones destino, Date fecha, List<String> horas, Bus bus) {
        this.origen = origen;
        this.destino = destino;
        this.fecha = fecha;
        this.horas = horas;
        this.bus = bus;
    }

    public Ubicaciones getOrigen() {
        return origen;
    }

    public void setOrigen(Ubicaciones origen) {
        this.origen = origen;
    }

    public Ubicaciones getDestino() {
        return destino;
    }

    public void setDestino(Ubicaciones destino) {
        this.destino = destino;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<String> getHoras() {
        return horas;
    }

    public void setHoras(List<String> horas) {
        this.horas = horas;
    }

    public int getPrecioDestino() {
        return destino.getPrecio();
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }
}
