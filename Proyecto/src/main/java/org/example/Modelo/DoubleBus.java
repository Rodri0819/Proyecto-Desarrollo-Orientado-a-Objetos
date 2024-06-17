package org.example.Modelo;

public class DoubleBus extends Bus {
    public DoubleBus(int capacidad, String id) {
        super(capacidad, id);
        int mitadCapacidad = capacidad / 2;
        for (int i = 1; i <= mitadCapacidad; i++) {
            agregarAsiento(new Asiento(i, "Salón Cama", false));
        }
        for (int i = mitadCapacidad + 1; i <= capacidad; i++) {
            agregarAsiento(new Asiento(i, "Semi Cama", false));
        }
    }
}
