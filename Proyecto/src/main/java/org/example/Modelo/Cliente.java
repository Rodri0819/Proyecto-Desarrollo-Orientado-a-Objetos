package org.example.Modelo;

/**
 * La clase representa un cliente con información personal básica
 * como nombre, apellido, RUT y email.
 * Esta clase se utiliza para almacenar y gestionar la información de los clientes
 * que reservan asientos en los buses.
 */
public class Cliente {
    private String nombre;
    private String apellido;
    private String rut;
    private String email;

    /**
     * Crea una instancia de {@code Cliente} con el nombre, apellido, RUT y email especificados.
     * @param nombre el nombre del cliente.
     * @param apellido el apellido del cliente.
     * @param rut el RUT del cliente.
     * @param email el email del cliente.
     */
    public Cliente(String nombre, String apellido, String rut, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.rut = rut;
        this.email = email;
    }

    /**
     * Obtiene el nombre del cliente.
     * @return el nombre del cliente.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del cliente.
     * @param nombre el nuevo nombre del cliente.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el apellido del cliente.
     * @return el apellido del cliente.
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Establece el apellido del cliente.
     * @param apellido el nuevo apellido del cliente.
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Obtiene el RUT del cliente.
     * @return el RUT del cliente.
     */
    public String getRut() {
        return rut;
    }

    /**
     * Establece el RUT del cliente.
     * @param rut el nuevo RUT del cliente.
     */
    public void setRut(String rut) {
        this.rut = rut;
    }

    /**
     * Obtiene el email del cliente.
     * @return el email del cliente.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el email del cliente.
     * @param email el nuevo email del cliente.
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
