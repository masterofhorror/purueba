/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author dsalamanca
 */
@Entity
@Table(name = "CONF_ARC_CAR", schema = "MIGRACIONQX")
@Cacheable
@NamedQueries({
    @NamedQuery(name = "ConfArcCar.findAll", query = "SELECT c FROM ConfArcCar c"),
    @NamedQuery(name = "ConfArcCar.findByCodProceso", query = "SELECT c FROM ConfArcCar c WHERE c.codProceso = :codProceso"),
    @NamedQuery(name = "ConfArcCar.CountByNombreArchivo", query = "SELECT COUNT(c) FROM ConfArcCar c WHERE c.nomArcOriginal LIKE CONCAT('%',:nombreArchivo,'%')"),
    @NamedQuery(name = "ConfArcCar.findByCodEstandar", query = "SELECT c FROM ConfArcCar c WHERE c.codEstandar = :codEstandar"),
    @NamedQuery(name = "ConfArcCar.findByNomArchivo", query = "SELECT c FROM ConfArcCar c WHERE c.nomArchivo = :nomArchivo"),
    @NamedQuery(name = "ConfArcCar.findByCodEstado", query = "SELECT c FROM ConfArcCar c WHERE c.codEstado = :codEstado"),
    @NamedQuery(name = "ConfArcCar.findByIdSecretaria", query = "SELECT c FROM ConfArcCar c WHERE c.idSecretaria = :idSecretaria"),
    @NamedQuery(name = "ConfArcCar.findByFecProceso", query = "SELECT c FROM ConfArcCar c WHERE c.fecProceso = :fecProceso"),
    @NamedQuery(name = "ConfArcCar.findByDesError", query = "SELECT c FROM ConfArcCar c WHERE c.desError = :desError"),
    @NamedQuery(name = "ConfArcCar.findByNumRegleidos", query = "SELECT c FROM ConfArcCar c WHERE c.numRegleidos = :numRegleidos"),
    @NamedQuery(name = "ConfArcCar.findByCodBoletin", query = "SELECT c FROM ConfArcCar c WHERE c.codBoletin = :codBoletin"),
    @NamedQuery(name = "ConfArcCar.findByCantError", query = "SELECT c FROM ConfArcCar c WHERE c.cantError = :cantError"),
    @NamedQuery(name = "ConfArcCar.findCountByNomArcOriginal", query = "SELECT COUNT(c) FROM ConfArcCar c WHERE c.nomArcOriginal = :nomArcOriginal AND c.codCarga IS NULL AND c.codEstado = 0"),
    @NamedQuery(name = "ConfArcCar.updateNumRegLeidos", query = "UPDATE ConfArcCar c SET c.numRegleidos = :numRegLeidos WHERE c.codProceso = :codProceso AND c.codCarga = :codCarga"),
    @NamedQuery(name = "ConfArcCar.updateCodEstado", query = "UPDATE ConfArcCar c SET c.codEstado = 1 WHERE c.codCarga = :codCarga"),
    @NamedQuery(name = "ConfArcCar.findByTipoRegistro", query = "SELECT c FROM ConfArcCar c WHERE c.tipoRegistro = :tipoRegistro")})
public class ConfArcCar implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(name = "MIGRACIONQX.SEQ_CODPROCESO", allocationSize = 1, sequenceName = "MIGRACIONQX.SEQ_CODPROCESO")
    @GeneratedValue(generator = "MIGRACIONQX.SEQ_CODPROCESO", strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "COD_PROCESO")
    private Integer codProceso;
    @Basic(optional = false)
    @Column(name = "COD_ESTANDAR")
    private Integer codEstandar;
    @Basic(optional = false)
    @Column(name = "NOM_ARCHIVO")
    private String nomArchivo;
    @Basic(optional = false)
    @Column(name = "COD_ESTADO")
    private Integer codEstado;
    @Basic(optional = false)
    @Column(name = "ID_SECRETARIA")
    private Long idSecretaria;
    @Basic(optional = false)
    @Column(name = "FEC_PROCESO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecProceso;
    @Column(name = "DES_ERROR")
    private String desError;
    @Basic(optional = false)
    @Column(name = "NUM_REGLEIDOS")
    private int numRegleidos;
    @Column(name = "COD_BOLETIN")
    private Integer codBoletin;
    @Column(name = "CANT_ERROR")
    private Integer cantError;
    @Column(name = "NOM_ARC_ORIGINAL")
    private String nomArcOriginal;
    @Column(name = "TIPO_REGISTRO")
    private Integer tipoRegistro;
    @JoinColumn(name = "COD_CARGA", referencedColumnName = "COD_CARGA")
    @ManyToOne
    private Carga codCarga;
    @Column(name = "TIPO_ARC")
    private Integer tipoArchivo;

    public ConfArcCar() {
        //Constructor vacio
    }

    public ConfArcCar(Integer codProceso) {
        this.codProceso = codProceso;
    }

    public ConfArcCar(Integer codProceso, Integer codEstandar, String nomArchivo, Integer codEstado, Long idSecretaria, Date fecProceso, int numRegleidos) {
        this.codProceso = codProceso;
        this.codEstandar = codEstandar;
        this.nomArchivo = nomArchivo;
        this.codEstado = codEstado;
        this.idSecretaria = idSecretaria;
        this.fecProceso = fecProceso;
        this.numRegleidos = numRegleidos;
    }

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

    public int getNumRegleidos() {
        return numRegleidos;
    }

    public void setNumRegleidos(int numRegleidos) {
        this.numRegleidos = numRegleidos;
    }

    public Integer getCodBoletin() {
        return codBoletin;
    }

    public void setCodBoletin(Integer codBoletin) {
        this.codBoletin = codBoletin;
    }

    public Integer getCantError() {
        return cantError;
    }

    public void setCantError(Integer cantError) {
        this.cantError = cantError;
    }

    public String getNomArcOriginal() {
        return nomArcOriginal;
    }

    public void setNomArcOriginal(String nomArcOriginal) {
        this.nomArcOriginal = nomArcOriginal;
    }

    public Integer getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(Integer tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public Carga getCodCarga() {
        return codCarga;
    }

    public void setCodCarga(Carga codCarga) {
        this.codCarga = codCarga;
    }

    public Integer getTipoArchivo() {
        return tipoArchivo;
    }

    public void setTipoArchivo(Integer tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codProceso != null ? codProceso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ConfArcCar)) {
            return false;
        }
        ConfArcCar other = (ConfArcCar) object;
        if ((this.codProceso == null && other.codProceso != null) || (this.codProceso != null && !this.codProceso.equals(other.codProceso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mapeoMigracionQx.ConfArcCar[ codProceso=" + codProceso + " ]";
    }
    
}
