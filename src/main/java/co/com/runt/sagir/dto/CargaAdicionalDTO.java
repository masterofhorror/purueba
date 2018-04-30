/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dto;

import java.util.Date;

/**
 *
 * @author APENA
 */
public class CargaAdicionalDTO {

    private String codCarga;
    private String idSecretaria;
    private Date fechaCarga;
    private String tipoRegistro;
    private String idFolio;
    private String codEstado;
    private String idBoletin;

    public String getCodCarga() {
        return codCarga;
    }

    public void setCodCarga(String codCarga) {
        this.codCarga = codCarga;
    }

    public String getIdSecretaria() {
        return idSecretaria;
    }

    public void setIdSecretaria(String idSecretaria) {
        this.idSecretaria = idSecretaria;
    }

    public Date getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(Date fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public String getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public String getIdFolio() {
        return idFolio;
    }

    public void setIdFolio(String idFolio) {
        this.idFolio = idFolio;
    }

    public String getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(String codEstado) {
        this.codEstado = codEstado;
    }

    public String getIdBoletin() {
        return idBoletin;
    }

    public void setIdBoletin(String idBoletin) {
        this.idBoletin = idBoletin;
    }
    
    
}
