package org.example.Modelo;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * La clase  representa un boleto de viaje que contiene información
 * sobre el cliente, la ruta, y el asiento reservado.
 * Esta clase permite generar un informe del pasaje en un archivo de texto con todos
 * los detalles relevantes del viaje.
 */
public class Pasaje {
    private Ruta ruta;
    private String nombreArchivo;
    private Cliente cliente;
    private Asiento asientoSeleccionado;

    /**
     * Crea una instancia de {@code Pasaje} con la ruta, cliente, asiento seleccionado y nombre del archivo especificados.
     * @param ruta la ruta del viaje.
     * @param cliente el cliente que ha reservado el pasaje.
     * @param asientoSeleccionado el asiento que ha sido reservado.
     * @param nombreArchivo el nombre del archivo donde se generará el informe del pasaje.
     */
    public Pasaje(Ruta ruta, Cliente cliente, Asiento asientoSeleccionado, String nombreArchivo) {
        this.ruta = ruta;
        this.nombreArchivo = nombreArchivo;
        this.cliente = cliente;
        this.asientoSeleccionado = asientoSeleccionado;
    }

    /**
     * Genera un informe del pasaje y lo guarda en un archivo de texto.
     * El informe incluye detalles del cliente, la ruta, y el asiento reservado.
     */
    public void generarInforme() {
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            writer.write("**Pasaje**\n\n");
            writer.write("Cliente: " + cliente.getNombre() + " " + cliente.getApellido() + "\n");
            writer.write("RUT: " + cliente.getRut() + "\n");
            writer.write("Email: " + cliente.getEmail() + "\n\n");
            writer.write("Origen: " + ruta.getOrigen() + "\n");
            writer.write("Destino: " + ruta.getDestino() + "\n");
            writer.write("Fecha: " + dateFormat.format(ruta.getFecha()) + "\n");
            writer.write("Hora: " + ruta.getHora() + "\n");
            writer.write("Bus: " + ruta.getBus().getId() + "\n");
            writer.write("Asiento: " + asientoSeleccionado.getNumero() + " (" + asientoSeleccionado.getCategoria() + ")\n");
            writer.write("Precio Total: " + ruta.getPrecio() + "\n\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
