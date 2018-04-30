/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.entities;

import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author dsalamanca
 */
@Entity
@Table(name = "PA_TIPOSERVI", schema = "RUNTPROD")
@Cacheable
@NamedQueries({
    @NamedQuery(name = "TipoServicio.findAll", query = "SELECT t FROM TipoServicio t WHERE t.estado = 'A'")
})
public class TipoServicio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "TIPOSERVI_IDETIPSER")
    private Integer ideTipSer;
    @Column(name = "TIPOSERVI_NOMBRE")
    private String nombre;
    @Column(name = "TIPOSERVI_ESTADO")
    private String estado;
    @Column(name = "TIPOSERVI_ANOSEXENT")
    private Integer anosexent;
    @Column(name = "TIPOSERVI_VIRETEME")
    private Integer vireteme;
    @Column(name = "TIPOSERVI_REGREPCAU")
    private String regrepCau;

    public Integer getIdeTipSer() {
        return ideTipSer;
    }

    public void setIdeTipSer(Integer ideTipSer) {
        this.ideTipSer = ideTipSer;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getAnosexent() {
        return anosexent;
    }

    public void setAnosexent(Integer anosexent) {
        this.anosexent = anosexent;
    }

    public Integer getVireteme() {
        return vireteme;
    }

    public void setVireteme(Integer vireteme) {
        this.vireteme = vireteme;
    }

    public String getRegrepCau() {
        return regrepCau;
    }

    public void setRegrepCau(String regrepCau) {
        this.regrepCau = regrepCau;
    }

    
}
