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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * PanelCliente es un panel que gestiona la interfaz de usuario para la reserva de pasajes por parte de los clientes.
 */
public class PanelCliente extends JPanel {
    private JPanel desde, hasta, dia, ruta;
    private JComboBox<Ubicaciones> comboBoxDesde, comboBoxHasta;
    private JDateChooser dateChooser;
    private JPanel panel1, panel2, panel3, panel4;
    private BaseDeDatos baseDeDatos;
    private JTable tablaRutas;
    private JScrollPane scrollPaneRutas;
    private String panelActual;
    private Ruta rutaSeleccionada;
    private List<Ruta> rutas;
    private JLabel precioLabel, TipoAsientoLabel, origenLabelText, destinoLabelText, semicamaLabelText, saloncamaLabelText;
    private int precio;

    // Campos para el formulario de cliente
    private JTextField nombreField;
    private JTextField apellidoField;
    private JTextField rutField;
    private JTextField emailField;
    private JTextField tarjetaField;
    private JTextField caducidadField;
    private JTextField cvvField;
    private Asiento asientoSeleccionado;
    private JButton botonConfirmarReserva;
    private JButton botonVolver;

    /**
     * Constructor de PanelCliente.
     * Inicializa los componentes del panel y configura los paneles internos.
     */
    public PanelCliente() {
        baseDeDatos = BaseDeDatos.getInstance();
        configurarPanel();
        agregarPanel1();
        agregarPanel2();
        agregarPanel3();
        agregarPanel4();
        mostrarPanel1();

        // Listener para los JComboBox
        comboBoxDesde.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarLabelsRuta();
            }
        });

        comboBoxHasta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarLabelsRuta();
            }
        });
    }

    /**
     * Actualiza las etiquetas de la ruta seleccionada y los precios según las ubicaciones seleccionadas.
     */
    private void actualizarLabelsRuta() {
        Ubicaciones origen = (Ubicaciones) comboBoxDesde.getSelectedItem();
        Ubicaciones destino = (Ubicaciones) comboBoxHasta.getSelectedItem();
        int semiprecio = RutaFactory.calcularPrecioBase(origen, destino) + 2000;
        int salonprecio = RutaFactory.calcularPrecioBase(origen, destino) + 4000;

        if (origen != destino) {
            if (origen != null) {
                origenLabelText.setText(origen.toString());
            }

            if (destino != null) {
                destinoLabelText.setText(destino.toString());
                semicamaLabelText.setText("$" + semiprecio);
                saloncamaLabelText.setText("$" + salonprecio);
            }
        } else {
            origenLabelText.setText(origen.toString());
            destinoLabelText.setText(destino.toString());
            semicamaLabelText.setText("Cambie la ruta");
            saloncamaLabelText.setText("Cambie la ruta");
        }
    }

    /**
     * Configura el panel principal y establece su layout.
     */
    private void configurarPanel() {
        setBackground(new Color(64, 164, 107));
        setPreferredSize(new Dimension(600, 900));
        setLayout(new CardLayout());
        panelActual = "Panel1"; // Inicializa el estado actual del panel
    }

    /**
     * Agrega el primer panel (selección de ubicaciones y fecha) al panel principal.
     */
    private void agregarPanel1() {
        panel1 = new JPanel();
        panel1.setLayout(null);
        panel1.setBackground(new Color(173, 216, 230));

        JLabel pasaje = new JLabel("Panel Cliente", SwingConstants.CENTER);
        pasaje.setFont(new Font("Arial", Font.BOLD, 19));
        pasaje.setOpaque(true);
        pasaje.setBackground(new Color(54, 107, 143));
        pasaje.setForeground(Color.WHITE);
        pasaje.setBounds(100, 10, 600, 30);
        panel1.add(pasaje);

        agregarPanelDesde();
        agregarPanelHasta();
        agregarPanelDia();
        agregarPanelRutaSeleccionada();

        agregarBotonItinerario(panel1);

        add(panel1, "Panel1");
    }

    /**
     * Agrega el subpanel para la selección de la ubicación de origen.
     */
    private void agregarPanelDesde() {
        desde = new JPanel();
        desde.setLayout(null);
        desde.setBounds(15, 50, 370, 150);
        desde.setBackground(new Color(187, 206, 211, 236));
        panel1.add(desde);

        JLabel labelDesde = new JLabel("¿Desde donde viajas?");
        labelDesde.setFont(new Font("Arial", Font.BOLD, 15));
        labelDesde.setBounds(10, 10, 200, 30);
        desde.add(labelDesde);

        comboBoxDesde = new JComboBox<>(Ubicaciones.values());
        comboBoxDesde.setBounds(10, 50, 200, 30);
        desde.add(comboBoxDesde);
    }

    /**
     * Agrega el subpanel para la selección de la ubicación de destino.
     */
    private void agregarPanelHasta() {
        hasta = new JPanel();
        hasta.setLayout(null);
        hasta.setBounds(15, 210, 370, 150);
        hasta.setBackground(new Color(187, 206, 211, 236));
        panel1.add(hasta);

        JLabel labelHasta = new JLabel("¿Hacia donde vas?");
        labelHasta.setFont(new Font("Arial", Font.BOLD, 15));
        labelHasta.setBounds(10, 10, 200, 30);
        hasta.add(labelHasta);

        comboBoxHasta = new JComboBox<>(Ubicaciones.values());
        comboBoxHasta.setBounds(10, 50, 200, 30);
        hasta.add(comboBoxHasta);
    }

    /**
     * Agrega el subpanel para la selección de la fecha del viaje.
     */
    private void agregarPanelDia() {
        dia = new JPanel();
        dia.setLayout(null);
        dia.setBounds(15, 370, 370, 300);
        dia.setBackground(new Color(187, 206, 211, 236));
        panel1.add(dia);

        JLabel labelDia = new JLabel("¿Qué día vas?");
        labelDia.setFont(new Font("Arial", Font.BOLD, 15));
        labelDia.setBounds(10, 10, 200, 30);
        dia.add(labelDia);

        dateChooser = new JDateChooser();
        dateChooser.setBounds(10, 50, 200, 30);
        dateChooser.setMinSelectableDate(new Date());
        dia.add(dateChooser);
    }

    /**
     * Agrega el subpanel para mostrar la ruta seleccionada y los precios.
     */
    private void agregarPanelRutaSeleccionada() {
        ruta = new JPanel();
        ruta.setLayout(null);
        ruta.setBounds(415, 50, 370, 300);
        ruta.setBackground(new Color(187, 206, 211, 236));
        panel1.add(ruta);

        JLabel labelRutaSeleccionada = new JLabel("Ruta seleccionada");
        labelRutaSeleccionada.setFont(new Font("Arial", Font.BOLD, 15));
        labelRutaSeleccionada.setBounds(10, 10, 200, 30);
        ruta.add(labelRutaSeleccionada);

        JLabel origenLabel = new JLabel("Origen: ");
        origenLabel.setFont(new Font("Arial", Font.BOLD, 13));
        origenLabel.setBounds(10, 50, 100, 30);
        ruta.add(origenLabel);

        origenLabelText = new JLabel("No hay selección");
        origenLabelText.setFont(new Font("Arial", Font.PLAIN, 13));
        origenLabelText.setBounds(120, 50, 200, 30);
        ruta.add(origenLabelText);

        JLabel destinoLabel = new JLabel("Destino:");
        destinoLabel.setFont(new Font("Arial", Font.BOLD, 13));
        destinoLabel.setBounds(10, 90, 100, 30);
        ruta.add(destinoLabel);

        destinoLabelText = new JLabel("No hay selección");
        destinoLabelText.setFont(new Font("Arial", Font.PLAIN, 13));
        destinoLabelText.setBounds(120, 90, 200, 30);
        ruta.add(destinoLabelText);

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setBounds(10, 130, 350, 10);
        ruta.add(separator);

        JLabel labelPrecios = new JLabel("Precios");
        labelPrecios.setFont(new Font("Arial", Font.BOLD, 15));
        labelPrecios.setBounds(10, 150, 200, 30);
        ruta.add(labelPrecios);

        JLabel precioSemi = new JLabel("Semi cama:");
        precioSemi.setFont(new Font("Arial", Font.BOLD, 13));
        precioSemi.setBounds(10, 190, 200, 30);
        ruta.add(precioSemi);

        semicamaLabelText = new JLabel("No hay selección");
        semicamaLabelText.setFont(new Font("Arial", Font.PLAIN, 13));
        semicamaLabelText.setBounds(120, 190, 200, 30);
        ruta.add(semicamaLabelText);

        JLabel precioSalon = new JLabel("Salón cama:");
        precioSalon.setFont(new Font("Arial", Font.BOLD, 13));
        precioSalon.setBounds(10, 230, 200, 30);
        ruta.add(precioSalon);

        saloncamaLabelText = new JLabel("No hay selección");
        saloncamaLabelText.setFont(new Font("Arial", Font.PLAIN, 13));
        saloncamaLabelText.setBounds(120, 230, 200, 30);
        ruta.add(saloncamaLabelText);
    }

    /**
     * Obtiene la fecha seleccionada por el usuario.
     *
     * @return la fecha seleccionada.
     */
    public Date getFechaSeleccionada() {
        return dateChooser.getDate();
    }

    /**
     * Agrega el segundo panel (visualización de rutas disponibles) al panel principal.
     */
    private void agregarPanel2() {
        panel2 = new JPanel();
        panel2.setLayout(null);
        panel2.setBackground(new Color(173, 216, 230));

        JLabel labelRutas = new JLabel("Rutas Disponibles", SwingConstants.CENTER);
        labelRutas.setFont(new Font("Arial", Font.BOLD, 19));
        labelRutas.setOpaque(true);
        labelRutas.setBackground(new Color(54, 107, 143));
        labelRutas.setForeground(Color.WHITE);
        labelRutas.setBounds(100, 10, 600, 30);
        panel2.add(labelRutas);

        tablaRutas = new JTable();
        scrollPaneRutas = new JScrollPane(tablaRutas);
        scrollPaneRutas.setBounds(100, 50, 600, 600);
        panel2.add(scrollPaneRutas);

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

        tablaRutas.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = tablaRutas.getSelectedRow();
                    if (fila != -1) {
                        rutaSeleccionada = rutas.get(fila); // Guardar la ruta seleccionada
                        mostrarPanel3();
                    }
                }
            }
        });
    }

    /**
     * Agrega el tercer panel (visualización de asientos disponibles) al panel principal.
     */
    private void agregarPanel3() {
        panel3 = new JPanel();
        panel3.setLayout(null);
        panel3.setBackground(new Color(173, 216, 230));

        JLabel labelDetalle = new JLabel("Asientos Disponibles", SwingConstants.CENTER);
        labelDetalle.setFont(new Font("Arial", Font.BOLD, 19));
        labelDetalle.setOpaque(true);
        labelDetalle.setBackground(new Color(0, 0, 255));
        labelDetalle.setForeground(Color.WHITE);
        labelDetalle.setBounds(100, 10, 600, 30);
        panel3.add(labelDetalle);

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

    /**
     * Agrega el cuarto panel (formulario de reserva de pasajes) al panel principal.
     */
    private void agregarPanel4() {
        panel4 = new JPanel();
        panel4.setLayout(null);
        panel4.setBackground(new Color(173, 216, 230));

        JLabel labelReserva = new JLabel("Reserva de Asientos", SwingConstants.CENTER);
        labelReserva.setFont(new Font("Arial", Font.BOLD, 19));
        labelReserva.setOpaque(true);
        labelReserva.setBackground(new Color(54, 107, 143));
        labelReserva.setForeground(Color.WHITE);
        labelReserva.setBounds(100, 10, 600, 30);
        panel4.add(labelReserva);

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setBounds(50, 70, 100, 30);
        panel4.add(nombreLabel);

        nombreField = new JTextField();
        nombreField.setBounds(150, 70, 200, 30);
        panel4.add(nombreField);

        JLabel apellidoLabel = new JLabel("Apellido:");
        apellidoLabel.setBounds(50, 110, 100, 30);
        panel4.add(apellidoLabel);

        apellidoField = new JTextField();
        apellidoField.setBounds(150, 110, 200, 30);
        panel4.add(apellidoField);

        JLabel rutLabel = new JLabel("RUT:");
        rutLabel.setBounds(50, 150, 100, 30);
        panel4.add(rutLabel);

        rutField = new JTextField();
        rutField.setBounds(150, 150, 200, 30);
        panel4.add(rutField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 190, 100, 30);
        panel4.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(150, 190, 200, 30);
        panel4.add(emailField);

        JLabel tarjetaLabel = new JLabel("N° Tarjeta:");
        tarjetaLabel.setBounds(400, 70, 100, 30);
        panel4.add(tarjetaLabel);

        tarjetaField = new JTextField();
        tarjetaField.setBounds(500, 70, 200, 30);
        panel4.add(tarjetaField);

        JLabel caducidadLabel = new JLabel("Caducidad:");
        caducidadLabel.setBounds(400, 110, 100, 30);
        panel4.add(caducidadLabel);

        caducidadField = new JTextField();
        caducidadField.setBounds(500, 110, 200, 30);
        panel4.add(caducidadField);

        JLabel cvvLabel = new JLabel("CVV:");
        cvvLabel.setBounds(400, 150, 100, 30);
        panel4.add(cvvLabel);

        cvvField = new JTextField();
        cvvField.setBounds(500, 150, 200, 30);
        panel4.add(cvvField);

        precioLabel = new JLabel("Precio: No seleccionado");
        precioLabel.setFont(new Font("Arial", Font.BOLD, 15));
        precioLabel.setBounds(50, 270, 200, 30);
        panel4.add(precioLabel);

        TipoAsientoLabel = new JLabel("Asiento: No seleccionado");
        TipoAsientoLabel.setFont(new Font("Arial", Font.BOLD, 15));
        TipoAsientoLabel.setBounds(50, 300, 200, 30);
        panel4.add(TipoAsientoLabel);

        botonConfirmarReserva = new JButton("Confirmar Reserva");
        botonConfirmarReserva.setFont(new Font("Arial", Font.BOLD, 20));
        botonConfirmarReserva.setBounds(238, 650, 300, 40);
        botonConfirmarReserva.setBackground(new Color(124, 232, 153));
        botonConfirmarReserva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmarReserva();
            }
        });
        panel4.add(botonConfirmarReserva);

        JButton botonVolverPanel4 = new JButton("Volver");
        botonVolverPanel4.setFont(new Font("Arial", Font.BOLD, 20));
        botonVolverPanel4.setBounds(288, 700, 200, 30);
        botonVolverPanel4.setBackground(new Color(255, 76, 76));
        botonVolverPanel4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPanel3();
            }
        });
        panel4.add(botonVolverPanel4);

        add(panel4, "Panel4");
    }

    /**
     * Establece el asiento seleccionado y actualiza el precio y las etiquetas correspondientes.
     *
     * @param asiento el asiento seleccionado.
     */
    public void setAsientoSeleccionado(Asiento asiento) {
        this.asientoSeleccionado = asiento;
        calcularPrecio(asiento);
        actualizarEtiquetas();
    }

    /**
     * Actualiza las etiquetas del tipo de asiento.
     */
    private void actualizarEtiquetas() {
        if (asientoSeleccionado != null) {
            TipoAsientoLabel.setText("Asiento: " + asientoSeleccionado.getCategoria());
        }
    }

    /**
     * Calcula el precio total del pasaje según el asiento seleccionado y la ruta.
     *
     * @param asiento el asiento seleccionado.
     */
    private void calcularPrecio(Asiento asiento) {
        if (rutaSeleccionada != null) {
            int precioBase = rutaSeleccionada.getPrecio();
            int precioAsiento = new RutaFactory(null).calcularPrecioAsiento(asiento.getCategoria());
            precio = precioBase + precioAsiento;
            precioLabel.setText("Precio: $" + precio);
        }
    }

    /**
     * Valida los datos ingresados por el cliente en el formulario.
     *
     * @param nombre     el nombre del cliente.
     * @param apellido   el apellido del cliente.
     * @param rut        el RUT del cliente.
     * @param email      el correo electrónico del cliente.
     * @param tarjeta    el número de tarjeta del cliente.
     * @param caducidad  la fecha de caducidad de la tarjeta.
     * @param cvv        el código CVV de la tarjeta.
     * @return true si todos los datos son válidos, false de lo contrario.
     */
    private boolean validarDatosCliente(String nombre, String apellido, String rut, String email, String tarjeta, String caducidad, String cvv) {
        // Validación de nombre y apellido
        if (nombre.isEmpty() || apellido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre y apellido no pueden estar vacíos.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!nombre.matches("[A-Za-zÁÉÍÓÚáéíóúñÑ ]+") || !apellido.matches("[A-Za-zÁÉÍÓÚáéíóúñÑ ]+")) {
            JOptionPane.showMessageDialog(this, "Nombre y apellido solo deben contener letras y espacios.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validación de RUT
        if (!validarRUT(rut)) {
            JOptionPane.showMessageDialog(this, "RUT inválido.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validación de Email
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            JOptionPane.showMessageDialog(this, "Formato de email inválido.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validación de Número de Tarjeta
        if (!tarjeta.matches("\\d{16}")){
            JOptionPane.showMessageDialog(this, "El Número de Tarjeta debe contener 16 dígitos.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validación de Fecha de Caducidad
        if (!caducidad.matches("\\d{2}/\\d{2}")){
            JOptionPane.showMessageDialog(this, "La Fecha de Caducidad debe ser del formato MM/YY.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        String mes = caducidad.substring(0, 2);
        int mesInt = Integer.parseInt(mes);
        if (mesInt < 1 || mesInt > 12) {
            JOptionPane.showMessageDialog(this, "El mes debe estar entre 01 y 12.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validación de CVV
        if (!cvv.matches("\\d{3}")){
            JOptionPane.showMessageDialog(this, "El CVV debe contener 3 dígitos.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    /**
     * Valida el RUT ingresado.
     *
     * @param rut el RUT ingresado.
     * @return true si el RUT es válido, false de lo contrario.
     */
    private boolean validarRUT(String rut) {
        try {
            rut = rut.toUpperCase();
            rut = rut.replace(".", "").replace("-", "");
            int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

            char dv = rut.charAt(rut.length() - 1);

            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }
            return dv == (char) (s != 0 ? s + 47 : 75);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Confirma la reserva de un pasaje.
     * Valida los datos del cliente y genera un informe del pasaje.
     */
    private void confirmarReserva() {
        if (asientoSeleccionado != null) {
            String nombre = nombreField.getText();
            String apellido = apellidoField.getText();
            String rut = rutField.getText();
            String email = emailField.getText();
            Cliente cliente = new Cliente(nombre, apellido, rut, email);

            if (validarDatosCliente(nombre, apellido, rut, email, tarjetaField.getText(), caducidadField.getText(), cvvField.getText())) {
                asientoSeleccionado.setCliente(cliente); // Asignar el cliente al asiento
                asientoSeleccionado.setEstado(false); // Marcar el asiento como reservado
                Pasaje pasaje = new Pasaje(rutaSeleccionada, cliente, asientoSeleccionado, "pasajes\\pasaje_" + cliente.getNombre() + "_" + cliente.getApellido() + ".txt");
                pasaje.generarInforme();
                JOptionPane.showMessageDialog(panel4, "Reserva confirmada.");
                mostrarPanel3();
            }
        } else {
            JOptionPane.showMessageDialog(panel4, "No se ha seleccionado ningún asiento.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Agrega un botón para revisar el itinerario y busca rutas según las ubicaciones y la fecha seleccionada.
     *
     * @param panel el panel al que se agregará el botón.
     */
    private void agregarBotonItinerario(JPanel panel) {
        JButton itinerario = new JButton("Revisar Itinerario");
        itinerario.setFont(new Font("Arial", Font.BOLD, 20));
        itinerario.setBounds(288, 700, 200, 30);
        itinerario.setBackground(new Color(253, 221, 175));
        panel.add(itinerario);

        itinerario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ubicaciones desdeUbicacion = getUbicacionDesde();
                Ubicaciones hastaUbicacion = getUbicacionHasta();
                Date fechaSeleccionada = getFechaSeleccionada();

                if (desdeUbicacion == hastaUbicacion) {
                    JOptionPane.showMessageDialog(PanelCliente.this, "No puedes viajar hacia donde estás.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    rutas = baseDeDatos.obtenerRutas(desdeUbicacion, hastaUbicacion, fechaSeleccionada);
                    if (!rutas.isEmpty()) {
                        mostrarRutas(rutas);
                        mostrarPanel2();
                    } else {
                        JOptionPane.showMessageDialog(PanelCliente.this, "No se encontraron rutas para la selección.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    /**
     * Muestra el primer panel (selección de ubicaciones y fecha).
     */
    private void mostrarPanel1() {
        CardLayout cl = (CardLayout) getLayout();
        cl.show(this, "Panel1");
        panelActual = "Panel1";
    }

    /**
     * Muestra el segundo panel (visualización de rutas disponibles) y actualiza la tabla de rutas.
     */
    private void mostrarPanel2() {
        actualizarTablaRutas();
        CardLayout cl = (CardLayout) getLayout();
        cl.show(this, "Panel2");
        panelActual = "Panel2";
    }

    /**
     * Actualiza la tabla de rutas disponibles según las ubicaciones y la fecha seleccionada.
     */
    private void actualizarTablaRutas() {
        Ubicaciones origen = getUbicacionDesde();
        Ubicaciones destino = getUbicacionHasta();
        Date fecha = getFechaSeleccionada();

        if (origen != null && destino != null && fecha != null) {
            List<Ruta> rutas = baseDeDatos.obtenerRutas(origen, destino, fecha);
            mostrarRutas(rutas);
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar origen, destino y fecha para actualizar las rutas.");
        }
    }

    /**
     * Muestra el tercer panel (visualización de asientos disponibles) y actualiza los asientos disponibles según la ruta seleccionada.
     */
    private void mostrarPanel3() {
        if (rutaSeleccionada != null) {
            List<Asiento> asientos = rutaSeleccionada.getBus().getAsientos();
            Bus bus = rutaSeleccionada.getBus();
            Asientos asientosPanel = new Asientos(asientos, bus, this);
            panel3.removeAll();
            asientosPanel.setBounds(100, 50, 600, 600);
            asientosPanel.setBackground(new Color(173, 216, 230));
            panel3.add(asientosPanel);

            JLabel labelDetalle = new JLabel("Asientos Disponibles", SwingConstants.CENTER);
            labelDetalle.setFont(new Font("Arial", Font.BOLD, 19));
            labelDetalle.setOpaque(true);
            labelDetalle.setBackground(new Color(54, 107, 143));
            labelDetalle.setForeground(Color.WHITE);
            labelDetalle.setBounds(100, 10, 600, 30);
            panel3.add(labelDetalle);

            JLabel semi = new JLabel("Semi cama", SwingConstants.CENTER);
            semi.setFont(new Font("Arial", Font.BOLD, 16));
            semi.setOpaque(true);
            semi.setBackground(new Color(29, 206, 79));
            semi.setForeground(Color.WHITE);
            semi.setBounds(170, 680, 100, 30);
            panel3.add(semi);
            JLabel salon = new JLabel("Salón cama", SwingConstants.CENTER);
            salon.setFont(new Font("Arial", Font.BOLD, 16));
            salon.setOpaque(true);
            salon.setBackground(new Color(35, 154, 100));
            salon.setForeground(Color.WHITE);
            salon.setBounds(170, 710, 100, 30);
            panel3.add(salon);

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

            revalidate();
            repaint();
        }

        CardLayout cl = (CardLayout) getLayout();
        cl.show(this, "Panel3");
        panelActual = "Panel3";
    }

    /**
     * Muestra el cuarto panel (formulario de reserva de pasajes).
     */
    public void mostrarPanel4() {
        CardLayout cl = (CardLayout) getLayout();
        cl.show(this, "Panel4");
        panelActual = "Panel4";
    }

    /**
     * Obtiene la ubicación de origen seleccionada.
     *
     * @return la ubicación de origen seleccionada.
     */
    public Ubicaciones getUbicacionDesde() {
        return (Ubicaciones) comboBoxDesde.getSelectedItem();
    }

    /**
     * Obtiene la ubicación de destino seleccionada.
     *
     * @return la ubicación de destino seleccionada.
     */
    public Ubicaciones getUbicacionHasta() {
        return (Ubicaciones) comboBoxHasta.getSelectedItem();
    }

    /**
     * Muestra las rutas disponibles en una tabla.
     *
     * @param rutas la lista de rutas disponibles.
     */
    private void mostrarRutas(List<Ruta> rutas) {
        String[] columnNames = {"Origen", "Destino", "Fecha", "Hora", "Bus"};
        Object[][] data = new Object[rutas.size()][5];
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i < rutas.size(); i++) {
            Ruta ruta = rutas.get(i);
            data[i][0] = ruta.getOrigen();
            data[i][1] = ruta.getDestino();
            data[i][2] = dateFormat.format(ruta.getFecha());
            data[i][3] = ruta.getHoraFormateada();
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
}
