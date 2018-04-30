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
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author dsalamanca
 */
@Entity
@Cacheable
@Table(name = "MG_PROGRAMAR_CARGUE", schema = "MIGRACIONQX")
@NamedQueries({
    @NamedQuery(name = "MgProgramarCargue.CountByNombreArchivo", query = "SELECT COUNT(m) FROM MgProgramarCargue m WHERE m.nombreArcOriginal LIKE CONCAT('%',:nombreArchivo,'%') AND m.codEstado IN (0)")
})
public class MgProgramarCargue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_PROCESO")
    private Integer idProceso;
    @Column(name = "ID_ORDEN")
    private Integer idOrden;
    @Column(name = "ID_SECRETARIA")
    private Long idSecretaria;
    @Column(name = "COD_ESTANDAR")
    private Integer codEstandar;
    @Column(name = "TIPO_REGISTRO")
    private Integer tipoRegistro;
    @Column(name = "TIPOARC")
    private Integer tipoArc;
    @Column(name = "FECHA_PROGRAMACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaProgramacion;
    @Column(name = "FECHA_EJECUCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEjecucion;
    @Column(name = "NOM_ARCHIVO")
    private String nombreArchivo;
    @Column(name = "NOM_ARC_ORIGINAL")
    private String nombreArcOriginal;
    @Column(name = "COD_ESTADO")
    private Integer codEstado;
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    @Column(name = "COD_CARGA")
    private Integer codCarga;
    @Column(name = "ID_USUARIO")
    private Long idUsuario;
    @Column(name = "RESOLUCION")
    private String resolucion;

    public Integer getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(Integer idProceso) {
        this.idProceso = idProceso;
    }

    public Integer getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Integer idOrden) {
        this.idOrden = idOrden;
    }

    public Long getIdSecretaria() {
        return idSecretaria;
    }

    public void setIdSecretaria(Long idSecretaria) {
        this.idSecretaria = idSecretaria;
    }

    public Integer getCodEstandar() {
        return codEstandar;
    }

    public void setCodEstandar(Integer codEstandar) {
        this.codEstandar = codEstandar;
    }

    public Integer getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(Integer tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public Integer getTipoArc() {
        return tipoArc;
    }

    public void setTipoArc(Integer tipoArc) {
        this.tipoArc = tipoArc;
    }

    public Date getFechaProgramacion() {
        return fechaProgramacion;
    }

    public void setFechaProgramacion(Date fechaProgramacion) {
        this.fechaProgramacion = fechaProgramacion;
    }

    public Date getFechaEjecucion() {
        return fechaEjecucion;
    }

    public void setFechaEjecucion(Date fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getNombreArcOriginal() {
        return nombreArcOriginal;
    }

    public void setNombreArcOriginal(String nombreArcOriginal) {
        this.nombreArcOriginal = nombreArcOriginal;
    }

    public Integer getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(Integer codEstado) {
        this.codEstado = codEstado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getCodCarga() {
        return codCarga;
    }

    public void setCodCarga(Integer codCarga) {
        this.codCarga = codCarga;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getResolucion() {
        return resolucion;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    
}
