    package org.example.Modelo;

    import java.time.LocalDate;
    import java.time.LocalTime;
    import java.time.ZoneId;
    import java.time.Duration;
    import java.util.ArrayList;
    import java.util.Date;
    import java.util.List;

    public class RutaFactory {
        private List<Bus> buses;

        public RutaFactory(List<Bus> buses) {
            this.buses = buses;
        }

        public List<Ruta> crearRutas(Ubicaciones origen, Ubicaciones destino, int dias) {
            List<Ruta> rutas = new ArrayList<>();
            LocalTime horaActual = LocalTime.now();
            LocalTime start = LocalTime.of(8, 0); // Hora de inicio general
            LocalTime end = LocalTime.of(22, 0); // Hora de fin
            int busIndex = 0;

            for (int i = 0; i < dias; i++) {
                LocalDate localDate = LocalDate.now().plusDays(i);
                Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

                LocalTime currentStart;
                if (i == 0) { // Si es el día actual
                    currentStart = horaActual.plusMinutes(30).withMinute(0).plusHours(1); // Redondear a la siguiente hora completa
                    if (currentStart.isAfter(end)) {
                        currentStart = end; // Asegurar que no exceda la hora de fin
                    }
                } else { // Días futuros
                    currentStart = start;
                }

                LocalTime current = currentStart;
                while (!current.isAfter(end)) {
                    Bus busOriginal = buses.get(busIndex); // Alterna entre los buses disponibles
                    Bus busClonado = busOriginal.clone(); // Clona el bus para esta ruta
                    int precio = calcularPrecioTotal(origen, destino, busClonado);
                    rutas.add(new Ruta(origen, destino, date, current, busClonado, precio));

                    // Alternar el índice de bus
                    busIndex = (busIndex + 1) % buses.size();

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
