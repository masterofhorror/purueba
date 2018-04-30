/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dsalamanca
 */
@Entity
@Table(name = "PA_MENSAJE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mensaje.findAll", query = "SELECT m FROM Mensaje m"),
    @NamedQuery(name = "Mensaje.detalle", query = "SELECT m.mensajeDetalle FROM Mensaje m WHERE m.mensajeIdentificador = :idIdentificador "),
    @NamedQuery(name = "Mensaje.findByMensajeId", query = "SELECT m FROM Mensaje m WHERE m.mensajeId = :mensajeId"),
    @NamedQuery(name = "Mensaje.findByMensajeIdentificador", query = "SELECT m FROM Mensaje m WHERE m.mensajeIdentificador = :mensajeIdentificador"),
    @NamedQuery(name = "Mensaje.findByMensajeDetalle", query = "SELECT m FROM Mensaje m WHERE m.mensajeDetalle = :mensajeDetalle"),
    @NamedQuery(name = "Mensaje.findByMensajeEstado", query = "SELECT m FROM Mensaje m WHERE m.mensajeEstado = :mensajeEstado")})
public class Mensaje implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "MENSAJE_IDENTIFICADOR")
    private BigInteger mensajeIdentificador;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "MENSAJE_DETALLE")
    private String mensajeDetalle;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "MENSAJE_ESTADO")
    private String mensajeEstado;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "MENSAJE_ID")
    private BigDecimal mensajeId;

    public Mensaje() {
        //Default constructor
    }

    public Mensaje(BigDecimal mensajeId) {
        this.mensajeId = mensajeId;
    }

    public Mensaje(BigDecimal mensajeId, BigInteger mensajeIdentificador, String mensajeDetalle, String mensajeEstado) {
        this.mensajeId = mensajeId;
        this.mensajeIdentificador = mensajeIdentificador;
        this.mensajeDetalle = mensajeDetalle;
        this.mensajeEstado = mensajeEstado;
    }

    public BigDecimal getMensajeId() {
        return mensajeId;
    }

    public void setMensajeId(BigDecimal mensajeId) {
        this.mensajeId = mensajeId;
    }

    public BigInteger getMensajeIdentificador() {
        return mensajeIdentificador;
    }

    public void setMensajeIdentificador(BigInteger mensajeIdentificador) {
        this.mensajeIdentificador = mensajeIdentificador;
    }

    public String getMensajeDetalle() {
        return mensajeDetalle;
    }

    public void setMensajeDetalle(String mensajeDetalle) {
        this.mensajeDetalle = mensajeDetalle;
    }

    public String getMensajeEstado() {
        return mensajeEstado;
    }

    public void setMensajeEstado(String mensajeEstado) {
        this.mensajeEstado = mensajeEstado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mensajeId != null ? mensajeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Mensaje)) {
            return false;
        }
        Mensaje other = (Mensaje) object;
        return !((this.mensajeId == null && other.mensajeId != null) || (this.mensajeId != null && !this.mensajeId.equals(other.mensajeId)));
    }

    @Override
    public String toString() {
        return "co.com.runt.tiquetera.entities.Mensaje[ mensajeId=" + mensajeId + " ]";
    }
}
