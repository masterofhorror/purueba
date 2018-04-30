/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Hmoreno
 */
@Entity
@Table(name = "RANGOS_ESPECIES_SERIE_RNA", schema = "MIGRUNT1")
public class RangosEspeciesSerieRna implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private RangosEspeciesSerieRnaPK id;

    @Column(name = "MIGRADO_PLACA_SERIE")
    private String migradoPlacaSerie;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "OBSERVACION")
    private String observaciones;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_CARGUE")
    private Date fechaCargue;

    @Column(name = "CARRO_MOTO")
    private Integer carroMoto;

    @Column(name = "ID_SECRETARIA_RUNT")
    private Long idSecretariaRunt;

    public RangosEspeciesSerieRnaPK getId() {
        return id;
    }

    public void setId(RangosEspeciesSerieRnaPK id) {
        this.id = id;
    }

    public String getMigradoPlacaSerie() {
        return migradoPlacaSerie;
    }

    public void setMigradoPlacaSerie(String migradoPlacaSerie) {
        this.migradoPlacaSerie = migradoPlacaSerie;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getFechaCargue() {
        return fechaCargue;
    }

    public void setFechaCargue(Date fechaCargue) {
        this.fechaCargue = fechaCargue;
    }

    public Integer getCarroMoto() {
        return carroMoto;
    }

    public void setCarroMoto(Integer carroMoto) {
        this.carroMoto = carroMoto;
    }

    public Long getIdSecretariaRunt() {
        return idSecretariaRunt;
    }

    public void setIdSecretariaRunt(Long idSecretariaRunt) {
        this.idSecretariaRunt = idSecretariaRunt;
    }

}
