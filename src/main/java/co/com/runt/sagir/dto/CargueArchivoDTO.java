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
public class CargueArchivoDTO {

    private String idArchivo;
    private String archivo;
    private String nombre;
    private String tipoCargue;
    private String nroTicket;
    private Long idSecretaria;

    public String getNroTicket() {
        return nroTicket;
    }

    public void setNroTicket(String nroTicket) {
        this.nroTicket = nroTicket;
    }

    public Long getIdSecretaria() {
        return idSecretaria;
    }

    public void setIdSecretaria(Long idSecretaria) {
        this.idSecretaria = idSecretaria;
    } 

    public String getTipoCargue() {
        return tipoCargue;
    }

    public void setTipoCargue(String tipoCargue) {
        this.tipoCargue = tipoCargue;
    }
    
    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdArchivo() {
        return idArchivo;
    }

    public void setIdArchivo(String idArchivo) {
        this.idArchivo = idArchivo;
    }

    @Override
    public String toString() {
        return "CargueArchivoDTO{" + "idArchivo=" + idArchivo + ", archivo=" + archivo + ", nombre=" + nombre + '}';
    }

}
