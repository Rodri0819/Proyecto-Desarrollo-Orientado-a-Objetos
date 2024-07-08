package org.example.Modelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RutaFactory {
    private List<Bus> buses;

    public RutaFactory(List<Bus> buses) {
        this.buses = buses;
    }

    public List<Ruta> crearRutas(Ubicaciones origen, Ubicaciones destino, int dias) {
        if (origen == null || destino == null) {
            throw new IllegalArgumentException("Origen y destino no pueden ser nulos");
        }
        if (dias <= 0) {
            throw new IllegalArgumentException("El número de días debe ser positivo y mayor que cero");
        }
        if (buses.isEmpty()) {
            throw new IllegalStateException("No hay buses disponibles para crear rutas");
        }
        List<Ruta> rutas = new ArrayList<>();
        LocalTime start = LocalTime.of(8, 0); // Hora de inicio general
        LocalTime end = LocalTime.of(22, 0); // Hora de fin
        int busIndex = 0;

        for (int i = 0; i < dias; i++) {
            LocalDate localDate = LocalDate.now().plusDays(i);
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            LocalTime currentStart;
            if (i == 0) {
                currentStart = LocalTime.now().truncatedTo(ChronoUnit.HOURS);
            } else {
                currentStart = start;
            }

            if (currentStart.isBefore(start)) {
                currentStart = start; // No iniciar antes de las 8 AM
            }

            LocalTime current = currentStart;
            while (!current.isAfter(end)) {
                if (!current.isAfter(end)) {
                    Bus busOriginal = buses.get(busIndex);
                    Bus busClonado = busOriginal.clone();
                    int precio = calcularPrecioTotal(origen, destino, busClonado);
                    rutas.add(new Ruta(origen, destino, date, current, busClonado, precio));

                    // Alternar el índice de bus
                    busIndex = (busIndex + 1) % buses.size();
                }
                // Incrementar la hora en una hora
                current = current.plusHours(1);
            }

        }

        return rutas;
    }
    public static int calcularPrecioBase(Ubicaciones origen, Ubicaciones destino) {
        // Definir precios entre ubicaciones
        if (origen == Ubicaciones.LOS_ANGELES && destino == Ubicaciones.SANTIAGO) {
            return 13500;
        } else if (origen == Ubicaciones.LOS_ANGELES && destino == Ubicaciones.CONCEPCION) {
            return 4000;
        } else if (origen == Ubicaciones.LOS_ANGELES && destino == Ubicaciones.CURANILAHUE) {
            return 5000;
        } else if (origen == Ubicaciones.CONCEPCION && destino == Ubicaciones.SANTIAGO) {
            return 11000;
        } else if (origen == Ubicaciones.CONCEPCION && destino == Ubicaciones.CURANILAHUE) {
            return 2500;
        } else if (origen == Ubicaciones.CONCEPCION && destino == Ubicaciones.LOS_ANGELES) {
            return 4000;
        } else if (origen == Ubicaciones.SANTIAGO && destino == Ubicaciones.CONCEPCION) {
            return 11000;
        } else if (origen == Ubicaciones.SANTIAGO && destino == Ubicaciones.CURANILAHUE) {
            return 16000;
        } else if (origen == Ubicaciones.SANTIAGO && destino == Ubicaciones.LOS_ANGELES) {
            return 13500;
        } else if (origen == Ubicaciones.CURANILAHUE && destino == Ubicaciones.SANTIAGO) {
            return 16000;
        } else if (origen == Ubicaciones.CURANILAHUE && destino == Ubicaciones.CONCEPCION) {
            return 2500;
        } else if (origen == Ubicaciones.CURANILAHUE && destino == Ubicaciones.LOS_ANGELES) {
            return 5000;
        } else {
            return 0;
        }
    }

    public int calcularPrecioAsiento(String categoria) {
        if (categoria.equals("Semi Cama")) {
            return 2000;
        } else if (categoria.equals("Salón Cama")) {
            return 4000;
        } else {
            return 0;
        }
    }

    public int calcularPrecioTotal(Ubicaciones origen, Ubicaciones destino, Bus bus) {
        int precioBase = calcularPrecioBase(origen, destino);
        int precioTotal = precioBase;
        for (Asiento asiento : bus.getAsientos()) {
            precioTotal += calcularPrecioAsiento(asiento.getCategoria()) / 120000;
        }
        return precioTotal;
    }
}
