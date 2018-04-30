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
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Hmoreno
 */
@Entity
@Table(name = "MG_PROGRAMAR_CARGUE", schema = "MIGRACIONQX")
@NamedQuery(name = "ProgramarCargue.findAll", query = "SELECT p FROM ProgramarCargue p WHERE p.codigoEstado = :codigoEstado ORDER BY p.id.idProceso ASC, p.id.idOrden ASC")
public class ProgramarCargue implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ProgramarCarguePK id;

    @Column(name = "ID_SECRETARIA")
    private Long idSecretaria;

    @Column(name = "COD_ESTANDAR")
    private Integer codigoEstandar;

    @Column(name = "TIPO_REGISTRO")
    private Integer tipoRegistro;

    @Column(name = "TIPOARC")
    private Integer tipoArchivo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_PROGRAMACION")
    private Date fechaProgramacion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_EJECUCION")
    private Date fechaEjecucion;

    @Column(name = "NOM_ARCHIVO")
    private String nombreArchivo;

    @Column(name = "NOM_ARC_ORIGINAL")
    private String nombreArchivoOriginal;

    @Column(name = "COD_ESTADO")
    private Long codigoEstado;

    @Column(name = "OBSERVACIONES")
    private String observaciones;

    @Column(name = "COD_CARGA")
    private Long codigoCarga;

    @Column(name = "ID_USUARIO")
    private Long idUsuario;

    @Column(name = "RESOLUCION")
    private String resolucion;

    public ProgramarCarguePK getId() {
        return id;
    }

    public void setId(ProgramarCarguePK id) {
        this.id = id;
    }

    public Long getIdSecretaria() {
        return idSecretaria;
    }

    public void setIdSecretaria(Long idSecretaria) {
        this.idSecretaria = idSecretaria;
    }

    public Integer getCodigoEstandar() {
        return codigoEstandar;
    }

    public void setCodigoEstandar(Integer codigoEstandar) {
        this.codigoEstandar = codigoEstandar;
    }

    public Integer getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(Integer tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public Integer getTipoArchivo() {
        return tipoArchivo;
    }

    public void setTipoArchivo(Integer tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
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

    public String getNombreArchivoOriginal() {
        return nombreArchivoOriginal;
    }

    public void setNombreArchivoOriginal(String nombreArchivoOriginal) {
        this.nombreArchivoOriginal = nombreArchivoOriginal;
    }

    public Long getCodigoEstado() {
        return codigoEstado;
    }

    public void setCodigoEstado(Long codigoEstado) {
        this.codigoEstado = codigoEstado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Long getCodigoCarga() {
        return codigoCarga;
    }

    public void setCodigoCarga(Long codigoCarga) {
        this.codigoCarga = codigoCarga;
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
