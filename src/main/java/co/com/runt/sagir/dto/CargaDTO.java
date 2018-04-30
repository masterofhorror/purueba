/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dto;

import co.com.runt.sagir.entities.OrganismosTransitoMigrunt;
import java.util.Date;

/**
 *
 * @author dsalamanca
 */
public class CargaDTO {
    
    private Integer codCarga;
    private OrganismosTransitoMigrunt idSecretaria;
    private Date fechaCarga;
    private String idFolio;
    private Integer codEstado;
    private Integer idBoletin;
    private Long archmigraIdentific;
    private Integer tipoRegistro;

    public Integer getCodCarga() {
        return codCarga;
    }

    public void setCodCarga(Integer codCarga) {
        this.codCarga = codCarga;
    }

    public OrganismosTransitoMigrunt getIdSecretaria() {
        return idSecretaria;
    }

    public void setIdSecretaria(OrganismosTransitoMigrunt idSecretaria) {
        this.idSecretaria = idSecretaria;
    }

    public Date getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(Date fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public String getIdFolio() {
        return idFolio;
    }

    public void setIdFolio(String idFolio) {
        this.idFolio = idFolio;
    }

    public Integer getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(Integer codEstado) {
        this.codEstado = codEstado;
    }

    public Integer getIdBoletin() {
        return idBoletin;
    }

    public void setIdBoletin(Integer idBoletin) {
        this.idBoletin = idBoletin;
    }

    public Long getArchmigraIdentific() {
        return archmigraIdentific;
    }

    public void setArchmigraIdentific(Long archmigraIdentific) {
        this.archmigraIdentific = archmigraIdentific;
    }

    public Integer getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(Integer tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }
    
    
    
}
