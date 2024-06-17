package org.example.Vista;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.util.List;

public class VentanaPrincipal extends JFrame {
    private PanelPrincipal panelPrincipal;

    public VentanaPrincipal(List<LocalTime> horarios) {
        configurarVentana(horarios);
    }

    private void configurarVentana(List<LocalTime> horarios) {
        this.setTitle("Sistema de Reserva de Asientos de Autobús");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 800);
        this.setLayout(new BorderLayout());

        panelPrincipal = new PanelPrincipal(horarios);
        this.add(panelPrincipal, BorderLayout.CENTER);

        this.setVisible(true);
    }

    public PanelCliente getPanelCliente() {
        return panelPrincipal.getPanelCliente();
    }

    public PanelAdmin getPanelAdmin() {
        return panelPrincipal.getPanelAdmin();
    }
}
