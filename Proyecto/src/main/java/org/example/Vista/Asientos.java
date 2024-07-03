package org.example.Vista;

import org.example.Modelo.Asiento;
import org.example.Modelo.Bus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Asientos extends JPanel {
    private List<Asiento> asientos;
    private Bus bus;
    private PanelCliente panelCliente;

    public Asientos(List<Asiento> asientos, Bus bus, PanelCliente panelCliente) {
        this.asientos = asientos;
        this.bus = bus;
        this.panelCliente = panelCliente;
        setLayout(null);
        actualizarAsientos();
    }

    private void actualizarAsientos() {
        removeAll();

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
                if (asiento.getCategoria()=="Semi Cama") {
                    botonAsiento.setBackground(new Color(0x53E131));
                } else {
                    botonAsiento.setBackground(new Color(0x239A64));
                }
            } else {
                botonAsiento.setBackground(new Color(0xCE1D1D));
            }

            // Agregar ActionListener al botón
            botonAsiento.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        if (asiento.isEstado()) {
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
    }
}
