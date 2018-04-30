/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dto;

import java.util.Date;

/**
 *
 * @author dsalamanca
 */
public class HiAutomotorDTO {
    
    private Integer columna;
    private String datoAnterios;
    private String datoNuevo;
    private String nombreColumna;
    private String usuarioModifica;
    private Date fechaModificacion;
    private String placa;
    private String tipoActo;

    public Integer getColumna() {
        return columna;
    }

    public void setColumna(Integer columna) {
        this.columna = columna;
    }

    public String getDatoAnterios() {
        return datoAnterios;
    }

    public void setDatoAnterios(String datoAnterios) {
        this.datoAnterios = datoAnterios;
    }

    public String getDatoNuevo() {
        return datoNuevo;
    }

    public void setDatoNuevo(String datoNuevo) {
        this.datoNuevo = datoNuevo;
    }

    public String getNombreColumna() {
        return nombreColumna;
    }

    public void setNombreColumna(String nombreColumna) {
        this.nombreColumna = nombreColumna;
    }

    public String getUsuarioModifica() {
        return usuarioModifica;
    }

    public void setUsuarioModifica(String usuarioModifica) {
        this.usuarioModifica = usuarioModifica;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTipoActo() {
        return tipoActo;
    }

    public void setTipoActo(String tipoActo) {
        this.tipoActo = tipoActo;
    }
    
    
    
}
