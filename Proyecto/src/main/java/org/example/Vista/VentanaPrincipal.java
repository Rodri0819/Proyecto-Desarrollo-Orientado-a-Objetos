package org.example.Vista;

import org.example.Modelo.*;
import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.util.List;

public class VentanaPrincipal extends JFrame {
    private PanelPrincipal panelPrincipal;

    public VentanaPrincipal(List<LocalTime> horarios, List<Bus> buses, BaseDeDatos baseDeDatos) {
        configurarVentana(horarios, buses, baseDeDatos);

        setIconImage(new ImageIcon(getClass().getResource("/images/logoBus.png")).getImage());
        setVisible(true);
    }

    private void configurarVentana(List<LocalTime> horarios, List<Bus> buses, BaseDeDatos baseDeDatos) {
        this.setTitle("Sistema de Reserva de Asientos de Autobús");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 800);
        this.setLayout(new BorderLayout());

        panelPrincipal = new PanelPrincipal(horarios, buses, baseDeDatos);
        this.add(panelPrincipal, BorderLayout.CENTER);

        // Configurar la ventana para que se abra en modo de ventana completa
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }

    public PanelCliente getPanelCliente() {
        return panelPrincipal.getPanelCliente();
    }

    public PanelAdmin getPanelAdmin() {
        return panelPrincipal.getPanelAdmin();
    }
}
