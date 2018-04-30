/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dto;

/**
 *
 * @author dsalamanca
 */
public class ArchivosProcesoMigracionRncDTO {
    
    private byte[] zip;
    private String nombreZip;
    
    private byte[] licencias;
    private String archivoLicencias;
    private String nombreLicencias;

    private byte[] residencias;
    private String archivoResidencias;
    private String nombreResidencias;

    private byte[] personas;
    private String archivoPersonas;
    private String nombrePersonas;

    private byte[] restricciones;
    private String archivoRestricciones;
    private String nombreRestricciones;
    
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

    public byte[] getLicencias() {
        return licencias;
    }

    public void setLicencias(byte[] licencias) {
        this.licencias = licencias;
    }

    public String getNombreLicencias() {
        return nombreLicencias;
    }

    public void setNombreLicencias(String nombreLicencias) {
        this.nombreLicencias = nombreLicencias;
    }

    public byte[] getResidencias() {
        return residencias;
    }

    public void setResidencias(byte[] residencias) {
        this.residencias = residencias;
    }

    public String getNombreResidencias() {
        return nombreResidencias;
    }

    public void setNombreResidencias(String nombreResidencias) {
        this.nombreResidencias = nombreResidencias;
    }

    public byte[] getPersonas() {
        return personas;
    }

    public void setPersonas(byte[] personas) {
        this.personas = personas;
    }

    public String getNombrePersonas() {
        return nombrePersonas;
    }

    public void setNombrePersonas(String nombrePersonas) {
        this.nombrePersonas = nombrePersonas;
    }

    public byte[] getRestricciones() {
        return restricciones;
    }

    public void setRestricciones(byte[] restricciones) {
        this.restricciones = restricciones;
    }

    public String getNombreRestricciones() {
        return nombreRestricciones;
    }

    public void setNombreRestricciones(String nombreRestricciones) {
        this.nombreRestricciones = nombreRestricciones;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getArchivoLicencias() {
        return archivoLicencias;
    }

    public void setArchivoLicencias(String archivoLicencias) {
        this.archivoLicencias = archivoLicencias;
    }

    public String getArchivoResidencias() {
        return archivoResidencias;
    }

    public void setArchivoResidencias(String archivoResidencias) {
        this.archivoResidencias = archivoResidencias;
    }

    public String getArchivoPersonas() {
        return archivoPersonas;
    }

    public void setArchivoPersonas(String archivoPersonas) {
        this.archivoPersonas = archivoPersonas;
    }

    public String getArchivoRestricciones() {
        return archivoRestricciones;
    }

    public void setArchivoRestricciones(String archivoRestricciones) {
        this.archivoRestricciones = archivoRestricciones;
    }
    
    
    
    public void addMensaje(String mensaje) {
        if (mensaje == null) {
            mensaje = "";
        }
        this.mensaje += mensaje + " ";
    }
        
}
