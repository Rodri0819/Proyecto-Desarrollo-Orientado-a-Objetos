package org.example.Vista;

import com.toedter.calendar.JDateChooser;
import org.example.Modelo.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class PanelAdmin extends JPanel {
    private JPanel panel1, panel2, panel3;
    private JComboBox<LocalTime> comboBoxHorarios;
    private JComboBox<Ubicaciones> comboBoxOrigen;
    private JComboBox<Ubicaciones> comboBoxDestino;
    private JComboBox<String> comboBoxBuses;
    private JPanel crearRecorrido = new JPanel(), Opciones;
    private JTextField nombreField;
    private JPasswordField contrasenaField;
    private BaseDeDatos baseDeDatos;
    private String panelActual;
    private JComboBox<String> comboBoxInformeBuses;
    private JDateChooser dateChooser;
    private JButton botonCrearRecorrido, botonRegistrarPasaje;
    private List<Ruta> rutas;  // Definición de la variable de instancia para almacenar rutas
    private JScrollPane scrollPaneRutas;  // Si deseas hacer la tabla desplazable
    private JTable tablaRutas; // Define la tabla aquí como variable de instancia

    public PanelAdmin(List<LocalTime> horarios, List<Bus> buses, BaseDeDatos baseDeDatos) {
        this.baseDeDatos = BaseDeDatos.getInstance();  // Usar la instancia Singleton
        setLayout(new CardLayout());
        configurarPanel1();
        configurarPanel2(horarios, buses);
        configurarPanel3();
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

        crearRecorrido.setLayout(null);
        crearRecorrido.setBounds(80, 50, 570, 300); // Aumentar la altura para acomodar todos los componentes
        crearRecorrido.setBackground(new Color(200, 200, 200));
        panel2.add(crearRecorrido);

        agregarLabel(crearRecorrido);
        agregarHorarios(crearRecorrido, horarios);
        agregarFecha(crearRecorrido);
        agregarOrigen(crearRecorrido);
        agregarDestino(crearRecorrido);
        agregarBuses(crearRecorrido, buses);
        configurarBotonesOpciones(crearRecorrido);

        Opciones = new JPanel();
        Opciones.setLayout(null);
        Opciones.setBounds(80, 370, 570, 250); // Ajustar la altura para incluir más espacio
        Opciones.setBackground(new Color(200, 200, 200));
        panel2.add(Opciones);

        JLabel labelRegistrarCliente = new JLabel("Registrar Cliente");
        labelRegistrarCliente.setFont(new Font("Arial", Font.BOLD, 15));
        labelRegistrarCliente.setBounds(10, 10, 200, 30);
        Opciones.add(labelRegistrarCliente);

        botonRegistrarPasaje = new JButton("Registrar Pasaje");
        botonRegistrarPasaje.setBounds(10, 180, 250, 30);
        botonRegistrarPasaje.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ubicaciones desdeUbicacion = getUbicacionDesde();
                Ubicaciones hastaUbicacion = getUbicacionHasta();
                Date fechaSeleccionada = getFechaSeleccionada();

                if (desdeUbicacion == hastaUbicacion) {
                    JOptionPane.showMessageDialog(PanelAdmin.this, "No puedes viajar hacia donde estás.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    rutas = baseDeDatos.obtenerRutas(desdeUbicacion, hastaUbicacion, fechaSeleccionada);
                    if (!rutas.isEmpty()) {
                        mostrarRutas(rutas);
                        mostrarPanel3();
                    } else {
                        JOptionPane.showMessageDialog(PanelAdmin.this, "No se encontraron rutas para la selección.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        Opciones.add(botonRegistrarPasaje);

        agregarFecha(Opciones);
        agregarOrigen(Opciones);
        agregarDestino(Opciones);

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

        tablaRutas = new JTable();  // Asegúrate de inicializar tablaRutas aquí
        tablaRutas.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = tablaRutas.getSelectedRow();
                    if (fila != -1) {
                        Ruta rutaSeleccionada = rutas.get(fila); // Guardar la ruta seleccionada
                        // Aquí puedes agregar el código para manejar la selección de la ruta
                    }
                }
            }
        });
    }

    private void configurarPanel3() {
        panel3 = new JPanel();
        panel3.setLayout(null);
        panel3.setBackground(new Color(173, 216, 230));

        JLabel labelDetalle = new JLabel("Rutas Disponibles", SwingConstants.CENTER);
        labelDetalle.setFont(new Font("Arial", Font.BOLD, 19));
        labelDetalle.setOpaque(true);
        labelDetalle.setBackground(new Color(213, 79, 250, 199));
        labelDetalle.setForeground(Color.WHITE);
        labelDetalle.setBounds(100, 10, 600, 30);
        panel3.add(labelDetalle);

        // Configura la tabla de rutas
        tablaRutas = new JTable();
        scrollPaneRutas = new JScrollPane(tablaRutas);
        scrollPaneRutas.setBounds(100, 50, 600, 500);  // Ajusta según tus necesidades
        panel3.add(scrollPaneRutas);

        JButton botonVolverPanel3 = new JButton("Volver");
        botonVolverPanel3.setFont(new Font("Arial", Font.BOLD, 20));
        botonVolverPanel3.setBounds(288, 700, 200, 30);
        botonVolverPanel3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPanel2();
            }
        });
        panel3.add(botonVolverPanel3);

        add(panel3, "Panel3");
    }

    private void mostrarRutas(List<Ruta> rutas) {
        String[] columnNames = {"Origen", "Destino", "Fecha", "Hora", "Bus"};
        Object[][] data = new Object[rutas.size()][5];
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i < rutas.size(); i++) {
            Ruta ruta = rutas.get(i);
            data[i][0] = ruta.getOrigen();
            data[i][1] = ruta.getDestino();
            data[i][2] = dateFormat.format(ruta.getFecha());
            data[i][3] = ruta.getHoraFormateada(); // Usar getHoraFormateada para obtener la hora formateada
            data[i][4] = ruta.getBus().getId();
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaRutas.setModel(model);
        tablaRutas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaRutas.setRowSelectionAllowed(true);
        tablaRutas.setColumnSelectionAllowed(false);

        TableColumnModel columnModel = tablaRutas.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(100);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(50);
        columnModel.getColumn(4).setPreferredWidth(50);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < tablaRutas.getColumnCount(); i++) {
            tablaRutas.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        tablaRutas.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row % 2 == 0) {
                    setBackground(Color.LIGHT_GRAY);
                } else {
                    setBackground(Color.WHITE);
                }
                if (isSelected) {
                    setBackground(Color.CYAN);
                }
                return this;
            }
        });
    }

    private void agregarLabel(JPanel panel) {
        JLabel opciones = new JLabel("Crear recorrido");
        opciones.setFont(new Font("Arial", Font.BOLD, 15));
        opciones.setBounds(10, 10, 200, 30);
        panel.add(opciones);
    }

    private void agregarHorarios(JPanel panel, List<LocalTime> horarios) {
        comboBoxHorarios = new JComboBox<>(horarios.toArray(new LocalTime[0]));
        comboBoxHorarios.setBounds(10, 50, 200, 30);
        panel.add(comboBoxHorarios);
    }

    private void agregarFecha(JPanel panel) {
        dateChooser = new JDateChooser();
        dateChooser.setBounds(10, 100, 200, 30);
        panel.add(dateChooser);
    }

    private void agregarOrigen(JPanel panel) {
        comboBoxOrigen = new JComboBox<>(Ubicaciones.values());
        comboBoxOrigen.setBounds(220, 50, 200, 30);
        panel.add(comboBoxOrigen);
    }

    private void agregarDestino(JPanel panel) {
        comboBoxDestino = new JComboBox<>(Ubicaciones.values());
        comboBoxDestino.setBounds(220, 100, 200, 30);
        panel.add(comboBoxDestino);
    }

    private void agregarBuses(JPanel panel, List<Bus> buses) {
        String[] idsDeBuses = new String[buses.size()];
        for (int i = 0; i < buses.size(); i++) {
            idsDeBuses[i] = buses.get(i).getId();
        }

        comboBoxBuses = new JComboBox<>(idsDeBuses);
        comboBoxBuses.setBounds(10, 150, 200, 30);
        panel.add(comboBoxBuses);
    }

    private void configurarBotonesOpciones(JPanel panel) {
        botonCrearRecorrido = new JButton("Crear Recorrido");
        botonCrearRecorrido.setBounds(10, 240, 250, 30);
        botonCrearRecorrido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearRecorrido();
            }
        });
        panel.add(botonCrearRecorrido);
    }

    private void agregarBusesInforme(JPanel panel, List<Bus> buses) {
        JButton botonDescargarInforme = new JButton("Descargar Informe");
        botonDescargarInforme.setFont(new Font("Arial", Font.BOLD, 15));
        botonDescargarInforme.setBounds(320, 160, 200, 30);
        botonDescargarInforme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                descargarInformeAsientos();
            }
        });
        panel.add(botonDescargarInforme);
    }

    private void descargarInformeAsientos() {
        String idBusSeleccionado = (String) comboBoxInformeBuses.getSelectedItem();
        Bus busSeleccionado = baseDeDatos.getBusPorId(idBusSeleccionado);

        if (busSeleccionado != null) {
            String rutaArchivoInforme = "informeBuses\\informe_asientos_" + busSeleccionado.getId() + ".txt";

            try {
                InformeDeRuta informe = new InformeDeRuta(busSeleccionado, rutaArchivoInforme);
                informe.generarInforme();
                JOptionPane.showMessageDialog(this, "Informe descargado correctamente en: " + rutaArchivoInforme, "Informe Descargado", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al descargar el informe", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Bus no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public LocalTime getHorarioSeleccionado() {
        return (LocalTime) comboBoxHorarios.getSelectedItem();
    }

    public JButton getBotonCrearRecorrido() {
        return botonCrearRecorrido;
    }

    public void crearRecorrido() {
        LocalTime horarioSeleccionado = (LocalTime) comboBoxHorarios.getSelectedItem();
        Ubicaciones origenSeleccionado = (Ubicaciones) comboBoxOrigen.getSelectedItem();
        Ubicaciones destinoSeleccionado = (Ubicaciones) comboBoxDestino.getSelectedItem();
        String busSeleccionadoId = (String) comboBoxBuses.getSelectedItem();
        Bus busSeleccionado = baseDeDatos.getBusPorId(busSeleccionadoId);
        Date fechaSeleccionada = dateChooser.getDate();

        if (horarioSeleccionado != null && origenSeleccionado != null && destinoSeleccionado != null && busSeleccionado != null && fechaSeleccionada != null) {
            int numeroAsientosDisponibles = busSeleccionado.getCapacidad();
            Ruta nuevaRuta = new Ruta(origenSeleccionado, destinoSeleccionado, fechaSeleccionada, horarioSeleccionado, busSeleccionado, numeroAsientosDisponibles);
            baseDeDatos.agregarRuta(nuevaRuta);
            JOptionPane.showMessageDialog(this, "Recorrido creado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
        }
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

    private void mostrarPanel3() {
        CardLayout cl = (CardLayout) getLayout();
        cl.show(this, "Panel3");
        panelActual = "Panel3";
    }

    public Ubicaciones getUbicacionDesde() {
        return (Ubicaciones) comboBoxOrigen.getSelectedItem();
    }

    public Ubicaciones getUbicacionHasta() {
        return (Ubicaciones) comboBoxDestino.getSelectedItem();
    }

    public Date getFechaSeleccionada() {
        return dateChooser.getDate();
    }
}
