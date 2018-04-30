/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dto;

import co.com.runt.sagir.entities.ConfArcCar;
import java.util.Date;

/**
 *
 * @author dsalamanca
 */
public class ArcCarDTO {
    
    private Long numreg;
    private Integer tipoArc;
    private Integer codestado;
    private String nomarc;
    private String deserr;
    private String nomot;
    private String desreg;
    private Date fecProceso;
    private Integer codCarga;
    private String identificador;
    private String observacion;
    private Integer codCargaError;
    private ConfArcCar codProceso;

    public Long getNumreg() {
        return numreg;
    }

    public void setNumreg(Long numreg) {
        this.numreg = numreg;
    }

    public Integer getTipoArc() {
        return tipoArc;
    }

    public void setTipoArc(Integer tipoArc) {
        this.tipoArc = tipoArc;
    }

    public Integer getCodestado() {
        return codestado;
    }

    public void setCodestado(Integer codestado) {
        this.codestado = codestado;
    }

    public String getNomarc() {
        return nomarc;
    }

    public void setNomarc(String nomarc) {
        this.nomarc = nomarc;
    }

    public String getDeserr() {
        return deserr;
    }

    public void setDeserr(String deserr) {
        this.deserr = deserr;
    }

    public String getNomot() {
        return nomot;
    }

    public void setNomot(String nomot) {
        this.nomot = nomot;
    }

    public String getDesreg() {
        return desreg;
    }

    public void setDesreg(String desreg) {
        this.desreg = desreg;
    }

    public Date getFecProceso() {
        return fecProceso;
    }

    public void setFecProceso(Date fecProceso) {
        this.fecProceso = fecProceso;
    }

    public Integer getCodCarga() {
        return codCarga;
    }

    public void setCodCarga(Integer codCarga) {
        this.codCarga = codCarga;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Integer getCodCargaError() {
        return codCargaError;
    }

    public void setCodCargaError(Integer codCargaError) {
        this.codCargaError = codCargaError;
    }

    public ConfArcCar getCodProceso() {
        return codProceso;
    }

    public void setCodProceso(ConfArcCar codProceso) {
        this.codProceso = codProceso;
    }
    
    
    
}
