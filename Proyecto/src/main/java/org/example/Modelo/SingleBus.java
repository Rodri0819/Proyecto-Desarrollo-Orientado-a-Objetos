package org.example.Modelo;

public class SingleBus extends Bus {
    public SingleBus(int capacidad, String id) {
        super(capacidad, id);
        for (int i = 1; i <= capacidad; i++) {
            agregarAsiento(new Asiento(i, "Semi Cama", false));
        }
    }
}
