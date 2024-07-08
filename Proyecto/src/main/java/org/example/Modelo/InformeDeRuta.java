package org.example.Modelo;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class InformeDeRuta {
    private Ruta ruta;
    private Bus bus;
    private String nombreArchivo;

    // Constructor existente para rutas
    public InformeDeRuta(Ruta ruta, String nombreArchivo) {
        this.ruta = ruta;
        this.nombreArchivo = nombreArchivo;
    }

    public void generarInforme() throws IOException {
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (ruta != null) {
                generarInformeRuta(writer, dateFormat);
            } else if (bus != null) {
                generarInformeBus(writer);
            }
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo de informe: " + e.getMessage());
            throw e;
        }
    }

    private void generarInformeRuta(FileWriter writer, SimpleDateFormat dateFormat) throws IOException {
        writer.write("**Informe de Ruta**\n\n");
        writer.write("Origen: " + ruta.getOrigen().toString() + "\n");
        writer.write("Destino: " + ruta.getDestino().toString() + "\n");
        writer.write("Fecha: " + dateFormat.format(ruta.getFecha()) + "\n");
        writer.write("Hora: " + ruta.getHora().toString() + "\n");
        writer.write("Bus: " + ruta.getBus().getId() + "\n\n");
        generarInformeAsientos(writer, ruta.getBus());
    }

    private void generarInformeBus(FileWriter writer) throws IOException {
        writer.write("**Informe de Asientos para el Bus ID: " + bus.getId() + "**\n\n");
        generarInformeAsientos(writer, bus);
    }

    private void generarInformeAsientos(FileWriter writer, Bus bus) throws IOException {
        writer.write("Asientos y Pasajeros:\n");
        for (Asiento asiento : bus.getAsientos()) {
            writer.write("Asiento: " + asiento.getNumero() + " - " + asiento.getCategoria() + "\n");
            if (asiento.getCliente() != null) {
                Cliente cliente = asiento.getCliente();
                writer.write("  Cliente: " + cliente.getNombre() + " " + cliente.getApellido() + "\n");
                writer.write("  RUT: " + cliente.getRut() + "\n");
                writer.write("  Email: " + cliente.getEmail() + "\n");
            } else {
                writer.write("  Cliente: No reservado\n");
            }
            writer.write("\n");
        }
    }
}
