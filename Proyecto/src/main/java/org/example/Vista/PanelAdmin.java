package org.example.Vista;

import org.example.Modelo.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.List;

public class PanelAdmin extends JPanel {
    private JPanel panel1, panel2;
    private JComboBox<LocalTime> comboBoxHorarios;
    private JComboBox<Ubicaciones> comboBoxOrigen;
    private JComboBox<Ubicaciones> comboBoxDestino;
    private JComboBox<String> comboBoxBuses;
    private JPanel crearBus, registroPasaje, informeBus;
    private JTextField nombreField;
    private JPasswordField contrasenaField;
    private BaseDeDatos baseDeDatos;
    private String panelActual;

    public PanelAdmin(List<LocalTime> horarios, List<Bus> buses, BaseDeDatos baseDeDatos) {
        this.baseDeDatos = baseDeDatos;
        setLayout(new CardLayout());
        configurarPanel1();
        configurarPanel2(horarios, buses);
        mostrarPanel1();
    }

    private void configurarPanel1() {
        panel1 = new JPanel();
        panel1.setLayout(null);
        panel1.setBackground(new Color(114, 206, 206));

        JLabel panelAdmin = new JLabel("Panel Administrador");
        panelAdmin.setFont(new Font("Arial", Font.BOLD, 20));
        panelAdmin.setBounds(250, 10, 400, 30);
        panel1.add(panelAdmin);

        JLabel nombreLabel = new JLabel("Usuario");
        nombreLabel.setBounds(200, 70, 100, 30);
        panel1.add(nombreLabel);

        nombreField = new JTextField();
        nombreField.setBounds(300, 70, 200, 30);
        panel1.add(nombreField);

        JLabel contrasenaLabel = new JLabel("Contraseña");
        contrasenaLabel.setBounds(200, 110, 100, 30);
        panel1.add(contrasenaLabel);

        contrasenaField = new JPasswordField();
        contrasenaField.setBounds(300, 110, 200, 30);
        panel1.add(contrasenaField);

        JButton botonLogin = new JButton("Login");
        botonLogin.setBounds(300, 160, 100, 30);
        botonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = nombreField.getText();
                String contrasena = new String(contrasenaField.getPassword());
                if (baseDeDatos.verificarCredenciales(usuario, contrasena)) {
                    mostrarPanel2();
                } else {
                    JOptionPane.showMessageDialog(panel1, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel1.add(botonLogin);

        add(panel1, "Panel1");
    }

    private void configurarPanel2(List<LocalTime> horarios, List<Bus> buses) {
        panel2 = new JPanel();
        panel2.setLayout(null);
        panel2.setBackground(new Color(114, 206, 206));

        crearBus = new JPanel();
        crearBus.setLayout(null);
        crearBus.setBounds(80, 50, 570, 200);
        crearBus.setBackground(new Color(200, 200, 200));
        panel2.add(crearBus);

        registroPasaje = new JPanel();
        registroPasaje.setLayout(null);
        registroPasaje.setBounds(80, 260, 570, 200);
        registroPasaje.setBackground(new Color(200, 200, 200));
        panel2.add(registroPasaje);

        informeBus = new JPanel();
        informeBus.setLayout(null);
        informeBus.setBounds(80, 470, 570, 200);
        informeBus.setBackground(new Color(200, 200, 200));
        panel2.add(informeBus);

        agregarLabel();
        agregarHorarios(horarios);
        agregarOrigen();
        agregarDestino();
        agregarBuses(buses);

        JButton botonVolverPanel2 = new JButton("Volver");
        botonVolverPanel2.setFont(new Font("Arial", Font.BOLD, 20));
        botonVolverPanel2.setBounds(288, 700, 200, 30);
        botonVolverPanel2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPanel1();
            }
        });
        panel2.add(botonVolverPanel2);

        add(panel2, "Panel2");
    }

    private void agregarLabel() {
        JLabel panel = new JLabel("Panel Administrador");
        panel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.setBounds(250, 10, 200, 30);
        panel2.add(panel);

        JLabel recorridos = new JLabel("Crear recorrido");
        recorridos.setFont(new Font("Arial", Font.BOLD, 15));
        recorridos.setBounds(10, 10, 200, 30);
        crearBus.add(recorridos);

        JLabel registrarPasaje = new JLabel("Registrar pasaje");
        registrarPasaje.setFont(new Font("Arial", Font.BOLD, 15));
        registrarPasaje.setBounds(10, 10, 200, 30);
        registroPasaje.add(registrarPasaje);

        JLabel informeAsientos = new JLabel("Descargar informe asientos");
        informeAsientos.setFont(new Font("Arial", Font.BOLD, 15));
        informeAsientos.setBounds(10, 10, 300, 30);
        informeBus.add(informeAsientos);
    }

    private void agregarHorarios(List<LocalTime> horarios) {
        comboBoxHorarios = new JComboBox<>(horarios.toArray(new LocalTime[0]));
        comboBoxHorarios.setBounds(10, 50, 200, 30);
        crearBus.add(comboBoxHorarios);
    }

    private void agregarOrigen() {
        comboBoxOrigen = new JComboBox<>(Ubicaciones.values());
        comboBoxOrigen.setBounds(220, 50, 200, 30);
        crearBus.add(comboBoxOrigen);
    }

    private void agregarDestino() {
        comboBoxDestino = new JComboBox<>(Ubicaciones.values());
        comboBoxDestino.setBounds(220, 100, 200, 30);
        crearBus.add(comboBoxDestino);
    }

    private void agregarBuses(List<Bus> buses) {
        String[] idsDeBuses = new String[buses.size()];
        for (int i = 0; i < buses.size(); i++) {
            idsDeBuses[i] = buses.get(i).getId();
        }

        comboBoxBuses = new JComboBox<>(idsDeBuses);
        comboBoxBuses.setBounds(10, 100, 200, 30);
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

    private void mostrarPanel1() {
        CardLayout cl = (CardLayout) getLayout();
        cl.show(this, "Panel1");
        panelActual = "Panel1";
    }

    private void mostrarPanel2() {
        CardLayout cl = (CardLayout) getLayout();
        cl.show(this, "Panel2");
        panelActual = "Panel2";
    }
}
