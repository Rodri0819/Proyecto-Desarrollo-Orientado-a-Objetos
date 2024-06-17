package org.example.Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.List;

public class PanelAdmin extends JPanel {
    private JComboBox<LocalTime> comboBoxHorarios;

    public PanelAdmin(List<LocalTime> horarios) {
        configurarPanel(horarios);
    }

    private void configurarPanel(List<LocalTime> horarios) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.cyan);
        //Para los horarios
        comboBoxHorarios = new JComboBox<>(horarios.toArray(new LocalTime[0]));
        this.add(comboBoxHorarios);
    }

    public LocalTime getHorarioSeleccionado() {
        return (LocalTime) comboBoxHorarios.getSelectedItem();
    }

    public void addComboBoxActionListener(ActionListener listener) {
        comboBoxHorarios.addActionListener(listener);
    }
}
