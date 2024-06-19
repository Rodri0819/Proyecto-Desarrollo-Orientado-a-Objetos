package org.example.Vista;

import org.example.Modelo.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.List;

public class PanelAdmin extends JPanel {
    private JComboBox<LocalTime> comboBoxHorarios;
    private JComboBox<Ubicaciones> comboBoxUbicaciones;
    private JComboBox<String> comboBoxBuses;
    private JPanel crearBus, registroPasaje, informeBus;

    public PanelAdmin(List<LocalTime> horarios, List<Bus> buses) {
        setLayout(null); // Establecer layout nulo
        configurarPanel2(horarios, buses);
    }

    private void configurarPanel2(List<LocalTime> horarios, List<Bus> buses) {
        setBackground(new Color(114, 206, 206));
        setPreferredSize(new Dimension(600, 800));

        crearBus = new JPanel();
        crearBus.setLayout(null);
        crearBus.setBounds(8, 50, 570, 200); // Ajusta según sea necesario
        crearBus.setBackground(new Color(200, 200, 200));
        add(crearBus);

        registroPasaje = new JPanel();
        registroPasaje.setLayout(null);
        registroPasaje.setBounds(8, 260, 570, 200); // Ajusta según sea necesario
        registroPasaje.setBackground(new Color(200, 200, 200));
        add(registroPasaje);

        informeBus = new JPanel();
        informeBus.setLayout(null);
        informeBus.setBounds(8, 470, 570, 200); // Ajusta según sea necesario
        informeBus.setBackground(new Color(200, 200, 200));
        add(informeBus);

        agregarLabel();
        agregarHorarios(horarios);
        agregarRutas();
        agregarBuses(buses);
    }

    private void agregarLabel() {
        JLabel panel = new JLabel("Panel Administrador");
        panel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.setBounds(200, 10, 200, 30);
        this.add(panel); // Agregar el JLabel directamente al PanelAdmin

        JLabel recorridos = new JLabel("Crear recorrido");
        recorridos.setFont(new Font("Arial", Font.BOLD, 15));
        recorridos.setBounds(10, 10, 200, 30); // Establecer posición y tamaño del JLabel
        crearBus.add(recorridos); // Agregar el JLabel al subpanel crearBus

        JLabel registrarPasaje = new JLabel("Registrar pasaje");
        registrarPasaje.setFont(new Font("Arial", Font.BOLD, 15));
        registrarPasaje.setBounds(10, 10, 200, 30); // Establecer posición y tamaño del JLabel
        registroPasaje.add(registrarPasaje); // Agregar el JLabel al subpanel registroPasaje

        JLabel informeAsientos = new JLabel("Descargar informe asientos");
        informeAsientos.setFont(new Font("Arial", Font.BOLD, 15));
        informeAsientos.setBounds(10, 10, 300, 30); // Establecer posición y tamaño del JLabel
        informeBus.add(informeAsientos); // Agregar el JLabel al subpanel informeBus
    }

    private void agregarHorarios(List<LocalTime> horarios) {
        comboBoxHorarios = new JComboBox<>(horarios.toArray(new LocalTime[0]));
        comboBoxHorarios.setBounds(10, 50, 200, 30); // Establecer posición y tamaño del JComboBox
        crearBus.add(comboBoxHorarios);
    }

    private void agregarRutas() {
        comboBoxUbicaciones = new JComboBox<>(Ubicaciones.values());
        comboBoxUbicaciones.setBounds(220, 50, 200, 30); // Establecer posición y tamaño del JComboBox
        crearBus.add(comboBoxUbicaciones);
    }

    private void agregarBuses(List<Bus> buses) {
        String[] idsDeBuses = new String[buses.size()];
        for (int i = 0; i < buses.size(); i++) {
            idsDeBuses[i] = buses.get(i).getId();
        }

        // Crear el JComboBox con los ids de los buses
        comboBoxBuses = new JComboBox<>(idsDeBuses);
        comboBoxBuses.setBounds(10, 100, 200, 30); // Establecer posición y tamaño del JComboBox
        crearBus.add(comboBoxBuses);
    }

    public LocalTime getHorarioSeleccionado() {
        return (LocalTime) comboBoxHorarios.getSelectedItem();
    }

    public String getBusSeleccionado() {
        return (String) comboBoxBuses.getSelectedItem();
    }

    public void addComboBoxActionListener(ActionListener listener) {
        comboBoxHorarios.addActionListener(listener);
    }
}
