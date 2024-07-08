package org.example.Vista;

import org.example.Modelo.*;
import javax.swing.*;
import java.time.LocalTime;
import java.util.List;

/**
 * La clase PanelPrincipal sirve como contenedor para los paneles de cliente y administrador.
 * Permite gestionar la vista y la funcionalidad de ambos paneles en un mismo espacio.
 */
public class PanelPrincipal extends JPanel {
    private PanelCliente panelCliente;
    private PanelAdmin panelAdmin;

    /**
     * Constructor para la clase PanelPrincipal.
     * Inicializa los paneles de cliente y administrador con los datos proporcionados.
     * @param horarios    una lista de horarios disponibles.
     * @param buses       una lista de buses disponibles.
     * @param baseDeDatos la instancia de la base de datos.
     */
    public PanelPrincipal(List<LocalTime> horarios, List<Bus> buses, BaseDeDatos baseDeDatos) {
        configurarPanel(horarios, buses, baseDeDatos);
    }

    /**
     * Configura el panel principal añadiendo los paneles de cliente y administrador.
     * Establece el diseño y las posiciones de los paneles.
     * @param horarios    una lista de horarios disponibles.
     * @param buses       una lista de buses disponibles.
     * @param baseDeDatos la instancia de la base de datos.
     */
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

    /**
     * Obtiene el panel de cliente.
     * @return el panel de cliente.
     */
    public PanelCliente getPanelCliente() {
        return panelCliente;
    }

    /**
     * Obtiene el panel de administrador.
     * @return el panel de administrador.
     */
    public PanelAdmin getPanelAdmin() {
        return panelAdmin;
    }
}
