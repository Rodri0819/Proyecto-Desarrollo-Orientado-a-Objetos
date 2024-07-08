package org.example.Modelo;

/**
 * La clase representa un asiento en un bus con un número,
 * una categoría (como Semi Cama o Salón Cama). un estado de disponibilidad
 * y un cliente que lo ha reservado (si aplica).
 * Esta clase gestiona la información de un asiento, incluida su disponibilidad
 * y el cliente que lo ha reservado.
 */
public class Asiento {
    private int numero;
    private String categoria;
    private boolean estado;
    private Cliente cliente;

    /**
     * Crea una instancia de {@code Asiento} con el número, categoría y estado especificados.
     * Inicialmente, el asiento no tiene ningún cliente asignado.
     *
     * @param numero    el número del asiento.
     * @param categoria la categoría del asiento (por ejemplo, Semi Cama o Salón Cama).
     * @param estado    el estado de disponibilidad del asiento (true si está disponible, false si está reservado).
     */
    public Asiento(int numero, String categoria, boolean estado) {
        this.numero = numero;
        this.categoria = categoria;
        this.estado = estado;
        this.cliente = null; // Inicialmente el asiento no está reservado
    }

    /**
     * Obtiene el número del asiento.
     * @return el número del asiento.
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Obtiene la categoría del asiento.
     * @return la categoría del asiento.
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Indica si el asiento está disponible.
     * @return true si el asiento está disponible, false si está reservado.
     */
    public boolean isEstado() {
        return estado;
    }

    /**
     * Establece el estado de disponibilidad del asiento.
     * @param estado el nuevo estado de disponibilidad del asiento (true para disponible, false para reservado).
     */
    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el cliente que ha reservado el asiento.
     * @return el cliente que ha reservado el asiento, o null si el asiento no está reservado.
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Asigna un cliente al asiento, indicando que el asiento ha sido reservado.
     * @param cliente el cliente que ha reservado el asiento.
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
