package org.example.Modelo;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class RutaFactory {
    private List<Bus> buses;

    public RutaFactory(List<Bus> buses) {
        this.buses = buses;
    }

    public List<Ruta> crearRutas(Ubicaciones origen, Ubicaciones destino, int dias) {
        List<Ruta> rutas = new ArrayList<>();
        String[] horarios = {"08:00", "12:00", "17:00", "20:00"};
        int busIndex = 0;

        for (int i = 0; i < dias; i++) {
            LocalDate localDate = LocalDate.now().plusDays(i);
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            for (String horario : horarios) {
                Bus busOriginal = buses.get(busIndex); // Alterna entre los buses disponibles
                Bus busClonado = busOriginal.clone(); // Clona el bus para esta ruta
                int precio = calcularPrecioTotal(origen, destino, busClonado);
                rutas.add(new Ruta(origen, destino, date, horario, busClonado, precio));

                // Alternar el índice de bus
                busIndex = (busIndex + 1) % buses.size();
            }
        }

        return rutas;
    }

    public int calcularPrecioBase(Ubicaciones origen, Ubicaciones destino) {
        // Definir precios entre ubicaciones
        if (origen == Ubicaciones.LOS_ANGELES && destino == Ubicaciones.SANTIAGO) {
            return 13000;
        } else if (origen == Ubicaciones.LOS_ANGELES && destino == Ubicaciones.CONCEPCION) {
            return 10000;
        } else if (origen == Ubicaciones.LOS_ANGELES && destino == Ubicaciones.CURANILAHUE) {
            return 12300;
        } else if (origen == Ubicaciones.CONCEPCION && destino == Ubicaciones.SANTIAGO) {
            return 15200;
        } else if (origen == Ubicaciones.CONCEPCION && destino == Ubicaciones.CURANILAHUE) {
            return 15200;
        } else if (origen == Ubicaciones.CONCEPCION && destino == Ubicaciones.LOS_ANGELES) {
            return 14200;
        } else if (origen == Ubicaciones.SANTIAGO && destino == Ubicaciones.CONCEPCION) {
            return 11200;
        } else if (origen == Ubicaciones.SANTIAGO && destino == Ubicaciones.CURANILAHUE) {
            return 11200;
        } else if (origen == Ubicaciones.SANTIAGO && destino == Ubicaciones.LOS_ANGELES) {
            return 11200;
        } else if (origen == Ubicaciones.CURANILAHUE && destino == Ubicaciones.SANTIAGO) {
            return 12200;
        } else if (origen == Ubicaciones.CURANILAHUE && destino == Ubicaciones.CONCEPCION) {
            return 13200;
        } else if (origen == Ubicaciones.CURANILAHUE && destino == Ubicaciones.LOS_ANGELES) {
            return 13200;
        } else {
            return -1;
        }
    }

    public int calcularPrecioAsiento(String categoria) {
        if (categoria.equals("Semi Cama")) {
            return 5000;
        } else if (categoria.equals("Salón Cama")) {
            return 15000;
        } else {
            return 0;
        }
    }

    public int calcularPrecioTotal(Ubicaciones origen, Ubicaciones destino, Bus bus) {
        int precioBase = calcularPrecioBase(origen, destino);
        int precioTotal = precioBase;
        for (Asiento asiento : bus.getAsientos()) {
            precioTotal += calcularPrecioAsiento(asiento.getCategoria());
        }
        return precioTotal;
    }
}
