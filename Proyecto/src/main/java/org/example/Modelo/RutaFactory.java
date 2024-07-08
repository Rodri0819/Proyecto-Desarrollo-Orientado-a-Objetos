package org.example.Modelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * La clase se encarga de crear rutas de viaje basadas en los
 * parámetros de origen, destino y número de días.
 * Utiliza una lista de buses para generar las rutas, asignando horarios y calculando
 * los precios de los viajes.
 */
public class RutaFactory {
    private List<Bus> buses;

    /**
     * Crea una instancia de {@code RutaFactory} con la lista de buses especificada.
     * @param buses la lista de buses disponible para crear rutas.
     */
    public RutaFactory(List<Bus> buses) {
        this.buses = buses;
    }

    /**
     * Crea una lista de rutas entre dos ubicaciones durante un número específico de días.
     * Genera horarios para las rutas desde las 8:00 AM hasta las 10:00 PM, asegurando
     * que no se exceda la capacidad de los buses disponibles.
     *
     * @param origen la ubicación de origen de las rutas.
     * @param destino la ubicación de destino de las rutas.
     * @param dias el número de días para generar rutas.
     * @return una lista de rutas generadas.
     * @throws IllegalArgumentException si el origen o destino son nulos, o si el número de días es menor o igual a cero.
     * @throws IllegalStateException si no hay buses disponibles para crear rutas.
     */
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

    /**
     * Calcula el precio base de un viaje entre dos ubicaciones.
     * @param origen la ubicación de origen.
     * @param destino la ubicación de destino.
     * @return el precio base del viaje.
     */
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

    /**
     * Calcula el precio adicional de un asiento según su categoría.
     * @param categoria la categoría del asiento (por ejemplo, "Semi Cama" o "Salón Cama").
     * @return el precio adicional del asiento.
     */
    public int calcularPrecioAsiento(String categoria) {
        if (categoria.equals("Semi Cama")) {
            return 2000;
        } else if (categoria.equals("Salón Cama")) {
            return 4000;
        } else {
            return 0;
        }
    }

    /**
     * Calcula el precio total de un viaje, incluyendo el precio base y el precio de los asientos.
     * @param origen la ubicación de origen.
     * @param destino la ubicación de destino.
     * @param bus el bus asignado a la ruta.
     * @return el precio total del viaje.
     */
    public int calcularPrecioTotal(Ubicaciones origen, Ubicaciones destino, Bus bus) {
        int precioBase = calcularPrecioBase(origen, destino);
        int precioTotal = precioBase;
        for (Asiento asiento : bus.getAsientos()) {
            precioTotal += calcularPrecioAsiento(asiento.getCategoria()) / 120000;
        }
        return precioTotal;
    }
}
