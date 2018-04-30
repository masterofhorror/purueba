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

/**
 *
 * @author dsalamanca
 */
@Embeddable
public class TblLicTtoPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_SECRETARIA")
    private BigInteger idSecretaria;
    @Basic(optional = false)
    @Column(name = "NRO_PLACA")
    private String nroPlaca;
    @Basic(optional = false)
    @Column(name = "COD_CARGA")
    private Integer codCarga;

    public TblLicTtoPK() {
        //Default constructor
    }

    public TblLicTtoPK(BigInteger idSecretaria, String nroPlaca, Integer codCarga) {
        this.idSecretaria = idSecretaria;
        this.nroPlaca = nroPlaca;
        this.codCarga = codCarga;
    }

    public BigInteger getIdSecretaria() {
        return idSecretaria;
    }

    public void setIdSecretaria(BigInteger idSecretaria) {
        this.idSecretaria = idSecretaria;
    }

    public String getNroPlaca() {
        return nroPlaca;
    }

    public void setNroPlaca(String nroPlaca) {
        this.nroPlaca = nroPlaca;
    }

    public Integer getCodCarga() {
        return codCarga;
    }

    public void setCodCarga(Integer codCarga) {
        this.codCarga = codCarga;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSecretaria != null ? idSecretaria.hashCode() : 0);
        hash += (nroPlaca != null ? nroPlaca.hashCode() : 0);
        hash += (codCarga != null ? codCarga.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TblLicTtoPK)) {
            return false;
        }
        TblLicTtoPK other = (TblLicTtoPK) object;
        if ((this.idSecretaria == null && other.idSecretaria != null) || (this.idSecretaria != null && !this.idSecretaria.equals(other.idSecretaria))) {
            return false;
        }
        if ((this.nroPlaca == null && other.nroPlaca != null) || (this.nroPlaca != null && !this.nroPlaca.equals(other.nroPlaca))) {
            return false;
        }
        return !((this.codCarga == null && other.codCarga != null) || (this.codCarga != null && !this.codCarga.equals(other.codCarga)));
    }
    
}
