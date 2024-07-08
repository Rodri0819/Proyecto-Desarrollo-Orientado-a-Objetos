package org.example.Modelo;

import java.util.ArrayList;
import java.util.List;

public abstract class Bus implements Cloneable {
    private int capacidad;
    private String tipo;
    private List<Asiento> asientos;

    public Bus(int capacidad, String tipo) {
        this.capacidad = capacidad;
        this.tipo = tipo;
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
        return tipo;
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
        if (!asiento.isEstado()) {
            throw new IllegalStateException("El asiento " + numero + " ya está reservado.");
        }
        asiento.setEstado(false);
        System.out.println("Reserva exitosa");
    }

    public void liberarAsiento(int numero) {
        Asiento asiento = getAsiento(numero);
        if (asiento.isEstado()) {
            throw new IllegalStateException("El asiento " + numero + " ya está libre.");
        }
        asiento.setEstado(true);
    }

    @Override
    public Bus clone() {
        try {
            Bus clonedBus = (Bus) super.clone();
            clonedBus.asientos = new ArrayList<>();
            for (Asiento asiento : this.asientos) {
                clonedBus.asientos.add(new Asiento(asiento.getNumero(), asiento.getCategoria(), asiento.isEstado()));
            }
            return clonedBus;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Error, no se pudo clonar", e);
        }
    }
}
