package org.example;

import org.example.Modelo.*;
import org.example.Vista.VentanaPrincipal;
import java.time.LocalTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Obtener la única instancia de BaseDeDatos usando el patrón Singleton
        BaseDeDatos baseDeDatos = BaseDeDatos.getInstance();
        List<Bus> buses = baseDeDatos.getBuses(); // Obtener buses desde BaseDeDatos

        // Generar lista de horarios desde las 8:00 AM hasta las 10:00 PM con intervalos de 30 minutos
        List<LocalTime> horarios = Horario.generarHorarios(LocalTime.of(8, 0), LocalTime.of(22, 0), 30);

        // Instanciar la vista con horarios y baseDeDatos
        VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(horarios, buses, baseDeDatos);

        // Mostrar la ventana principal
        ventanaPrincipal.setVisible(true);
    }
}
