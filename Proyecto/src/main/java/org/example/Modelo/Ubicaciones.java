package org.example.Modelo;

public enum Ubicaciones {
    LOS_ANGELES(5000),
    CURANILAHUE(1200),
    SANTIAGO(13000),
    TALCA(1500),
    CHILLAN(3000);

    private final int precio;

    Ubicaciones(int precio) {
        this.precio = precio;
    }

    public int getPrecio() {
        return this.precio;
    }
}
