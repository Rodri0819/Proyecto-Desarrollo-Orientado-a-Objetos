package org.example.Vista;

import org.example.Modelo.Asiento;
import org.example.Modelo.Bus;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * La clase representa un panel de visualización de asientos de un bus,
 * permitiendo la interacción para reservar y liberar asientos.
 * Esta clase se integra con los paneles de cliente y administrador para manejar las acciones
 * relacionadas con los asientos.
 */
public class Asientos extends JPanel {
    private List<Asiento> asientos;
    private Bus bus;
    private PanelCliente panelCliente;
    private PanelAdmin panelAdmin;

    /**
     * Crea una instancia de {@code Asientos} para el panel del cliente.
     * @param asientos la lista de asientos del bus.
     * @param bus el bus asociado a los asientos.
     * @param panelCliente el panel del cliente para manejar las interacciones de reserva.
     */
    public Asientos(List<Asiento> asientos, Bus bus, PanelCliente panelCliente) {
        this.asientos = asientos;
        this.bus = bus;
        this.panelCliente = panelCliente;
        setLayout(null);
        actualizarAsientos();
    }

    /**
     * Crea una instancia de {@code Asientos} para el panel del administrador.
     * @param asientos la lista de asientos del bus.
     * @param bus el bus asociado a los asientos.
     * @param panelAdmin el panel del administrador para manejar las interacciones de reserva.
     */
    public Asientos(List<Asiento> asientos, Bus bus, PanelAdmin panelAdmin) {
        this.asientos = asientos;
        this.bus = bus;
        this.panelAdmin = panelAdmin;
        setLayout(null);
        actualizarAsientos();
    }

    /**
     * Actualiza la visualización de los asientos en el panel, configurando sus estados y
     * añadiendo los correspondientes listeners para las acciones de reserva y liberación.
     */
    private void actualizarAsientos() {
        removeAll();
        System.out.println("Actualizando asientos...");

        int x1 = 70;
        int x2 = 130;
        int x3 = 390;
        int x4 = 450;
        int y = 10;
        int ancho = 50;
        int alto = 50;
        int espacio = 10;
        int alturafila = alto + espacio;

        for (int i = 0; i < asientos.size(); i++) {
            Asiento asiento = asientos.get(i);
            JButton botonAsiento = new JButton(String.valueOf(asiento.getNumero()));
            botonAsiento.setPreferredSize(new Dimension(ancho, alto));

            if (asiento.isEstado()) {
                if (asiento.getCategoria().equals("Semi Cama")) {
                    botonAsiento.setBackground(new Color(0x53E131));
                } else {
                    botonAsiento.setBackground(new Color(0x239A64));
                }
            } else {
                botonAsiento.setBackground(new Color(0xCE1D1D));
            }


            botonAsiento.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        if (panelAdmin != null) {
                            if (!asiento.isEstado()) { // Si el asiento está reservado
                                panelAdmin.quitarReservaAsiento(asiento);
                                actualizarAsientos();
                            } else {
                                JOptionPane.showMessageDialog(null, "El asiento no está reservado.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else if (asiento.isEstado() && panelCliente != null) {
                            panelCliente.setAsientoSeleccionado(asiento);
                            panelCliente.mostrarPanel4();
                        } else {
                            throw new IllegalStateException("El asiento ya está reservado.");
                        }
                    } catch (IllegalStateException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            if (i % 4 == 0) {
                botonAsiento.setBounds(x1, y, ancho, alto);
            } else if (i % 4 == 1) {
                botonAsiento.setBounds(x2, y, ancho, alto);
            } else if (i % 4 == 2) {
                botonAsiento.setBounds(x3, y, ancho, alto);
            } else {
                botonAsiento.setBounds(x4, y, ancho, alto);
                y += alturafila;
            }

            add(botonAsiento);
        }

        revalidate();
        repaint();
        System.out.println("Asientos actualizados.");
    }
}
