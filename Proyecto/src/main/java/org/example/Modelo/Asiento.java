package org.example.Modelo;

public class Asiento {
    private int numero;
    private String categoria;
    private boolean estado;

    public Asiento(int numero, String categoria, boolean estado) {
        this.numero = numero;
        this.categoria = categoria;
        this.estado = estado;
    }

    public int getNumero() {
        return numero;
    }

    public String getCategoria() {
        return categoria;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}