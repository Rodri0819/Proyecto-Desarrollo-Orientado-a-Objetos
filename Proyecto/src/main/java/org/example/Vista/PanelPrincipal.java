package org.example.Vista;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.util.List;

public class PanelPrincipal extends JPanel {
    private PanelCliente panelCliente;
    private PanelAdmin panelAdmin;

    public PanelPrincipal(List<LocalTime> horarios) {
        configurarPanel(horarios);
    }

    private void configurarPanel(List<LocalTime> horarios) {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBackground(Color.gray);

        panelCliente = new PanelCliente();
        panelAdmin = new PanelAdmin(horarios);

        this.add(panelCliente);
        this.add(panelAdmin);
    }

    public PanelCliente getPanelCliente() {
        return panelCliente;
    }

    public PanelAdmin getPanelAdmin() {
        return panelAdmin;
    }
}
