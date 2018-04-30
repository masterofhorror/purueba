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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dsalamanca
 */
@Entity
@Table(name = "TEMPORARY_BOLETIN_RNC_CONSOLID", schema = "CSWSAGIR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BoletinRNC.findAll", query = "SELECT b FROM BoletinRNC b"),
    @NamedQuery(name = "BoletinRNC.findByCodCarga", query = "SELECT b FROM BoletinRNC b WHERE b.codCarga = :codCarga"),
    @NamedQuery(name = "BoletinRNC.findCountCodCarga", query = "SELECT COUNT(b) FROM BoletinRNC b WHERE b.codCarga = :codCarga"),
    @NamedQuery(name = "BoletinRNC.findByLiceReportadas", query = "SELECT b FROM BoletinRNC b WHERE b.liceReportadas = :liceReportadas"),
    @NamedQuery(name = "BoletinRNC.findByLiceAprobadas", query = "SELECT b FROM BoletinRNC b WHERE b.liceAprobadas = :liceAprobadas"),
    @NamedQuery(name = "BoletinRNC.findByLiceRechazadas", query = "SELECT b FROM BoletinRNC b WHERE b.liceRechazadas = :liceRechazadas"),
    @NamedQuery(name = "BoletinRNC.findByLiceNovedades", query = "SELECT b FROM BoletinRNC b WHERE b.liceNovedades = :liceNovedades"),
    @NamedQuery(name = "BoletinRNC.findByConsolLiceReportadas", query = "SELECT b FROM BoletinRNC b WHERE b.consolLiceReportadas = :consolLiceReportadas"),
    @NamedQuery(name = "BoletinRNC.findByConsolLiceAprobadas", query = "SELECT b FROM BoletinRNC b WHERE b.consolLiceAprobadas = :consolLiceAprobadas"),
    @NamedQuery(name = "BoletinRNC.findByConsolLiceRechazadas", query = "SELECT b FROM BoletinRNC b WHERE b.consolLiceRechazadas = :consolLiceRechazadas"),
    @NamedQuery(name = "BoletinRNC.findByPersReportadas", query = "SELECT b FROM BoletinRNC b WHERE b.persReportadas = :persReportadas"),
    @NamedQuery(name = "BoletinRNC.findByPersAprobadas", query = "SELECT b FROM BoletinRNC b WHERE b.persAprobadas = :persAprobadas"),
    @NamedQuery(name = "BoletinRNC.findByPersRechazadas", query = "SELECT b FROM BoletinRNC b WHERE b.persRechazadas = :persRechazadas"),
    @NamedQuery(name = "BoletinRNC.findByPersNovedades", query = "SELECT b FROM BoletinRNC b WHERE b.persNovedades = :persNovedades"),
    @NamedQuery(name = "BoletinRNC.findByConsolPersReportadas", query = "SELECT b FROM BoletinRNC b WHERE b.consolPersReportadas = :consolPersReportadas"),
    @NamedQuery(name = "BoletinRNC.findByConsolPersAprobadas", query = "SELECT b FROM BoletinRNC b WHERE b.consolPersAprobadas = :consolPersAprobadas"),
    @NamedQuery(name = "BoletinRNC.findByConsolPersRechazadas", query = "SELECT b FROM BoletinRNC b WHERE b.consolPersRechazadas = :consolPersRechazadas"),
    @NamedQuery(name = "BoletinRNC.findByRestReportadas", query = "SELECT b FROM BoletinRNC b WHERE b.restReportadas = :restReportadas"),
    @NamedQuery(name = "BoletinRNC.findByRestAprobadas", query = "SELECT b FROM BoletinRNC b WHERE b.restAprobadas = :restAprobadas"),
    @NamedQuery(name = "BoletinRNC.findByRestRechazadas", query = "SELECT b FROM BoletinRNC b WHERE b.restRechazadas = :restRechazadas"),
    @NamedQuery(name = "BoletinRNC.findByRestNovedades", query = "SELECT b FROM BoletinRNC b WHERE b.restNovedades = :restNovedades"),
    @NamedQuery(name = "BoletinRNC.findByConsolRestReportadas", query = "SELECT b FROM BoletinRNC b WHERE b.consolRestReportadas = :consolRestReportadas"),
    @NamedQuery(name = "BoletinRNC.findByConsolRestAprobadas", query = "SELECT b FROM BoletinRNC b WHERE b.consolRestAprobadas = :consolRestAprobadas"),
    @NamedQuery(name = "BoletinRNC.findByConsolRestRechazadas", query = "SELECT b FROM BoletinRNC b WHERE b.consolRestRechazadas = :consolRestRechazadas"),
    @NamedQuery(name = "BoletinRNC.findByResiReportadas", query = "SELECT b FROM BoletinRNC b WHERE b.resiReportadas = :resiReportadas"),
    @NamedQuery(name = "BoletinRNC.findByResiAprobadas", query = "SELECT b FROM BoletinRNC b WHERE b.resiAprobadas = :resiAprobadas"),
    @NamedQuery(name = "BoletinRNC.findByResiRechazadas", query = "SELECT b FROM BoletinRNC b WHERE b.resiRechazadas = :resiRechazadas"),
    @NamedQuery(name = "BoletinRNC.findByResiNovedades", query = "SELECT b FROM BoletinRNC b WHERE b.resiNovedades = :resiNovedades"),
    @NamedQuery(name = "BoletinRNC.findByConsolResiReportadas", query = "SELECT b FROM BoletinRNC b WHERE b.consolResiReportadas = :consolResiReportadas"),
    @NamedQuery(name = "BoletinRNC.findByConsolResiAprobadas", query = "SELECT b FROM BoletinRNC b WHERE b.consolResiAprobadas = :consolResiAprobadas"),
    @NamedQuery(name = "BoletinRNC.findByConsolResiRechazadas", query = "SELECT b FROM BoletinRNC b WHERE b.consolResiRechazadas = :consolResiRechazadas")})
