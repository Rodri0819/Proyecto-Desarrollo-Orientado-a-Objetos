package org.example.Modelo;

/**
 * La clase  representa un bus de un solo nivel con todos los asientos
 * en la categoría "Semi Cama".
 * Esta clase extiende la clase abstracta {@link Bus} e inicializa los asientos
 * según la capacidad especificada.
 */
public class SingleBus extends Bus {

    /**
     * Crea una instancia de {@code SingleBus} con la capacidad y el identificador especificados.
     * Todos los asientos del bus se inicializan como "Semi Cama".
     * @param capacidad la capacidad máxima del bus.
     * @param id el identificador del bus.
     */
    public SingleBus(int capacidad, String id) {
        super(capacidad, id);
        for (int i = 1; i <= capacidad; i++) {
            agregarAsiento(new Asiento(i, "Semi Cama", true)); // Inicializa con el estado adecuado
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
