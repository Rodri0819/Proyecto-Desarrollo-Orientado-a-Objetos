package org.example.Vista;

import javax.swing.*;

public class Ventana extends javax.swing.JFrame {

    public Ventana() {
    configurarVentana();
    }

    private void configurarVentana() {
        this.setTitle("Sistema de reserva de asientos de autobús");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1100, 800);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
