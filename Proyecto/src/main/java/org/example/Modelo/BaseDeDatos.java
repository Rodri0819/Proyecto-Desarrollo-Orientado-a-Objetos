package org.example.Modelo;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BaseDeDatos {
    private List<Ruta> rutas;
    private List<Bus> buses;

    public BaseDeDatos() {
        rutas = new ArrayList<>();
        buses = new ArrayList<>();
        cargarDatos();
    }

    private void cargarDatos() {
        // Crear buses
        Bus bus1 = new SingleBus(28, "Bus1");
        Bus bus2 = new DoubleBus(40, "Bus2");

        buses.add(bus1);
        buses.add(bus2);

        // Crear rutas para el día de hoy
        Date todayDate = new Date();
        rutas.add(new Ruta(Ubicaciones.LOS_ANGELES, Ubicaciones.SANTIAGO, todayDate, "08:00", bus1));
        rutas.add(new Ruta(Ubicaciones.LOS_ANGELES, Ubicaciones.SANTIAGO, todayDate, "12:00", bus2));
        rutas.add(new Ruta(Ubicaciones.LOS_ANGELES, Ubicaciones.SANTIAGO, todayDate, "17:00", bus1));
        rutas.add(new Ruta(Ubicaciones.LOS_ANGELES, Ubicaciones.SANTIAGO, todayDate, "20:00", bus2));

        // Crear rutas para el día siguiente
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        Date tomorrowDate = Date.from(tomorrow.atStartOfDay(ZoneId.systemDefault()).toInstant());

        rutas.add(new Ruta(Ubicaciones.LOS_ANGELES, Ubicaciones.SANTIAGO, tomorrowDate, "08:00", bus1));
        rutas.add(new Ruta(Ubicaciones.LOS_ANGELES, Ubicaciones.SANTIAGO, tomorrowDate, "12:00", bus2));
        rutas.add(new Ruta(Ubicaciones.LOS_ANGELES, Ubicaciones.SANTIAGO, tomorrowDate, "17:00", bus1));
        rutas.add(new Ruta(Ubicaciones.LOS_ANGELES, Ubicaciones.SANTIAGO, tomorrowDate, "20:00", bus2));
    }

    public List<Ruta> obtenerRutas(Ubicaciones origen, Ubicaciones destino, Date fecha) {
        List<Ruta> rutasEncontradas = new ArrayList<>();
        for (Ruta ruta : rutas) {
            if (ruta.getOrigen() == origen && ruta.getDestino() == destino && esMismaFecha(ruta.getFecha(), fecha)) {
                rutasEncontradas.add(ruta);
            }
        }
        return rutasEncontradas;
    }

    private boolean esMismaFecha(Date fecha1, Date fecha2) {
        return fecha1.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate().equals(fecha2.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
    }

    public List<Bus> getBuses() {
        return buses;
    }
}
