package org.example.Vista;

import javax.swing.*;
import java.awt.event.ActionListener;

public class PanelCliente extends JPanel {


    public PanelCliente() {
        configurarPanel();
    }

    private void configurarPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }


}
