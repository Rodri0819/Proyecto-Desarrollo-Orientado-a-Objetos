package org.example;

import org.example.Modelo.*;
import org.example.Vista.VentanaPrincipal;
import org.example.Controlador.Controlador;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Crear instancia de BaseDeDatos
        BaseDeDatos baseDeDatos = new BaseDeDatos();
        List<Bus> buses = baseDeDatos.getBuses(); // Obtener buses desde BaseDeDatos

        List<Ruta> rutas = baseDeDatos.obtenerRutas(Ubicaciones.LOS_ANGELES, Ubicaciones.SANTIAGO, new Date());

        Cliente cliente = new Cliente("Juan", "Perez", "12345678-9", "juan.perez@example.com");

        // Generar lista de horarios desde las 8:00 AM hasta las 10:00 PM con intervalos de 30 minutos
        List<LocalTime> horarios = Horario.generarHorarios(LocalTime.of(8, 0), LocalTime.of(22, 0), 30);

        // Instanciar la vista con horarios y baseDeDatos
        VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(horarios, buses, baseDeDatos);

        // Instanciar el controlador con el modelo y la vista
        Controlador controlador = new Controlador(rutas, cliente, ventanaPrincipal);

        // Mostrar la ventana principal
        ventanaPrincipal.setVisible(true);
    }
}
