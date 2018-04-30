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
 * @author APENA
 */
@Entity
@Table(name = "PA_NIT_CIA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PaNitCia.findAll", query = "SELECT p FROM PaNitCia p"),
    @NamedQuery(name = "PaNitCia.findByCiaId", query = "SELECT p FROM PaNitCia p WHERE p.ciaId = :ciaId"),
    @NamedQuery(name = "PaNitCia.findByCiaNit", query = "SELECT p FROM PaNitCia p WHERE p.ciaNit = :ciaNit"),
    @NamedQuery(name = "PaNitCia.findByCiaNombre", query = "SELECT p FROM PaNitCia p WHERE p.ciaNombre = :ciaNombre"),
    @NamedQuery(name = "PaNitCia.findByCiaEstadoNit", query = "SELECT p FROM PaNitCia p WHERE p.ciaEstadoNit = :ciaEstadoNit")})
public class PaNitCia implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CIA_ID")
    private BigDecimal ciaId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CIA_NIT")
    private BigInteger ciaNit;
    @Size(max = 100)
    @Column(name = "CIA_NOMBRE")
    private String ciaNombre;
    @Size(max = 1)
    @Column(name = "CIA_ESTADO_NIT")
    private String ciaEstadoNit;

    public PaNitCia() {
        //Default constructor
    }

    public PaNitCia(BigDecimal ciaId) {
        this.ciaId = ciaId;
    }

    public PaNitCia(BigDecimal ciaId, BigInteger ciaNit) {
        this.ciaId = ciaId;
        this.ciaNit = ciaNit;
    }

    public BigDecimal getCiaId() {
        return ciaId;
    }

    public void setCiaId(BigDecimal ciaId) {
        this.ciaId = ciaId;
    }

    public BigInteger getCiaNit() {
        return ciaNit;
    }

    public void setCiaNit(BigInteger ciaNit) {
        this.ciaNit = ciaNit;
    }

    public String getCiaNombre() {
        return ciaNombre;
    }

    public void setCiaNombre(String ciaNombre) {
        this.ciaNombre = ciaNombre;
    }

    public String getCiaEstadoNit() {
        return ciaEstadoNit;
    }

    public void setCiaEstadoNit(String ciaEstadoNit) {
        this.ciaEstadoNit = ciaEstadoNit;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ciaId != null ? ciaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof PaNitCia)) {
            return false;
        }
        PaNitCia other = (PaNitCia) object;
        return !((this.ciaId == null && other.ciaId != null) || (this.ciaId != null && !this.ciaId.equals(other.ciaId)));
    }

    @Override
    public String toString() {
        return "co.com.runt.sagir.entities.PaNitCia[ ciaId=" + ciaId + " ]";
    }
    
}
