package org.example.Controlador;

import org.example.Modelo.*;
import org.example.Vista.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.List;

public class Controlador {
    private List<Ruta> rutas;
    private Cliente cliente;
    private VentanaPrincipal ventanaPrincipal;

    public Controlador(List<Ruta> rutas, Cliente cliente, VentanaPrincipal ventanaPrincipal) {
        this.rutas = rutas;
        this.cliente = cliente;
        this.ventanaPrincipal = ventanaPrincipal;
        inicializarControlador();
    }

    private void inicializarControlador() {
        ventanaPrincipal.getPanelAdmin().addComboBoxActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalTime horarioSeleccionado = ventanaPrincipal.getPanelAdmin().getHorarioSeleccionado();
                System.out.println("Horario seleccionado: " + horarioSeleccionado);
                // Aquí puedes añadir lógica para manejar la selección de horarios
            }
        });
    }
}
