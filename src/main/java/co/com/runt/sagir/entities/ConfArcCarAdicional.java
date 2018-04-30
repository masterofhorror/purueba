/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Hmoreno
 */
@Entity
@Table(name = "CONF_ARC_CAR_ADICIONAL", schema = "MIGRACIONQX")
@NamedQueries({
    @NamedQuery(name = "ConfArcCarAdicional.findAll", query = "SELECT c FROM ConfArcCarAdicional c")})
public class ConfArcCarAdicional implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SeqConfArcCarAdicional", sequenceName = "seq_cod_proceso_adicional", schema = "MIGRACIONQX", allocationSize = 1)
    @GeneratedValue(generator = "SeqConfArcCarAdicional", strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @NotNull
    @Column(name = "COD_PROCESO")
    private Integer codProceso;

    @Basic(optional = false)
    @NotNull
    @Column(name = "COD_ESTANDAR")
    private Integer codEstandar;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "NOM_ARCHIVO")
    private String nomArchivo;

    @Basic(optional = false)
    @NotNull
    @Column(name = "COD_ESTADO")
    private Integer codEstado;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_SECRETARIA")
    private Long idSecretaria;

    @Basic(optional = false)
    @NotNull
    @Column(name = "TIPO_ARC")
    private Integer tipoArc;

    @Basic(optional = false)
    @NotNull
    @Column(name = "FEC_PROCESO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecProceso;

    @Size(max = 500)
    @Column(name = "DES_ERROR")
    private String desError;

    @Basic(optional = false)
    @NotNull
    @Column(name = "NUM_REGLEIDOS")
    private Integer numRegleidos;

    @Column(name = "COD_BOLETIN")
    private Long codBoletin;

    @Column(name = "CANT_ERROR")
    private Long cantError;

    @Size(max = 300)
    @Column(name = "NOM_ARC_ORIGINAL")
    private String nomArcOriginal;

    @Column(name = "TIPO_REGISTRO")
    private Long tipoRegistro;

    @Column(name = "COD_CARGA")
    private Long codCarga;

    public Integer getCodProceso() {
        return codProceso;
    }

    public void setCodProceso(Integer codProceso) {
        this.codProceso = codProceso;
    }

    public Integer getCodEstandar() {
        return codEstandar;
    }

    public void setCodEstandar(Integer codEstandar) {
        this.codEstandar = codEstandar;
    }

    public String getNomArchivo() {
        return nomArchivo;
    }

    public void setNomArchivo(String nomArchivo) {
        this.nomArchivo = nomArchivo;
    }

    public Integer getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(Integer codEstado) {
        this.codEstado = codEstado;
    }

    public Long getIdSecretaria() {
        return idSecretaria;
    }

    public void setIdSecretaria(Long idSecretaria) {
        this.idSecretaria = idSecretaria;
    }

    public Integer getTipoArc() {
        return tipoArc;
    }

    public void setTipoArc(Integer tipoArc) {
        this.tipoArc = tipoArc;
    }

    public Date getFecProceso() {
        return fecProceso;
    }

    public void setFecProceso(Date fecProceso) {
        this.fecProceso = fecProceso;
    }

    public String getDesError() {
        return desError;
    }

    public void setDesError(String desError) {
        this.desError = desError;
    }

    public Integer getNumRegleidos() {
        return numRegleidos;
    }

    public void setNumRegleidos(Integer numRegleidos) {
        this.numRegleidos = numRegleidos;
    }

    public Long getCodBoletin() {
        return codBoletin;
    }

    public void setCodBoletin(Long codBoletin) {
        this.codBoletin = codBoletin;
    }

    public Long getCantError() {
        return cantError;
    }

    public void setCantError(Long cantError) {
        this.cantError = cantError;
    }

    public String getNomArcOriginal() {
        return nomArcOriginal;
    }

    public void setNomArcOriginal(String nomArcOriginal) {
        this.nomArcOriginal = nomArcOriginal;
    }

    public Long getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(Long tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public Long getCodCarga() {
        return codCarga;
    }

    public void setCodCarga(Long codCarga) {
        this.codCarga = codCarga;
    }

}
