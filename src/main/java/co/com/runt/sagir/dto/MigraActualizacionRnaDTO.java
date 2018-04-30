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
public class MigraActualizacionRnaDTO {
    
    private Long codCarga;
    private String placa;
    private Long cargaControl;
    private String estadoProceso;
    private Long usuario;
    private Date fechaProceso;

    public Long getCodCarga() {
        return codCarga;
    }

    public void setCodCarga(Long codCarga) {
        this.codCarga = codCarga;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Long getCargaControl() {
        return cargaControl;
    }

    public void setCargaControl(Long cargaControl) {
        this.cargaControl = cargaControl;
    }

    public String getEstadoProceso() {
        return estadoProceso;
    }

    public void setEstadoProceso(String estadoProceso) {
        this.estadoProceso = estadoProceso;
    }

    public Long getUsuario() {
        return usuario;
    }

    public void setUsuario(Long usuario) {
        this.usuario = usuario;
    }

    public Date getFechaProceso() {
        return fechaProceso;
    }

    public void setFechaProceso(Date fechaProceso) {
        this.fechaProceso = fechaProceso;
    }
    
    
}
