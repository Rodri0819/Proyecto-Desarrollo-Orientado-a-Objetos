package org.example.Vista;

import org.example.Modelo.Ruta;
import org.example.Modelo.Ubicaciones;
import com.toedter.calendar.JCalendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelCliente extends JPanel {
    private JPanel desde, hasta, dia;
    private JComboBox<Ubicaciones> comboBoxDesde, comboBoxHasta;
    private JCalendar calendario;
    private JButton botonVolver;
    private JPanel panel1, panel2;

    public PanelCliente() {
        configurarPanel();
        agregarPanel1();
        agregarPanel2();
        mostrarPanel1();
    }

    private void configurarPanel() {
        // Configurar el panel principal con un CardLayout para alternar entre paneles
        setBackground(new Color(64, 164, 107));
        setPreferredSize(new Dimension(600, 700));
        setLayout(new CardLayout()); // Usar CardLayout para alternar entre subpaneles
    }

    private void agregarPanel1() {
        panel1 = new JPanel();
        panel1.setLayout(null);
        panel1.setBackground(new Color(189, 105, 105));

        JLabel pasaje = new JLabel("Panel Cliente");
        pasaje.setFont(new Font("Arial", Font.BOLD, 20));
        pasaje.setBounds(200, 10, 200, 30);
        panel1.add(pasaje);

        desde = new JPanel();
        desde.setLayout(null);
        desde.setBounds(8, 50, 570, 150);
        desde.setBackground(new Color(157, 218, 184));
        panel1.add(desde);

        JLabel labelDesde = new JLabel("¿Desde donde viajas?");
        labelDesde.setFont(new Font("Arial", Font.BOLD, 15));
        labelDesde.setBounds(10, 10, 200, 30);
        desde.add(labelDesde);

        comboBoxDesde = new JComboBox<>(Ubicaciones.values());
        comboBoxDesde.setBounds(10, 50, 200, 30);
        desde.add(comboBoxDesde);

        hasta = new JPanel();
        hasta.setLayout(null);
        hasta.setBounds(8, 210, 570, 150);
        hasta.setBackground(new Color(225, 122, 122));
        panel1.add(hasta);

        JLabel labelHasta = new JLabel("¿Hacia donde vas?");
        labelHasta.setFont(new Font("Arial", Font.BOLD, 15));
        labelHasta.setBounds(10, 10, 200, 30);
        hasta.add(labelHasta);

        comboBoxHasta = new JComboBox<>(Ubicaciones.values());
        comboBoxHasta.setBounds(10, 50, 200, 30);
        hasta.add(comboBoxHasta);

        dia = new JPanel();
        dia.setLayout(null);
        dia.setBounds(8, 370, 570, 300);
        dia.setBackground(new Color(162, 113, 194));
        panel1.add(dia);

        JLabel labelDia = new JLabel("¿Qué día vas?");
        labelDia.setFont(new Font("Arial", Font.BOLD, 15));
        labelDia.setBounds(10, 10, 200, 30);
        dia.add(labelDia);

        calendario = new JCalendar();
        calendario.setBounds(80, 50, 400, 200);
        dia.add(calendario);

        agregarBotonItinerario(panel1);

        add(panel1, "Panel1");
    }

    private void agregarPanel2() {
        panel2 = new JPanel();
        panel2.setLayout(null);
        panel2.setBackground(new Color(236, 0, 0));

        agregarBotonVolver(panel2);

        add(panel2, "Panel2");
    }

    private void agregarBotonItinerario(JPanel panel) {
        JButton itinerario = new JButton("Revisar Itinerario");
        itinerario.setFont(new Font("Arial", Font.BOLD, 20));
        itinerario.setBounds(180, 700, 200, 30);
        panel.add(itinerario);

        itinerario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ubicaciones desdeUbicacion = getUbicacionDesde();
                Ubicaciones hastaUbicacion = getUbicacionHasta();
                java.util.Date fechaSeleccionada = getFechaSeleccionada();

                if (desdeUbicacion == hastaUbicacion) {
                    JOptionPane.showMessageDialog(PanelCliente.this, "No puedes viajar hacia donde estás.", "Error", JOptionPane.ERROR_MESSAGE
                    );
                } else {
                    mostrarPanel2();
                }
            }
        });
    }

    private void agregarBotonVolver(JPanel panel) {
        botonVolver = new JButton("Volver");
        botonVolver.setFont(new Font("Arial", Font.BOLD, 20));
        botonVolver.setBounds(180, 700, 200, 30);
        panel.add(botonVolver);

        botonVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPanel1();
            }
        });
    }

    // Métodos para mostrar paneles específicos usando CardLayout
    private void mostrarPanel1() {
        CardLayout cl = (CardLayout) getLayout();
        cl.show(this, "Panel1");
    }

    private void mostrarPanel2() {
        CardLayout cl = (CardLayout) getLayout();
        cl.show(this, "Panel2");
    }

    public Ubicaciones getUbicacionDesde() {
        return (Ubicaciones) comboBoxDesde.getSelectedItem();
    }

    public Ubicaciones getUbicacionHasta() {
        return (Ubicaciones) comboBoxHasta.getSelectedItem();
    }

    public java.util.Date getFechaSeleccionada() {
        return calendario.getDate();
    }
}
