/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.entities;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author APENA
 */
@Embeddable
public class CiaCarguePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "SEC_VALIDACION")
    private String secValidacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NRO_IDENTIFICACION_SEDE")
     String nroIdentificacionSede;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NRO_CERTIFICADO")
    private String nroCertificado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COD_CARGA")
    private BigInteger codCarga;

    public CiaCarguePK() {
        //Default constructor
    }

    public CiaCarguePK(String secValidacion, String nroIdentificacionSede, String nroCertificado, BigInteger codCarga) {
        this.secValidacion = secValidacion;
        this.nroIdentificacionSede = nroIdentificacionSede;
        this.nroCertificado = nroCertificado;
        this.codCarga = codCarga;
    }

    public String getSecValidacion() {
        return secValidacion;
    }

    public void setSecValidacion(String secValidacion) {
        this.secValidacion = secValidacion;
    }

    public String getNroIdentificacionSede() {
        return nroIdentificacionSede;
    }

    public void setNroIdentificacionSede(String nroIdentificacionSede) {
        this.nroIdentificacionSede = nroIdentificacionSede;
    }

    public String getNroCertificado() {
        return nroCertificado;
    }

    public void setNroCertificado(String nroCertificado) {
        this.nroCertificado = nroCertificado;
    }

    public BigInteger getCodCarga() {
        return codCarga;
    }

    public void setCodCarga(BigInteger codCarga) {
        this.codCarga = codCarga;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (secValidacion != null ? secValidacion.hashCode() : 0);
        hash += (nroIdentificacionSede != null ? nroIdentificacionSede.hashCode() : 0);
        hash += (nroCertificado != null ? nroCertificado.hashCode() : 0);
        hash += (codCarga != null ? codCarga.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CiaCarguePK)) {
            return false;
        }
        CiaCarguePK other = (CiaCarguePK) object;
        if ((this.secValidacion == null && other.secValidacion != null) || (this.secValidacion != null && !this.secValidacion.equals(other.secValidacion))) {
            return false;
        }
        if ((this.nroIdentificacionSede == null && other.nroIdentificacionSede != null) || (this.nroIdentificacionSede != null && !this.nroIdentificacionSede.equals(other.nroIdentificacionSede))) {
            return false;
        }
        if ((this.nroCertificado == null && other.nroCertificado != null) || (this.nroCertificado != null && !this.nroCertificado.equals(other.nroCertificado))) {
            return false;
        }
        return !((this.codCarga == null && other.codCarga != null) || (this.codCarga != null && !this.codCarga.equals(other.codCarga)));
    }

    @Override
    public String toString() {
        return "co.com.runt.sagir.entities.CiaCarguePK[ secValidacion=" + secValidacion + ", nroIdentificacionSede=" + nroIdentificacionSede + ", nroCertificado=" + nroCertificado + ", codCarga=" + codCarga + " ]";
    }
    
}
