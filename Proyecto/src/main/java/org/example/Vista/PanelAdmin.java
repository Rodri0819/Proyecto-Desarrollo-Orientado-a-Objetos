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
    // Otros atributos
    private JPanel panel1, panel2, panel3, panel4;
    private JComboBox<LocalTime> comboBoxHorarios;
    private JComboBox<Ubicaciones> comboBoxOrigen;
    private JComboBox<Ubicaciones> comboBoxDestino;
    private JPanel crearRecorrido = new JPanel(), Opciones;
    private JTextField nombreField;
    private JPasswordField contrasenaField;
    private BaseDeDatos baseDeDatos;
    private String panelActual;
    private JButton botonInforme, botonAsientos;
    private JComboBox<String> comboBoxInformeBuses;
    private JDateChooser dateChooser;
    private JButton botonCrearRecorrido, botonRegistrarPasaje;
    private List<Ruta> rutas;  // Definición de la variable de instancia para almacenar rutas
    private JScrollPane scrollPaneRutas;  // Si deseas hacer la tabla desplazable
    private JTable tablaRutas; // Define la tabla aquí como variable de instancia
    private JComboBox<LocalTime> comboBoxHorariosCrear;
    private JComboBox<Ubicaciones> comboBoxOrigenCrear;
    private JComboBox<Ubicaciones> comboBoxDestinoCrear;
    private JComboBox<String> comboBoxBusesCrear;
    private JDateChooser dateChooserCrear;
    private Ruta rutaSeleccionada;

    public PanelAdmin(List<LocalTime> horarios, List<Bus> buses, BaseDeDatos baseDeDatos) {
        this.baseDeDatos = BaseDeDatos.getInstance();
        setLayout(new CardLayout());
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 1, true));
        configurarPanel1();
        configurarPanel2(horarios, buses);
        configurarPanel3();
        configurarPanel4();
        mostrarPanel1();
    }

    private void addComboBoxActionListener() {
        if (comboBoxHorarios != null) {
            comboBoxHorarios.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Código del listener
                }
            });
        } else {
            System.out.println("Error: comboBoxHorarios no ha sido inicializado.");
        }
    }

    private void configurarPanel1() {
        panel1 = new JPanel();
        panel1.setLayout(null);
        panel1.setBackground(new Color(114, 206, 206));

        JLabel panelAdmin = new JLabel("Panel Administrador");
        panelAdmin.setFont(new Font("Arial", Font.BOLD, 20));
        panelAdmin.setBounds(305, 10, 400, 30);
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

        JButton botonLogin = new JButton("Ingresar");
        botonLogin.setBounds(300, 160, 150, 30);
        botonLogin.setBackground(new Color(253, 221, 175));
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
        crearRecorrido.setBounds(80, 50, 570, 300);
        crearRecorrido.setBackground(new Color(152, 172, 185));
        panel2.add(crearRecorrido);

        agregarLabel(crearRecorrido);
        agregarHorariosCrear(crearRecorrido, horarios);
        agregarFechaCrear(crearRecorrido);
        agregarOrigenCrear(crearRecorrido);
        agregarDestinoCrear(crearRecorrido);
        agregarBusesCrear(crearRecorrido, buses);
        configurarBotonesOpciones(crearRecorrido);

        Opciones = new JPanel();
        Opciones.setLayout(null);
        Opciones.setBounds(80, 370, 570, 250);
        Opciones.setBackground(new Color(152, 172, 185));
        panel2.add(Opciones);

        JLabel labelRegistrarCliente = new JLabel("Administración");
        labelRegistrarCliente.setFont(new Font("Arial", Font.BOLD, 15));
        labelRegistrarCliente.setBounds(10, 10, 200, 30);
        Opciones.add(labelRegistrarCliente);

        JLabel origen = new JLabel("Origen");
        origen.setFont(new Font("Arial", Font.BOLD, 13));
        origen.setBounds(430, 50, 200, 30);
        Opciones.add(origen);
        JLabel destino = new JLabel("Destino");
        destino.setFont(new Font("Arial", Font.BOLD, 13));
        destino.setBounds(430, 100, 200, 30);
        Opciones.add(destino);

        botonRegistrarPasaje = new JButton("Ver recorridos");
        botonRegistrarPasaje.setBounds(10, 180, 250, 30);
        botonRegistrarPasaje.setBackground(new Color(253, 221, 175));
        botonRegistrarPasaje.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarRutas();
            }
        });
        Opciones.add(botonRegistrarPasaje);

        agregarFecha(Opciones);
        agregarOrigen(Opciones);
        agregarDestino(Opciones);

        // Inicialización de comboBoxHorarios en configurarPanel2
        comboBoxHorarios = new JComboBox<>(horarios.toArray(new LocalTime[0]));
        comboBoxHorarios.setBounds(10, 50, 200, 30);
        Opciones.add(comboBoxHorarios);

        addComboBoxActionListener();

        JButton botonVolverPanel2 = new JButton("Volver");
        botonVolverPanel2.setFont(new Font("Arial", Font.BOLD, 20));
        botonVolverPanel2.setBounds(288, 700, 200, 30);
        botonVolverPanel2.setBackground(new Color(255, 76, 76));
        botonVolverPanel2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPanel1();
            }
        });
        panel2.add(botonVolverPanel2);

        add(panel2, "Panel2");
    }

    private void buscarRutas() {
        Ubicaciones desdeUbicacion = (Ubicaciones) comboBoxOrigen.getSelectedItem();
        Ubicaciones hastaUbicacion = (Ubicaciones) comboBoxDestino.getSelectedItem();
        Date fechaSeleccionada = dateChooser.getDate();

        if (desdeUbicacion == hastaUbicacion) {
            JOptionPane.showMessageDialog(this, "Elecciones iguales, por favor cambiar.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            rutas = baseDeDatos.obtenerRutas(desdeUbicacion, hastaUbicacion, fechaSeleccionada);
            if (!rutas.isEmpty()) {
                mostrarRutas(rutas);
                mostrarPanel3();
            } else {
                JOptionPane.showMessageDialog(this, "No se encontraron rutas para la selección.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void configurarPanel3() {
        panel3 = new JPanel();
        panel3.setLayout(null);
        panel3.setBackground(new Color(173, 216, 230));

        JLabel labelDetalle = new JLabel("Rutas Disponibles", SwingConstants.CENTER);
        labelDetalle.setFont(new Font("Arial", Font.BOLD, 19));
        labelDetalle.setOpaque(true);
        labelDetalle.setBackground(new Color(54, 107, 143));
        labelDetalle.setForeground(Color.WHITE);
        labelDetalle.setBounds(100, 10, 600, 30);
        panel3.add(labelDetalle);

        tablaRutas = new JTable();
        scrollPaneRutas = new JScrollPane(tablaRutas);
        scrollPaneRutas.setBounds(100, 50, 600, 500);
        panel3.add(scrollPaneRutas);

        botonInforme = new JButton("Ver informe ruta");
        botonInforme.setBounds(100, 560, 200, 30);
        botonInforme.setBackground(new Color(124, 232, 153));
        botonInforme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                descargarInforme();
            }
        });
        panel3.add(botonInforme);

        botonAsientos = new JButton("Quitar reserva asiento");
        botonAsientos.setBounds(500, 560, 200, 30);
        botonAsientos.setBackground(new Color(255, 76, 76));
        botonAsientos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tablaRutas.getSelectedRow();
                if (filaSeleccionada != -1) {
                    rutaSeleccionada = rutas.get(filaSeleccionada);
                    if (rutaSeleccionada != null) {
                        mostrarPanel4();
                    } else {
                        JOptionPane.showMessageDialog(panel3, "La ruta seleccionada no es válida.", "Error de selección", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(panel3, "Seleccione una ruta para ver los asientos.", "Sin selección", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        panel3.add(botonAsientos);

        JButton botonVolverPanel3 = new JButton("Volver");
        botonVolverPanel3.setFont(new Font("Arial", Font.BOLD, 20));
        botonVolverPanel3.setBounds(288, 700, 200, 30);
        botonVolverPanel3.setBackground(new Color(255, 76, 76));
        botonVolverPanel3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPanel2();
            }
        });
        panel3.add(botonVolverPanel3);

        add(panel3, "Panel3");
    }

    private void configurarPanel4() {
        panel4 = new JPanel();
        panel4.setLayout(null);
        panel4.setBackground(new Color(173, 216, 230));

        JLabel labelDetalle = new JLabel("Asientos Disponibles", SwingConstants.CENTER);
        labelDetalle.setFont(new Font("Arial", Font.BOLD, 19));
        labelDetalle.setOpaque(true);
        labelDetalle.setBackground(new Color(54, 107, 143));
        labelDetalle.setForeground(Color.WHITE);
        labelDetalle.setBounds(100, 10, 600, 30);
        panel4.add(labelDetalle);

        JLabel elegirAsiento = new JLabel("Presione el asiento que desea eliminar", SwingConstants.CENTER);
        elegirAsiento.setFont(new Font("Arial", Font.BOLD, 19));
        elegirAsiento.setBounds(100, 670, 600, 30);
        panel4.add(elegirAsiento);

        JButton botonVolverPanel4 = new JButton("Volver");
        botonVolverPanel4.setFont(new Font("Arial", Font.BOLD, 20));
        botonVolverPanel4.setBounds(288, 700, 200, 30);
        botonVolverPanel4.setBackground(new Color(255, 76, 76));
        botonVolverPanel4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPanel3(); // Regresar a la vista de rutas
            }
        });
        panel4.add(botonVolverPanel4);

        add(panel4, "Panel4");
    }

    public void quitarReservaAsiento(Asiento asiento) {
        asiento.setEstado(true); // O el método apropiado para marcar el asiento como no reservado
        JOptionPane.showMessageDialog(null, "Reserva del asiento " + asiento.getNumero() + " eliminada.", "Reserva Eliminada", JOptionPane.INFORMATION_MESSAGE);
        actualizarVistaAsientos(); // Refrescar la vista de los asientos después de quitar la reserva
    }

    private void actualizarVistaAsientos() {
        if (rutaSeleccionada != null) {
            System.out.println("Mostrando asientos para la ruta: " + rutaSeleccionada); // Depuración
            List<Asiento> asientos = rutaSeleccionada.getBus().getAsientos();
            for (Asiento asiento : asientos) {
                System.out.println("Asiento: " + asiento); // Depuración
            }
            panel4.removeAll(); // Limpiar el panel antes de agregar nuevos componentes

            // Reconfigurar la vista de asientos
            Asientos asientosPanel = new Asientos(asientos, rutaSeleccionada.getBus(), this);
            asientosPanel.setBounds(100, 50, 600, 600);
            asientosPanel.setBackground(new Color(173, 216, 230));
            panel4.add(asientosPanel);

            // Volver a agregar los botones y etiquetas adicionales
            JLabel labelDetalle = new JLabel("Asientos Disponibles", SwingConstants.CENTER);
            labelDetalle.setFont(new Font("Arial", Font.BOLD, 19));
            labelDetalle.setOpaque(true);
            labelDetalle.setBackground(new Color(54, 107, 143));
            labelDetalle.setForeground(Color.WHITE);
            labelDetalle.setBounds(100, 10, 600, 30);
            panel4.add(labelDetalle);

            JLabel elegirAsiento = new JLabel("Presione el asiento que desea eliminar", SwingConstants.CENTER);
            elegirAsiento.setFont(new Font("Arial", Font.BOLD, 19));
            elegirAsiento.setBounds(100, 670, 600, 30);
            panel4.add(elegirAsiento);

            JButton botonVolverPanel4 = new JButton("Volver");
            botonVolverPanel4.setFont(new Font("Arial", Font.BOLD, 20));
            botonVolverPanel4.setBounds(288, 700, 200, 30);
            botonVolverPanel4.setBackground(new Color(255, 76, 76));
            botonVolverPanel4.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mostrarPanel3(); // Regresar a la vista de rutas
                }
            });
            panel4.add(botonVolverPanel4);

            panel4.revalidate();
            panel4.repaint();
        } else {
            System.out.println("No hay ruta seleccionada para mostrar asientos.");
        }
        System.out.println("Dimensiones del panel4: " + panel4.getWidth() + "x" + panel4.getHeight());
    }

    private void descargarInforme() {
        int filaSeleccionada = tablaRutas.getSelectedRow();
        if (filaSeleccionada != -1) { // Asegúrate de que hay una fila seleccionada
            Ruta rutaSeleccionada = rutas.get(filaSeleccionada); // Accede a la ruta seleccionada
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss"); // Formato de fecha y hora
            String horaInfore = sdf.format(new Date()); // Obtén la fecha y hora actual

            // Construye la ruta del archivo usando la ruta específica en tu sistema, incluyendo la fecha y hora
            String nombreArchivo = "informeruta\\InformeDeRuta_" + rutaSeleccionada.getOrigen() + "_to_" + rutaSeleccionada.getDestino() + "_" + horaInfore + ".txt";

            try {
                InformeDeRuta informe = new InformeDeRuta(rutaSeleccionada, nombreArchivo);
                informe.generarInforme();
                JOptionPane.showMessageDialog(this, "Informe generado correctamente" , "Informe Generado", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al generar el informe" , "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor seleccione una ruta de la tabla.", "Error", JOptionPane.WARNING_MESSAGE);
        }
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

        JLabel origen = new JLabel("Origen");
        origen.setFont(new Font("Arial", Font.BOLD, 13));
        origen.setBounds(430, 50, 200, 30);
        panel.add(origen);
        JLabel destino = new JLabel("Destino");
        destino.setFont(new Font("Arial", Font.BOLD, 13));
        destino.setBounds(430, 100, 200, 30);
        panel.add(destino);
    }

    private void agregarFecha(JPanel panel) {
        dateChooser = new JDateChooser();
        dateChooser.setBounds(10, 50, 200, 30);
        dateChooser.setMinSelectableDate(new Date());
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

    private void configurarBotonesOpciones(JPanel panel) {
        botonCrearRecorrido = new JButton("Crear Recorrido");
        botonCrearRecorrido.setBounds(10, 240, 250, 30);
        botonCrearRecorrido.setBackground(new Color(124, 232, 153));
        botonCrearRecorrido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearRecorrido();
            }
        });
        panel.add(botonCrearRecorrido);
    }

    private void agregarHorariosCrear(JPanel panel, List<LocalTime> horarios) {
        comboBoxHorariosCrear = new JComboBox<>(horarios.toArray(new LocalTime[0]));
        comboBoxHorariosCrear.setBounds(10, 50, 200, 30);
        panel.add(comboBoxHorariosCrear);
    }

    private void agregarFechaCrear(JPanel panel) {
        dateChooserCrear = new JDateChooser();
        dateChooserCrear.setBounds(10, 100, 200, 30);
        dateChooserCrear.setMinSelectableDate(new Date());  // Deshabilitar fechas pasadas
        panel.add(dateChooserCrear);
    }

    private void agregarOrigenCrear(JPanel panel) {
        comboBoxOrigenCrear = new JComboBox<>(Ubicaciones.values());
        comboBoxOrigenCrear.setBounds(220, 50, 200, 30);
        panel.add(comboBoxOrigenCrear);
    }

    private void agregarDestinoCrear(JPanel panel) {
        comboBoxDestinoCrear = new JComboBox<>(Ubicaciones.values());
        comboBoxDestinoCrear.setBounds(220, 100, 200, 30);
        panel.add(comboBoxDestinoCrear);
    }

    private void agregarBusesCrear(JPanel panel, List<Bus> buses) {
        String[] idsDeBuses = new String[buses.size()];
        for (int i = 0; i < buses.size(); i++) {
            idsDeBuses[i] = buses.get(i).getId();
        }

        comboBoxBusesCrear = new JComboBox<>(idsDeBuses);
        comboBoxBusesCrear.setBounds(10, 150, 200, 30);
        panel.add(comboBoxBusesCrear);
    }


    public LocalTime getHorarioSeleccionado() {
        return (LocalTime) comboBoxHorarios.getSelectedItem();
    }

    public void crearRecorrido() {
        LocalTime horarioSeleccionado = (LocalTime) comboBoxHorariosCrear.getSelectedItem();
        Ubicaciones origenSeleccionado = (Ubicaciones) comboBoxOrigenCrear.getSelectedItem();
        Ubicaciones destinoSeleccionado = (Ubicaciones) comboBoxDestinoCrear.getSelectedItem();
        String busSeleccionadoId = (String) comboBoxBusesCrear.getSelectedItem();
        Bus busSeleccionado = baseDeDatos.getBusPorId(busSeleccionadoId);
        Date fechaSeleccionada = dateChooserCrear.getDate();

        if (origenSeleccionado == destinoSeleccionado) {
            JOptionPane.showMessageDialog(this, "El origen y destino no pueden ser iguales.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (horarioSeleccionado != null && origenSeleccionado != null && destinoSeleccionado != null && busSeleccionado != null && fechaSeleccionada != null) {
            int Precio = RutaFactory.calcularPrecioBase(origenSeleccionado, destinoSeleccionado);
            Ruta nuevaRuta = new Ruta(origenSeleccionado, destinoSeleccionado, fechaSeleccionada, horarioSeleccionado, busSeleccionado, Precio);
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
        panelActual = ("Panel3");
    }

    private void mostrarPanel4() {
        actualizarVistaAsientos(); // Asegúrate de actualizar la vista de asientos
        CardLayout cl = (CardLayout) getLayout();
        cl.show(this, "Panel4");
    }
}
