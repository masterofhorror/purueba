/*
 *  Copyright (c) 2016 - Concesion RUNT S.A, Todos los derechos reservados.
 *  Proyecto: runt-consulta-nm
 *  Servicios de consulta de informacion
 *  para la administracion de Organismos de Transito en Colombia
 */

package co.com.runt.sagir.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dospina
 */
@Entity
@Table(name = "LOG_CONSULTA", schema = "CSWCONSULTAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LogConsulta.findAll", query = "SELECT l FROM LogConsulta l"),
    @NamedQuery(name = "LogConsulta.findByConsultaFecha", query = "SELECT l FROM LogConsulta l WHERE l.consultaFecha = :consultaFecha"),
    @NamedQuery(name = "LogConsulta.findByConsultaEntrada", query = "SELECT l FROM LogConsulta l WHERE l.consultaEntrada = :consultaEntrada"),
    @NamedQuery(name = "LogConsulta.findByConsultaAutoridad", query = "SELECT l FROM LogConsulta l WHERE l.consultaAutoridad = :consultaAutoridad"),
    @NamedQuery(name = "LogConsulta.findByConsultaUsuario", query = "SELECT l FROM LogConsulta l WHERE l.consultaUsuario = :consultaUsuario"),
    @NamedQuery(name = "LogConsulta.findByConsultaIp", query = "SELECT l FROM LogConsulta l WHERE l.consultaIp = :consultaIp"),
    @NamedQuery(name = "LogConsulta.findByConsultaId", query = "SELECT l FROM LogConsulta l WHERE l.consultaId = :consultaId")})
public class LogConsulta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CONSULTA_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date consultaFecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "CONSULTA_ENTRADA")
    private String consultaEntrada;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CONSULTA_AUTORIDAD")
    private Long consultaAutoridad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "CONSULTA_USUARIO")
    private String consultaUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "CONSULTA_IP")
    private String consultaIp;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CONSULTA_ID")
    private String consultaId;
    @JoinColumn(name = "CONSULTA_APLICACION_CODIGO", referencedColumnName = "APLICACION_CODIGO")
    @ManyToOne(optional = false)
    private PaAplicacion consultaAplicacionCodigo;
    @JoinColumn(name = "CONSULTA_TIPOCONSULTA_CODIGO", referencedColumnName = "TIPOCONSULTA_CODIGO")
    @ManyToOne(optional = false)
    private PaTipoconsulta consultaTipoconsultaCodigo;

    public LogConsulta() {
        //Constructor vacio
    }

    public LogConsulta(String consultaId) {
        this.consultaId = consultaId;
    }

    public LogConsulta(String consultaId, Date consultaFecha, String consultaEntrada, Long consultaAutoridad, String consultaUsuario, String consultaIp) {
        this.consultaId = consultaId;
        this.consultaFecha = consultaFecha;
        this.consultaEntrada = consultaEntrada;
        this.consultaAutoridad = consultaAutoridad;
        this.consultaUsuario = consultaUsuario;
        this.consultaIp = consultaIp;
    }

    public Date getConsultaFecha() {
        return consultaFecha;
    }

    public void setConsultaFecha(Date consultaFecha) {
        this.consultaFecha = consultaFecha;
    }

    public String getConsultaEntrada() {
        return consultaEntrada;
    }

    public void setConsultaEntrada(String consultaEntrada) {
        this.consultaEntrada = consultaEntrada;
    }

    public Long getConsultaAutoridad() {
        return consultaAutoridad;
    }

    public void setConsultaAutoridad(Long consultaAutoridad) {
        this.consultaAutoridad = consultaAutoridad;
    }

    public String getConsultaUsuario() {
        return consultaUsuario;
    }

    public void setConsultaUsuario(String consultaUsuario) {
        this.consultaUsuario = consultaUsuario;
    }

    public String getConsultaIp() {
        return consultaIp;
    }

    public void setConsultaIp(String consultaIp) {
        this.consultaIp = consultaIp;
    }

    public String getConsultaId() {
        return consultaId;
    }

    public void setConsultaId(String consultaId) {
        this.consultaId = consultaId;
    }

    public PaAplicacion getConsultaAplicacionCodigo() {
        return consultaAplicacionCodigo;
    }

    public void setConsultaAplicacionCodigo(PaAplicacion consultaAplicacionCodigo) {
        this.consultaAplicacionCodigo = consultaAplicacionCodigo;
    }

    public PaTipoconsulta getConsultaTipoconsultaCodigo() {
        return consultaTipoconsultaCodigo;
    }

    public void setConsultaTipoconsultaCodigo(PaTipoconsulta consultaTipoconsultaCodigo) {
        this.consultaTipoconsultaCodigo = consultaTipoconsultaCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (consultaId != null ? consultaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof LogConsulta)) {
            return false;
        }
        LogConsulta other = (LogConsulta) object;
        if ((this.consultaId == null && other.consultaId != null) || (this.consultaId != null && !this.consultaId.equals(other.consultaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.runt.consultacomparendos.entidades.LogConsulta[ consultaId=" + consultaId + " ]";
    }

}
