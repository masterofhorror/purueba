/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author dsalamanca
 */
@Entity
@Table(name = "HI_AUTOMOTOR", schema = "RUNTPROD")
@Cacheable
@NamedQueries({})
public class HiAutomotor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "AUTOMOTOR_DATOANTER")
    private String datoAnterios;
    @Column(name = "AUTOMOTOR_DATONUEVO")
    private String datoNuevo;
    @Column(name = "AUTOMOTOR_NOMBCOLUM")
    private String nombreColumna;
    @Column(name = "AUTOMOTOR_USUAMODIF")
    private String usuarioModifica;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "AUTOMOTOR_FECHMODIF")
    private Date fechaModificacion;
    @Column(name = "AUTOMOTOR_PLACA")
    private String placa;
    @Column(name = "AUTOMOTOR_TIPACTREM_NOMBRE")
    private String tipoActo;

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
