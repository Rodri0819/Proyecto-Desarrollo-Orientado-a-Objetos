package org.example.Modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase abstracta Bus representa un bus con una capacidad específica,
 * un tipo, y una lista de asientos. Esta clase implementa el patrón de diseño Prototype,
 * permitiendo la clonación de objetos Bus.
 * Las subclases deben implementar su propia lógica específica para los diferentes tipos de buses.
 *
 */
public abstract class Bus implements Cloneable {
    private int capacidad;
    private String tipo;
    private List<Asiento> asientos;

    /**
     * Constructor para crear una instancia de {@code Bus}.
     * @param capacidad la capacidad máxima del bus.
     * @param tipo el tipo de bus (por ejemplo, MicroBus, Articulado).
     */
    public Bus(int capacidad, String tipo) {
        this.capacidad = capacidad;
        this.tipo = tipo;
        this.asientos = new ArrayList<>();
    }

    /**
     * Agrega un asiento al bus si no se ha excedido la capacidad.
     * @param asiento el asiento a agregar.
     * @throws IllegalStateException si se excede la capacidad del bus.
     */
    protected void agregarAsiento(Asiento asiento) {
        if (asientos.size() < capacidad) {
            this.asientos.add(asiento);
        } else {
            throw new IllegalStateException("Capacidad del bus excedida");
        }
    }

    /**
     * Obtiene la capacidad del bus.
     * @return la capacidad del bus.
     */
    public int getCapacidad() {
        return capacidad;
    }

    /**
     * Obtiene el identificador del bus, que en este caso es el tipo de bus.
     * @return el tipo de bus.
     */
    public String getId() {
        return tipo;
    }

    /**
     * Obtiene la lista de asientos del bus.
     * @return una lista de asientos.
     */
    public List<Asiento> getAsientos() {
        return asientos;
    }

    /**
     * Obtiene un asiento específico por su número.
     * @param numero el número del asiento.
     * @return el asiento correspondiente al número especificado.
     * @throws IllegalArgumentException si el número de asiento es inválido.
     */
    public Asiento getAsiento(int numero) {
        if (numero < 1 || numero > capacidad) {
            throw new IllegalArgumentException("Número de asiento inválido: " + numero);
        }
        return asientos.get(numero - 1);
    }

    /**
     * Reserva un asiento específico por su número.
     * @param numero el número del asiento a reservar.
     * @throws IllegalStateException si el asiento ya está reservado.
     */
    public void reservarAsiento(int numero) {
        Asiento asiento = getAsiento(numero);
        if (!asiento.isEstado()) {
            throw new IllegalStateException("El asiento " + numero + " ya está reservado.");
        }
        asiento.setEstado(false);
        System.out.println("Reserva exitosa");
    }

    /**
     * Libera un asiento específico por su número.
     * @param numero el número del asiento a liberar.
     * @throws IllegalStateException si el asiento ya está libre.
     */
    public void liberarAsiento(int numero) {
        Asiento asiento = getAsiento(numero);
        if (asiento.isEstado()) {
            throw new IllegalStateException("El asiento " + numero + " ya está libre.");
        }
        asiento.setEstado(true);
    }

    /**
     * Crea y devuelve una copia del bus.
     * Este método realiza una clonación profunda de los asientos del bus.
     *
     * @return una copia clonada del bus.
     * @throws RuntimeException si ocurre un error durante la clonación.
     */
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
