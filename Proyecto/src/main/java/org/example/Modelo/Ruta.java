package org.example.Modelo;

import java.util.List;

public class Ruta {
    private String origen;
    private Ubicaciones destino;
    private String horario;
    private List<Bus> buses;

    public Ruta(String origen, Ubicaciones destino, String horario, List<Bus> buses) {
        this.origen = origen;
        this.destino = destino;
        this.horario = horario;
        this.buses = buses;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public Ubicaciones getDestino() {
        return destino;
    }

    public void setDestino(Ubicaciones destino) {
        this.destino = destino;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public List<Bus> getBuses() {
        return buses;
    }

    public void setBuses(List<Bus> buses) {
        this.buses = buses;
    }

    public int getPrecioDestino() {
        return destino.getPrecio();
    }
}
