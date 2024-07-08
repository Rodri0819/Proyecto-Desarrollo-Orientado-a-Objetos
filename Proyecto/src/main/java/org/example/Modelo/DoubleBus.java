package org.example.Modelo;

/**
 * La clase representa un bus grande con asientos
 * divididos en categorías de "Salón Cama" y "Semi Cama".
 * Esta clase extiende la clase abstracta {@link Bus} e inicializa los asientos con
 * capacidades específicas para cada categoría.
 */
public class DoubleBus extends Bus {

    /**
     * Crea una instancia de {@code DoubleBus} con la capacidad y el identificador especificados.
     * La capacidad se divide entre asientos de "Salón Cama" y "Semi Cama". La primera mitad
     * de los asientos se asigna como "Salón Cama" y la segunda mitad como "Semi Cama".
     * @param capacidad la capacidad máxima del bus.
     * @param id el identificador del bus.
     */
    public DoubleBus(int capacidad, String id) {
        super(capacidad, id);

        int mitadCapacidad = ((capacidad - 1) / 2) + 4 - ((capacidad - 1) / 2) % 4;

        for (int i = 1; i <= mitadCapacidad; i++) {
            agregarAsiento(new Asiento(i, "Salón Cama", true));
        }
        for (int i = mitadCapacidad + 1; i <= capacidad; i++) {
            agregarAsiento(new Asiento(i, "Semi Cama", true));
        }
    }

    /**
     * Obtiene el identificador del bus.
     * @return el identificador del bus.
     */
    @Override
    public String getId() {
        return super.getId();
    }
}
