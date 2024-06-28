package org.example.Modelo;

public class Asiento {
    private int numero;
    private String categoria;
    private boolean estado;
    private Cliente cliente; // Referencia a un cliente que tiene reservado el asiento

    public Asiento(int numero, String categoria, boolean estado) {
        this.numero = numero;
        this.categoria = categoria;
        this.estado = estado;
        this.cliente = null; // Inicialmente el asiento no está reservado
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
