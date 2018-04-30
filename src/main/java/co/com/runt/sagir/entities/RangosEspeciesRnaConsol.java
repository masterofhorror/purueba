/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Hmoreno
 */
@Entity
@Table(name = "RANGOS_ESPECIES_RNA_CONSOL", schema = "MIGRUNT1")
@NamedQuery(name = "RangosEspeciesRnaConsol.findMaximoConsecutivo", query = "SELECT MAX(p.consecutivo) FROM RangosEspeciesRnaConsol p")
public class RangosEspeciesRnaConsol implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CONSECUTIVO")
    private Long consecutivo;

    @Column(name = "ID_SECRETARIA_1")
    private Long idSecretaria1;

    @Column(name = "ID_SECRETARIA_2")
    private Long idSecretaria2;

    @Column(name = "CARRO_MOTO")
    private Integer carroMoto;

    @Column(name = "DESDE")
    private String desde;

    @Column(name = "HASTA")
    private String hasta;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA")
    private Date fecha;

    @Column(name = "RESOLUCION")
    private String resolucion;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_ENTREGA")
    private Date fechaEntrega;

    @Column(name = "OBSERVACIONES")
    private String observaciones;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "ESTADO_2")
    private String estado2;

    public Long getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(Long consecutivo) {
        this.consecutivo = consecutivo;
    }

    public Long getIdSecretaria1() {
        return idSecretaria1;
    }

    public void setIdSecretaria1(Long idSecretaria1) {
        this.idSecretaria1 = idSecretaria1;
    }

    public Long getIdSecretaria2() {
        return idSecretaria2;
    }

    public void setIdSecretaria2(Long idSecretaria2) {
        this.idSecretaria2 = idSecretaria2;
    }

    public Integer getCarroMoto() {
        return carroMoto;
    }

    public void setCarroMoto(Integer carroMoto) {
        this.carroMoto = carroMoto;
    }

    public String getDesde() {
        return desde;
    }

    public void setDesde(String desde) {
        this.desde = desde;
    }

    public String getHasta() {
        return hasta;
    }

    public void setHasta(String hasta) {
        this.hasta = hasta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getResolucion() {
        return resolucion;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstado2() {
        return estado2;
    }

    public void setEstado2(String estado2) {
        this.estado2 = estado2;
    }

}
