package org.example.Vista;

import org.example.Modelo.*;

import javax.swing.*;
import java.time.LocalTime;
import java.util.List;

public class PanelPrincipal extends JPanel {
    private PanelCliente panelCliente;
    private PanelAdmin panelAdmin;

    public PanelPrincipal(List<LocalTime> horarios, List<Bus> buses, BaseDeDatos baseDeDatos) {
        configurarPanel(horarios, buses, baseDeDatos);
    }

    private void configurarPanel(List<LocalTime> horarios, List<Bus> buses, BaseDeDatos baseDeDatos) {
        this.setLayout(null); // Establecer layout nulo

        panelCliente = new PanelCliente();
        panelAdmin = new PanelAdmin(horarios, buses, baseDeDatos);

        // Establecer tamaño y posición del panelCliente
        panelCliente.setBounds(0, 0, 800, 800);
        this.add(panelCliente);

        // Establecer tamaño y posición del panelAdmin
        panelAdmin.setBounds(800, 0, 800, 800);
        this.add(panelAdmin);
    }

    public PanelCliente getPanelCliente() {
        return panelCliente;
    }

    public PanelAdmin getPanelAdmin() {
        return panelAdmin;
    }
}
