package org.example.Modelo;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * La clase genera informes detallados de una ruta específica
 * de los asientos de un bus en un archivo de texto.
 * Los informes incluyen información sobre el origen, destino, fecha y hora de la ruta,
 * así como los asientos y los pasajeros que los han reservado.
 */
public class InformeDeRuta {
    private Ruta ruta;
    private Bus bus;
    private String nombreArchivo;

    /**
     * Constructor para crear un informe de una ruta específica.
     * @param ruta la ruta de la cual generar el informe.
     * @param nombreArchivo el nombre del archivo donde se generará el informe.
     */
    public InformeDeRuta(Ruta ruta, String nombreArchivo) {
        this.ruta = ruta;
        this.nombreArchivo = nombreArchivo;
    }

    /**
     * Genera el informe y lo guarda en un archivo.
     * El método selecciona generar un informe de la ruta o del bus basado en
     * la información disponible.
     *
     * @throws IOException si ocurre un error al escribir el archivo.
     */
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

    /**
     * Genera el informe detallado de la ruta y escribe la información en el archivo.
     * @param writer el escritor de archivos para el informe.
     * @param dateFormat el formato de fecha para mostrar en el informe.
     * @throws IOException si ocurre un error al escribir el archivo.
     */
    private void generarInformeRuta(FileWriter writer, SimpleDateFormat dateFormat) throws IOException {
        writer.write("**Informe de Ruta**\n\n");
        writer.write("Origen: " + ruta.getOrigen().toString() + "\n");
        writer.write("Destino: " + ruta.getDestino().toString() + "\n");
        writer.write("Fecha: " + dateFormat.format(ruta.getFecha()) + "\n");
        writer.write("Hora: " + ruta.getHora().toString() + "\n");
        writer.write("Bus: " + ruta.getBus().getId() + "\n\n");
        generarInformeAsientos(writer, ruta.getBus());
    }

    /**
     * Genera el informe detallado de los asientos del bus y escribe la información en el archivo.
     * @param writer el escritor de archivos para el informe.
     * @throws IOException si ocurre un error al escribir el archivo.
     */
    private void generarInformeBus(FileWriter writer) throws IOException {
        writer.write("**Informe de Asientos para el Bus ID: " + bus.getId() + "**\n\n");
        generarInformeAsientos(writer, bus);
    }

    /**
     * Genera el informe de los asientos y sus respectivos pasajeros y escribe la información en el archivo.
     * @param writer el escritor de archivos para el informe.
     * @param bus el bus del cual se generará el informe de asientos.
     * @throws IOException si ocurre un error al escribir el archivo.
     */
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
