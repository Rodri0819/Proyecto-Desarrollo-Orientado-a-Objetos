package org.example.Modelo;

import java.util.ArrayList;
import java.util.List;

public abstract class Bus {
    private int capacidad;
    private String id;
    private List<Asiento> asientos;

    public Bus(int capacidad, String id) {
        this.capacidad = capacidad;
        this.id = id;
        this.asientos = new ArrayList<>();
    }

    protected void agregarAsiento(Asiento asiento) {
        if (asientos.size() < capacidad) {
            this.asientos.add(asiento);
        } else {
            throw new IllegalStateException("Capacidad del bus excedida");
        }
    }

    public int getCapacidad() {
        return capacidad;
    }

    public String getId() {
        return id;
    }

    public List<Asiento> getAsientos() {
        return asientos;
    }

    public Asiento getAsiento(int numero) {
        if (numero < 1 || numero > capacidad) {
            throw new IllegalArgumentException("Número de asiento inválido: " + numero);
        }
        return asientos.get(numero - 1);
    }

    public void reservarAsiento(int numero) {
        Asiento asiento = getAsiento(numero);
        if (asiento.isEstado()) {
            System.out.println("Reserva existosa");
        }
        else {
            throw new IllegalStateException("El asiento " + numero + " ya está reservado.");
        }
        asiento.setEstado(false);
    }

    public void liberarAsiento(int numero) {
        Asiento asiento = getAsiento(numero);
        if (!asiento.isEstado()) {
            throw new IllegalStateException("El asiento " + numero + " ya está libre.");
        }
        asiento.setEstado(false);
    }
}
