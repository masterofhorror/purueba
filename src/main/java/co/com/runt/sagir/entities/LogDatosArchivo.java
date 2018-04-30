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
 * @author dsalamanca
 */
@Entity
@Table(name = "TBL_LOGDATOSARCHIVO")
@Cacheable
@NamedQueries({
    @NamedQuery(name = "LogDatosArchivo.findAll", query = "SELECT l FROM LogDatosArchivo l"),
    @NamedQuery(name = "LogDatosArchivo.findByDatosarchivoId", query = "SELECT l FROM LogDatosArchivo l WHERE l.datosarchivoId = :datosarchivoId"),
    @NamedQuery(name = "LogDatosArchivo.findByDatosarchivoEntrada", query = "SELECT l FROM LogDatosArchivo l WHERE l.datosarchivoEntrada = :datosarchivoEntrada"),
    @NamedQuery(name = "LogDatosArchivo.findByDatosarchivoFecha", query = "SELECT l FROM LogDatosArchivo l WHERE l.datosarchivoFecha = :datosarchivoFecha"),
    @NamedQuery(name = "LogDatosArchivo.findByDatosarchivoTipoarchivo", query = "SELECT l FROM LogDatosArchivo l WHERE l.datosarchivoTipoarchivo = :datosarchivoTipoarchivo"),
    @NamedQuery(name = "LogDatosArchivo.findByDatosarchivoAutoridad", query = "SELECT l FROM LogDatosArchivo l WHERE l.datosarchivoAutoridad = :datosarchivoAutoridad")})
public class LogDatosArchivo implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator (name = "LOGDATOSARCHIVO_SEQ", allocationSize = 1, sequenceName = "LOGDATOSARCHIVO_SEQ")
    @GeneratedValue(generator = "LOGDATOSARCHIVO_SEQ", strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATOSARCHIVO_ID")
    private Integer datosarchivoId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "DATOSARCHIVO_ENTRADA")
    private String datosarchivoEntrada;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "DATOSARCHIVO_NOMBREARCHIVO")
    private String nombreArchivo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATOSARCHIVO_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datosarchivoFecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "DATOSARCHIVO_TIPOARCHIVO")
    private String datosarchivoTipoarchivo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATOSARCHIVO_AUTORIDAD")
    private Long datosarchivoAutoridad;

    public LogDatosArchivo() {
        //Constructor vacio
    }

    public LogDatosArchivo(Integer datosarchivoId) {
        this.datosarchivoId = datosarchivoId;
    }

    public LogDatosArchivo(Integer datosarchivoId, String datosarchivoEntrada, String nombreArchivo, Date datosarchivoFecha, String datosarchivoTipoarchivo, Long datosarchivoAutoridad) {
        this.datosarchivoId = datosarchivoId;
        this.datosarchivoEntrada = datosarchivoEntrada;
        this.datosarchivoEntrada = nombreArchivo;
        this.datosarchivoFecha = datosarchivoFecha;
        this.datosarchivoTipoarchivo = datosarchivoTipoarchivo;
        this.datosarchivoAutoridad = datosarchivoAutoridad;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
    
    

    public Integer getDatosarchivoId() {
        return datosarchivoId;
    }

    public void setDatosarchivoId(Integer datosarchivoId) {
        this.datosarchivoId = datosarchivoId;
    }

    public String getDatosarchivoEntrada() {
        return datosarchivoEntrada;
    }

    public void setDatosarchivoEntrada(String datosarchivoEntrada) {
        this.datosarchivoEntrada = datosarchivoEntrada;
    }

    public Date getDatosarchivoFecha() {
        return datosarchivoFecha;
    }

    public void setDatosarchivoFecha(Date datosarchivoFecha) {
        this.datosarchivoFecha = datosarchivoFecha;
    }

    public String getDatosarchivoTipoarchivo() {
        return datosarchivoTipoarchivo;
    }

    public void setDatosarchivoTipoarchivo(String datosarchivoTipoarchivo) {
        this.datosarchivoTipoarchivo = datosarchivoTipoarchivo;
    }

    public Long getDatosarchivoAutoridad() {
        return datosarchivoAutoridad;
    }

    public void setDatosarchivoAutoridad(Long datosarchivoAutoridad) {
        this.datosarchivoAutoridad = datosarchivoAutoridad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (datosarchivoId != null ? datosarchivoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof LogDatosArchivo)) {
            return false;
        }
        LogDatosArchivo other = (LogDatosArchivo) object;
        if ((this.datosarchivoId == null && other.datosarchivoId != null) || (this.datosarchivoId != null && !this.datosarchivoId.equals(other.datosarchivoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.runt.sagir.entities.LogDatosArchivo[ datosarchivoId=" + datosarchivoId + " ]";
    }
    
}
