package org.example.Modelo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class Pasaje {
    private Ruta ruta;
    private String nombreArchivo;
    private Cliente cliente;
    private Asiento asientoSeleccionado;
    private String rutaArchivo;

    public Pasaje(Ruta ruta, Cliente cliente, Asiento asientoSeleccionado, String nombreArchivo) {
        this.ruta = ruta;
        this.nombreArchivo = nombreArchivo;
        this.cliente = cliente;
        this.asientoSeleccionado = asientoSeleccionado;
    }

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
    public String getRutaArchivo() {
        return rutaArchivo;
    }
    public void eliminarArchivo() {

        File archivo = new File(rutaArchivo);
        if (archivo.exists()) {
            archivo.delete();
        }
    }
}
