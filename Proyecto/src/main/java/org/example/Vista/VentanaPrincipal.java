package org.example.Vista;

import org.example.Modelo.*;
import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.util.List;

/**
 * La clase VentanaPrincipal representa la ventana principal de la aplicación.
 * Contiene un panel principal que agrupa los paneles de cliente y administrador.
 */
public class VentanaPrincipal extends JFrame {
    private PanelPrincipal panelPrincipal;

    /**
     * Constructor para la clase VentanaPrincipal.
     * Inicializa la ventana y sus componentes.
     * @param horarios    una lista de horarios disponibles.
     * @param buses       una lista de buses disponibles.
     * @param baseDeDatos la instancia de la base de datos.
     */
    public VentanaPrincipal(List<LocalTime> horarios, List<Bus> buses, BaseDeDatos baseDeDatos) {
        configurarVentana(horarios, buses, baseDeDatos);

        // Establecer el icono de la ventana
        setIconImage(new ImageIcon(getClass().getResource("/images/logoBus.png")).getImage());
        setVisible(true);
    }

    /**
     * Configura las propiedades y componentes de la ventana principal.
     * @param horarios    una lista de horarios disponibles.
     * @param buses       una lista de buses disponibles.
     * @param baseDeDatos la instancia de la base de datos.
     */
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

    /**
     * Obtiene el panel de cliente.
     * @return el panel de cliente.
     */
    public PanelCliente getPanelCliente() {
        return panelPrincipal.getPanelCliente();
    }

    /**
     * Obtiene el panel de administrador.
     * @return el panel de administrador.
     */
    public PanelAdmin getPanelAdmin() {
        return panelPrincipal.getPanelAdmin();
    }
}
