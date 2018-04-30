/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dto;

/**
 *
 * @author Hmoreno
 */
public class ArchivosCargueMigracionDTO {

    private byte[] zip;
    private String nombreZip;

    private byte[] propietarios;
    private String nombrePropietarios;

    private byte[] vehiculos;
    private String nombreVehiculos;

    private byte[] tramites;
    private String nombreTramites;

    private byte[] medidasCautelares;
    private String nombreMedidasCautelares;

    private byte[] radicacionesCuenta;
    private String nombreRadicacionesCuenta;

    private byte[] infoAdicionalMedidasCautelares;
    private String nombreInfoAdicionalMedidasCautelares;

    private byte[] prendas;
    private String nombrePrendas;

    private byte[] radicadoMT;
    private String nombreRadicadoMT;

    private String mensaje;

    public byte[] getZip() {
        return zip;
    }

    public void setZip(byte[] zip) {
        this.zip = zip;
    }

    public String getNombreZip() {
        return nombreZip;
    }

    public void setNombreZip(String nombreZip) {
        this.nombreZip = nombreZip;
    }

    public byte[] getPropietarios() {
        return propietarios;
    }

    public void setPropietarios(byte[] propietarios) {
        this.propietarios = propietarios;
    }

    public byte[] getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(byte[] vehiculos) {
        this.vehiculos = vehiculos;
    }

    public byte[] getTramites() {
        return tramites;
    }

    public void setTramites(byte[] tramites) {
        this.tramites = tramites;
    }

    public byte[] getMedidasCautelares() {
        return medidasCautelares;
    }

    public void setMedidasCautelares(byte[] medidasCautelares) {
        this.medidasCautelares = medidasCautelares;
    }

    public byte[] getRadicacionesCuenta() {
        return radicacionesCuenta;
    }

    public void setRadicacionesCuenta(byte[] radicacionesCuenta) {
        this.radicacionesCuenta = radicacionesCuenta;
    }

    public byte[] getInfoAdicionalMedidasCautelares() {
        return infoAdicionalMedidasCautelares;
    }

    public void setInfoAdicionalMedidasCautelares(byte[] infoAdicionalMedidasCautelares) {
        this.infoAdicionalMedidasCautelares = infoAdicionalMedidasCautelares;
    }

    public byte[] getPrendas() {
        return prendas;
    }

    public void setPrendas(byte[] prendas) {
        this.prendas = prendas;
    }

    public byte[] getRadicadoMT() {
        return radicadoMT;
    }

    public void setRadicadoMT(byte[] radicadoMT) {
        this.radicadoMT = radicadoMT;
    }

    public String getNombrePropietarios() {
        return nombrePropietarios;
    }

    public void setNombrePropietarios(String nombrePropietarios) {
        this.nombrePropietarios = nombrePropietarios;
    }

    public String getNombreVehiculos() {
        return nombreVehiculos;
    }

    public void setNombreVehiculos(String nombreVehiculos) {
        this.nombreVehiculos = nombreVehiculos;
    }

    public String getNombreTramites() {
        return nombreTramites;
    }

    public void setNombreTramites(String nombreTramites) {
        this.nombreTramites = nombreTramites;
    }

    public String getNombreMedidasCautelares() {
        return nombreMedidasCautelares;
    }

    public void setNombreMedidasCautelares(String nombreMedidasCautelares) {
        this.nombreMedidasCautelares = nombreMedidasCautelares;
    }

    public String getNombreRadicacionesCuenta() {
        return nombreRadicacionesCuenta;
    }

    public void setNombreRadicacionesCuenta(String nombreRadicacionesCuenta) {
        this.nombreRadicacionesCuenta = nombreRadicacionesCuenta;
    }

    public String getNombreInfoAdicionalMedidasCautelares() {
        return nombreInfoAdicionalMedidasCautelares;
    }

    public void setNombreInfoAdicionalMedidasCautelares(String nombreInfoAdicionalMedidasCautelares) {
        this.nombreInfoAdicionalMedidasCautelares = nombreInfoAdicionalMedidasCautelares;
    }

    public String getNombrePrendas() {
        return nombrePrendas;
    }

    public void setNombrePrendas(String nombrePrendas) {
        this.nombrePrendas = nombrePrendas;
    }

    public String getNombreRadicadoMT() {
        return nombreRadicadoMT;
    }

    public void setNombreRadicadoMT(String nombreRadicadoMT) {
        this.nombreRadicadoMT = nombreRadicadoMT;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void addMensaje(String mensaje) {
        if (mensaje == null) {
            mensaje = "";
        }
        this.mensaje += mensaje + " ";
    }
}
