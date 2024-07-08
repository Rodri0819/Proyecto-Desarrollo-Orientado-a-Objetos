package org.example.Modelo;

public class DoubleBus extends Bus {
    public DoubleBus(int capacidad, String id) {
        super(capacidad, id);

        int mitadCapacidad = ((capacidad-1) / 2) + 4 - ((capacidad-1) / 2) % 4;

        for (int i = 1; i <= mitadCapacidad; i++) {
            agregarAsiento(new Asiento(i, "Salón Cama", true));
        }
        for (int i = mitadCapacidad + 1; i <= capacidad; i++) {
            agregarAsiento(new Asiento(i, "Semi Cama", true));
        }
    }

    @Override
    public String getId() {
        return super.getId();
    }
}