public class BoletinRNC implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "COD_CARGA")
    private Long codCarga;
    @Column(name = "LICE_REPORTADAS")
    private BigInteger liceReportadas;
    @Column(name = "LICE_APROBADAS")
    private BigInteger liceAprobadas;
    @Column(name = "LICE_RECHAZADAS")
    private BigInteger liceRechazadas;
    @Column(name = "LICE_NOVEDADES")
    private BigInteger liceNovedades;
    @Column(name = "CONSOL_LICE_REPORTADAS")
    private BigInteger consolLiceReportadas;
    @Column(name = "CONSOL_LICE_APROBADAS")
    private BigInteger consolLiceAprobadas;
    @Column(name = "CONSOL_LICE_RECHAZADAS")
    private BigInteger consolLiceRechazadas;
    @Column(name = "PERS_REPORTADAS")
    private BigInteger persReportadas;
    @Column(name = "PERS_APROBADAS")
    private BigInteger persAprobadas;
    @Column(name = "PERS_RECHAZADAS")
    private BigInteger persRechazadas;
    @Column(name = "PERS_NOVEDADES")
    private BigInteger persNovedades;
    @Column(name = "CONSOL_PERS_REPORTADAS")
    private BigInteger consolPersReportadas;
    @Column(name = "CONSOL_PERS_APROBADAS")
    private BigInteger consolPersAprobadas;
    @Column(name = "CONSOL_PERS_RECHAZADAS")
    private BigInteger consolPersRechazadas;
    @Column(name = "REST_REPORTADAS")
    private BigInteger restReportadas;
    @Column(name = "REST_APROBADAS")
    private BigInteger restAprobadas;
    @Column(name = "REST_RECHAZADAS")
    private BigInteger restRechazadas;
    @Column(name = "REST_NOVEDADES")
    private BigInteger restNovedades;
    @Column(name = "CONSOL_REST_REPORTADAS")
    private BigInteger consolRestReportadas;
    @Column(name = "CONSOL_REST_APROBADAS")
    private BigInteger consolRestAprobadas;
    @Column(name = "CONSOL_REST_RECHAZADAS")
    private BigInteger consolRestRechazadas;
    @Column(name = "RESI_REPORTADAS")
    private BigInteger resiReportadas;
    @Column(name = "RESI_APROBADAS")
    private BigInteger resiAprobadas;
    @Column(name = "RESI_RECHAZADAS")
    private BigInteger resiRechazadas;
    @Column(name = "RESI_NOVEDADES")
    private BigInteger resiNovedades;
    @Column(name = "CONSOL_RESI_REPORTADAS")
    private BigInteger consolResiReportadas;
    @Column(name = "CONSOL_RESI_APROBADAS")
    private BigInteger consolResiAprobadas;
    @Column(name = "CONSOL_RESI_RECHAZADAS")
    private BigInteger consolResiRechazadas;

    public BoletinRNC() {
        //Default constructor
    }

    public BoletinRNC(Long codCarga) {
        this.codCarga = codCarga;
    }

    public Long getCodCarga() {
        return codCarga;
    }

    public void setCodCarga(Long codCarga) {
        this.codCarga = codCarga;
    }

    public BigInteger getLiceReportadas() {
        return liceReportadas;
    }

    public void setLiceReportadas(BigInteger liceReportadas) {
        this.liceReportadas = liceReportadas;
    }

    public BigInteger getLiceAprobadas() {
        return liceAprobadas;
    }

    public void setLiceAprobadas(BigInteger liceAprobadas) {
        this.liceAprobadas = liceAprobadas;
    }

    public BigInteger getLiceRechazadas() {
        return liceRechazadas;
    }

    public void setLiceRechazadas(BigInteger liceRechazadas) {
        this.liceRechazadas = liceRechazadas;
    }

    public BigInteger getLiceNovedades() {
        return liceNovedades;
    }

    public void setLiceNovedades(BigInteger liceNovedades) {
        this.liceNovedades = liceNovedades;
    }

    public BigInteger getConsolLiceReportadas() {
        return consolLiceReportadas;
    }

    public void setConsolLiceReportadas(BigInteger consolLiceReportadas) {
        this.consolLiceReportadas = consolLiceReportadas;
    }

    public BigInteger getConsolLiceAprobadas() {
        return consolLiceAprobadas;
    }

    public void setConsolLiceAprobadas(BigInteger consolLiceAprobadas) {
        this.consolLiceAprobadas = consolLiceAprobadas;
    }

    public BigInteger getConsolLiceRechazadas() {
        return consolLiceRechazadas;
    }

    public void setConsolLiceRechazadas(BigInteger consolLiceRechazadas) {
        this.consolLiceRechazadas = consolLiceRechazadas;
    }

    public BigInteger getPersReportadas() {
        return persReportadas;
    }

    public void setPersReportadas(BigInteger persReportadas) {
        this.persReportadas = persReportadas;
    }

    public BigInteger getPersAprobadas() {
        return persAprobadas;
    }

    public void setPersAprobadas(BigInteger persAprobadas) {
        this.persAprobadas = persAprobadas;
    }

    public BigInteger getPersRechazadas() {
        return persRechazadas;
    }

    public void setPersRechazadas(BigInteger persRechazadas) {
        this.persRechazadas = persRechazadas;
    }

    public BigInteger getPersNovedades() {
        return persNovedades;
    }

    public void setPersNovedades(BigInteger persNovedades) {
        this.persNovedades = persNovedades;
    }

    public BigInteger getConsolPersReportadas() {
        return consolPersReportadas;
    }

    public void setConsolPersReportadas(BigInteger consolPersReportadas) {
        this.consolPersReportadas = consolPersReportadas;
    }

    public BigInteger getConsolPersAprobadas() {
        return consolPersAprobadas;
    }

    public void setConsolPersAprobadas(BigInteger consolPersAprobadas) {
        this.consolPersAprobadas = consolPersAprobadas;
    }

    public BigInteger getConsolPersRechazadas() {
        return consolPersRechazadas;
    }

    public void setConsolPersRechazadas(BigInteger consolPersRechazadas) {
        this.consolPersRechazadas = consolPersRechazadas;
    }

    public BigInteger getRestReportadas() {
        return restReportadas;
    }

    public void setRestReportadas(BigInteger restReportadas) {
        this.restReportadas = restReportadas;
    }

    public BigInteger getRestAprobadas() {
        return restAprobadas;
    }

    public void setRestAprobadas(BigInteger restAprobadas) {
        this.restAprobadas = restAprobadas;
    }

    public BigInteger getRestRechazadas() {
        return restRechazadas;
    }

    public void setRestRechazadas(BigInteger restRechazadas) {
        this.restRechazadas = restRechazadas;
    }

    public BigInteger getRestNovedades() {
        return restNovedades;
    }

    public void setRestNovedades(BigInteger restNovedades) {
        this.restNovedades = restNovedades;
    }

    public BigInteger getConsolRestReportadas() {
        return consolRestReportadas;
    }

    public void setConsolRestReportadas(BigInteger consolRestReportadas) {
        this.consolRestReportadas = consolRestReportadas;
    }

    public BigInteger getConsolRestAprobadas() {
        return consolRestAprobadas;
    }

    public void setConsolRestAprobadas(BigInteger consolRestAprobadas) {
        this.consolRestAprobadas = consolRestAprobadas;
    }

    public BigInteger getConsolRestRechazadas() {
        return consolRestRechazadas;
    }

    public void setConsolRestRechazadas(BigInteger consolRestRechazadas) {
        this.consolRestRechazadas = consolRestRechazadas;
    }

    public BigInteger getResiReportadas() {
        return resiReportadas;
    }

    public void setResiReportadas(BigInteger resiReportadas) {
        this.resiReportadas = resiReportadas;
    }

    public BigInteger getResiAprobadas() {
        return resiAprobadas;
    }

    public void setResiAprobadas(BigInteger resiAprobadas) {
        this.resiAprobadas = resiAprobadas;
    }

    public BigInteger getResiRechazadas() {
        return resiRechazadas;
    }

    public void setResiRechazadas(BigInteger resiRechazadas) {
        this.resiRechazadas = resiRechazadas;
    }

    public BigInteger getResiNovedades() {
        return resiNovedades;
    }

    public void setResiNovedades(BigInteger resiNovedades) {
        this.resiNovedades = resiNovedades;
    }

    public BigInteger getConsolResiReportadas() {
        return consolResiReportadas;
    }

    public void setConsolResiReportadas(BigInteger consolResiReportadas) {
        this.consolResiReportadas = consolResiReportadas;
    }

    public BigInteger getConsolResiAprobadas() {
        return consolResiAprobadas;
    }

    public void setConsolResiAprobadas(BigInteger consolResiAprobadas) {
        this.consolResiAprobadas = consolResiAprobadas;
    }

    public BigInteger getConsolResiRechazadas() {
        return consolResiRechazadas;
    }

    public void setConsolResiRechazadas(BigInteger consolResiRechazadas) {
        this.consolResiRechazadas = consolResiRechazadas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codCarga != null ? codCarga.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof BoletinRNC)) {
            return false;
        }
        BoletinRNC other = (BoletinRNC) object;
        if ((this.codCarga == null && other.codCarga != null) || (this.codCarga != null && !this.codCarga.equals(other.codCarga))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.runt.sagir.entities.BoletinRNC[ codCarga=" + codCarga + " ]";
    }
    
}
