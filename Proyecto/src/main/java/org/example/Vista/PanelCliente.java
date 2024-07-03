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

    public class PanelCliente extends JPanel {
        private JPanel desde, hasta, dia;
        private JComboBox<Ubicaciones> comboBoxDesde, comboBoxHasta;
        private JDateChooser dateChooser;
        private JPanel panel1, panel2, panel3, panel4;
        private BaseDeDatos baseDeDatos;
        private JTable tablaRutas;
        private JScrollPane scrollPaneRutas;
        private String panelActual;
        private Ruta rutaSeleccionada;
        private List<Ruta> rutas;
        private JLabel precioLabel, TipoAsientoLabel;
        private int precio;

        // Campos para el formulario de cliente
        private JTextField nombreField;
        private JTextField apellidoField;
        private JTextField rutField;
        private JTextField emailField;
        private JTextField tarjetaField;
        private JTextField caducidadField;
        private JTextField cvvField;



        private JButton guardarButton;
        private Asiento asientoSeleccionado;
        private JButton botonConfirmarReserva;
        private JButton botonVolver;

        public PanelCliente() {
            baseDeDatos = BaseDeDatos.getInstance(); // Correcto: Usa el método Singleton para obtener la instancia
            configurarPanel();
            agregarPanel1();
            agregarPanel2();
            agregarPanel3();
            agregarPanel4();
            mostrarPanel1();
        }

        private void configurarPanel() {
            setBackground(new Color(64, 164, 107));
            setPreferredSize(new Dimension(600, 900));
            setLayout(new CardLayout());
            panelActual = "Panel1"; // Inicializa el estado actual del panel
        }

        private void agregarPanel1() {
            panel1 = new JPanel();
            panel1.setLayout(null);
            panel1.setBackground(new Color(189, 105, 105));

            JLabel pasaje = new JLabel("Panel Cliente");
            pasaje.setFont(new Font("Arial", Font.BOLD, 20));
            pasaje.setBounds(335, 10, 500, 30);
            panel1.add(pasaje);

            agregarPanelDesde();
            agregarPanelHasta();
            agregarPanelDia();

            agregarBotonItinerario(panel1);

            add(panel1, "Panel1");
        }

        private void agregarPanelDesde() {
            desde = new JPanel();
            desde.setLayout(null);
            desde.setBounds(15, 50, 570, 150);
            desde.setBackground(new Color(157, 218, 184));
            panel1.add(desde);

            JLabel labelDesde = new JLabel("¿Desde donde viajas?");
            labelDesde.setFont(new Font("Arial", Font.BOLD, 15));
            labelDesde.setBounds(10, 10, 200, 30);
            desde.add(labelDesde);

            comboBoxDesde = new JComboBox<>(Ubicaciones.values());
            comboBoxDesde.setBounds(10, 50, 200, 30);
            desde.add(comboBoxDesde);
        }

        private void agregarPanelHasta() {
            hasta = new JPanel();
            hasta.setLayout(null);
            hasta.setBounds(15, 210, 570, 150);
            hasta.setBackground(new Color(225, 122, 122));
            panel1.add(hasta);

            JLabel labelHasta = new JLabel("¿Hacia donde vas?");
            labelHasta.setFont(new Font("Arial", Font.BOLD, 15));
            labelHasta.setBounds(10, 10, 200, 30);
            hasta.add(labelHasta);

            comboBoxHasta = new JComboBox<>(Ubicaciones.values());
            comboBoxHasta.setBounds(10, 50, 200, 30);
            hasta.add(comboBoxHasta);
        }

        private void agregarPanelDia() {
            dia = new JPanel();
            dia.setLayout(null);
            dia.setBounds(15, 370, 570, 300);
            dia.setBackground(new Color(162, 113, 194));
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

        public Date getFechaSeleccionada() {
            return dateChooser.getDate();
        }

        private void agregarPanel2() {
            panel2 = new JPanel();
            panel2.setLayout(null);
            panel2.setBackground(new Color(189, 105, 105));

            JLabel labelRutas = new JLabel("Rutas Disponibles", SwingConstants.CENTER);
            labelRutas.setFont(new Font("Arial", Font.BOLD, 20));
            labelRutas.setBounds(100, 10, 600, 30);
            panel2.add(labelRutas);

            tablaRutas = new JTable();
            scrollPaneRutas = new JScrollPane(tablaRutas);
            scrollPaneRutas.setBounds(100, 50, 600, 600);
            panel2.add(scrollPaneRutas);

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
            botonVolverPanel3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mostrarPanel2();
                }
            });
            panel3.add(botonVolverPanel3);

            add(panel3, "Panel3");
        }

        private void agregarPanel4() {
            panel4 = new JPanel();
            panel4.setLayout(null);
            panel4.setBackground(new Color(173, 216, 230));

            JLabel labelReserva = new JLabel("Reserva de Asientos", SwingConstants.CENTER);
            labelReserva.setFont(new Font("Arial", Font.BOLD, 19));
            labelReserva.setOpaque(true);
            labelReserva.setBackground(new Color(0, 0, 255));
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
            botonConfirmarReserva.setBounds(238, 650, 300, 40); // Ajustar el ancho y la altura del botón
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
            botonVolverPanel4.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mostrarPanel3();
                }
            });
            panel4.add(botonVolverPanel4);

            add(panel4, "Panel4");
        }

        public void setAsientoSeleccionado(Asiento asiento) {
            this.asientoSeleccionado = asiento;
            calcularPrecio(asiento);
            actualizarEtiquetas();
        }

        private void actualizarEtiquetas() {
            if (asientoSeleccionado != null) {
                TipoAsientoLabel.setText("Asiento: " + asientoSeleccionado.getCategoria());
            }
        }

        private void calcularPrecio(Asiento asiento) {
            if (rutaSeleccionada != null) {
                int precioBase = rutaSeleccionada.getPrecio();
                int precioAsiento = new RutaFactory(null).calcularPrecioAsiento(asiento.getCategoria());
                precio = precioBase + precioAsiento;
                precioLabel.setText("Precio: $" + precio);
            }
        }


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

        private boolean guardarCliente() {
            String nombre = nombreField.getText();
            String apellido = apellidoField.getText();
            String rut = rutField.getText();
            String email = emailField.getText();
            String tarjeta = tarjetaField.getText();
            String caducidad = caducidadField.getText();
            String cvv = cvvField.getText();

            if (validarDatosCliente(nombre, apellido, rut, email, tarjeta, caducidad, cvv)) {
                Cliente cliente = new Cliente(nombre, apellido, rut, email);
                Pasaje pasaje = new Pasaje(rutaSeleccionada, cliente, asientoSeleccionado, "pasajes\\pasaje_" + cliente.getNombre() + "_" + cliente.getApellido() + ".txt");
                pasaje.generarInforme();
                return true;
            } else {
                return false;
            }
        }
        private void confirmarReserva() {
            if (asientoSeleccionado != null) {
                asientoSeleccionado.setEstado(false);
                if (guardarCliente()) {
                    JOptionPane.showMessageDialog(panel4, "Reserva confirmada.");
                    mostrarPanel3();
                }
            } else {
                JOptionPane.showMessageDialog(panel4, "No se ha seleccionado ningún asiento.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void agregarBotonItinerario(JPanel panel) {
            JButton itinerario = new JButton("Revisar Itinerario");
            itinerario.setFont(new Font("Arial", Font.BOLD, 20));
            itinerario.setBounds(288, 700, 200, 30);
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

        private void agregarBotonVolver(JPanel panel) {
            botonVolver = new JButton("Volver");
            botonVolver.setFont(new Font("Arial", Font.BOLD, 20));
            botonVolver.setBounds(288, 700, 200, 30);
            panel.add(botonVolver);

            botonVolver.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (panelActual.equals("Panel4")) {
                        mostrarPanel3();
                    } else if (panelActual.equals("Panel3")) {
                        mostrarPanel2();
                    } else if (panelActual.equals("Panel2")) {
                        mostrarPanel1();
                    }
                }
            });
        }

        private void mostrarPanel1() {
            CardLayout cl = (CardLayout) getLayout();
            cl.show(this, "Panel1");
            panelActual = "Panel1";
        }

        private void mostrarPanel2() {
            actualizarTablaRutas();
            CardLayout cl = (CardLayout) getLayout();
            cl.show(this, "Panel2");
            panelActual = "Panel2";
        }

        private void actualizarTablaRutas() {
            // Asumimos que tienes métodos para obtener las ubicaciones y la fecha actual seleccionada por el usuario
            Ubicaciones origen = getUbicacionDesde(); // Método que obtiene la ubicación de origen seleccionada por el usuario
            Ubicaciones destino = getUbicacionHasta(); // Método que obtiene la ubicación de destino seleccionada por el usuario
            Date fecha = getFechaSeleccionada(); // Método que obtiene la fecha seleccionada por el usuario

            // Verifica que origen, destino y fecha no sean nulos antes de hacer la llamada
            if (origen != null && destino != null && fecha != null) {
                List<Ruta> rutas = baseDeDatos.obtenerRutas(origen, destino, fecha);
                mostrarRutas(rutas);
            } else {
                // Manejo de caso donde alguno de los valores necesarios es nulo
                JOptionPane.showMessageDialog(this, "Debe seleccionar origen, destino y fecha para actualizar las rutas.");
            }
        }

        private void mostrarPanel3() {
            if (rutaSeleccionada != null) {
                List<Asiento> asientos = rutaSeleccionada.getBus().getAsientos();
                Bus bus = rutaSeleccionada.getBus(); // Obtener el objeto Bus
                Asientos asientosPanel = new Asientos(asientos, bus, this); // Pasar la lista de asientos y el objeto Bus
                panel3.removeAll();
                asientosPanel.setBounds(100, 50, 600, 600);
                asientosPanel.setBackground(new Color(173, 216, 230));
                panel3.add(asientosPanel);

                JLabel labelDetalle = new JLabel("Asientos Disponibles", SwingConstants.CENTER);
                labelDetalle.setFont(new Font("Arial", Font.BOLD, 19));
                labelDetalle.setOpaque(true);
                labelDetalle.setBackground(new Color(0, 0, 255));
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

        public void mostrarPanel4() {
            CardLayout cl = (CardLayout) getLayout();
            cl.show(this, "Panel4");
            panelActual = "Panel4";
        }

        public Ubicaciones getUbicacionDesde() {
            return (Ubicaciones) comboBoxDesde.getSelectedItem();
        }

        public Ubicaciones getUbicacionHasta() {
            return (Ubicaciones) comboBoxHasta.getSelectedItem();
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
    }
