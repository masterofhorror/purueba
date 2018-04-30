/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
 * @author APENA
 */
@Entity
@Table(name = "ARC_CAR_ADICIONAL", schema = "MIGRACIONQX")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ArcCarAdicional.findAll", query = "SELECT a FROM ArcCarAdicional a"),
    @NamedQuery(name = "ArcCarAdicional.findByNumreg", query = "SELECT a FROM ArcCarAdicional a WHERE a.numreg = :numreg"),
    @NamedQuery(name = "ArcCarAdicional.findByTipoarc", query = "SELECT a FROM ArcCarAdicional a WHERE a.tipoarc = :tipoarc"),
    @NamedQuery(name = "ArcCarAdicional.findByCodestado", query = "SELECT a FROM ArcCarAdicional a WHERE a.codestado = :codestado"),
    @NamedQuery(name = "ArcCarAdicional.findByNomarc", query = "SELECT a FROM ArcCarAdicional a WHERE a.nomarc = :nomarc"),
    @NamedQuery(name = "ArcCarAdicional.findByDeserr", query = "SELECT a FROM ArcCarAdicional a WHERE a.deserr = :deserr"),
    @NamedQuery(name = "ArcCarAdicional.findByNomot", query = "SELECT a FROM ArcCarAdicional a WHERE a.nomot = :nomot"),
    @NamedQuery(name = "ArcCarAdicional.findByDesreg", query = "SELECT a FROM ArcCarAdicional a WHERE a.desreg = :desreg"),
    @NamedQuery(name = "ArcCarAdicional.findByCodProceso", query = "SELECT a FROM ArcCarAdicional a WHERE a.codProceso = :codProceso"),
    @NamedQuery(name = "ArcCarAdicional.findByFecProceso", query = "SELECT a FROM ArcCarAdicional a WHERE a.fecProceso = :fecProceso"),
    @NamedQuery(name = "ArcCarAdicional.findByCodCarga", query = "SELECT a FROM ArcCarAdicional a WHERE a.codCarga = :codCarga"),
    @NamedQuery(name = "ArcCarAdicional.findByIdentificador", query = "SELECT a FROM ArcCarAdicional a WHERE a.identificador = :identificador")})
public class ArcCarAdicional implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "NUMREG")
    private Long numreg;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIPOARC")
    private short tipoarc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODESTADO")
    private short codestado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "NOMARC")
    private String nomarc;
    @Size(max = 4000)
    @Column(name = "DESERR")
    private String deserr;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "NOMOT")
    private String nomot;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "DESREG")
    private String desreg;
    @Column(name = "COD_PROCESO")
    private Integer codProceso;
    @Column(name = "FEC_PROCESO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecProceso;
    @Column(name = "COD_CARGA")
    private BigInteger codCarga;
    @Size(max = 20)
    @Column(name = "IDENTIFICADOR")
    private String identificador;

    public ArcCarAdicional() {
    }

    public ArcCarAdicional(Long numreg) {
        this.numreg = numreg;
    }

    public ArcCarAdicional(Long numreg, short tipoarc, short codestado, String nomarc, String nomot, String desreg) {
        this.numreg = numreg;
        this.tipoarc = tipoarc;
        this.codestado = codestado;
        this.nomarc = nomarc;
        this.nomot = nomot;
        this.desreg = desreg;
    }

    public Long getNumreg() {
        return numreg;
    }

    public void setNumreg(Long numreg) {
        this.numreg = numreg;
    }

    public short getTipoarc() {
        return tipoarc;
    }

    public void setTipoarc(short tipoarc) {
        this.tipoarc = tipoarc;
    }

    public short getCodestado() {
        return codestado;
    }

    public void setCodestado(short codestado) {
        this.codestado = codestado;
    }

    public String getNomarc() {
        return nomarc;
    }

    public void setNomarc(String nomarc) {
        this.nomarc = nomarc;
    }

    public String getDeserr() {
        return deserr;
    }

    public void setDeserr(String deserr) {
        this.deserr = deserr;
    }

    public String getNomot() {
        return nomot;
    }

    public void setNomot(String nomot) {
        this.nomot = nomot;
    }

    public String getDesreg() {
        return desreg;
    }

    public void setDesreg(String desreg) {
        this.desreg = desreg;
    }

    public Integer getCodProceso() {
        return codProceso;
    }

    public void setCodProceso(Integer codProceso) {
        this.codProceso = codProceso;
    }

    public Date getFecProceso() {
        return fecProceso;
    }

    public void setFecProceso(Date fecProceso) {
        this.fecProceso = fecProceso;
    }

    public BigInteger getCodCarga() {
        return codCarga;
    }

    public void setCodCarga(BigInteger codCarga) {
        this.codCarga = codCarga;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numreg != null ? numreg.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ArcCarAdicional)) {
            return false;
        }
        ArcCarAdicional other = (ArcCarAdicional) object;
        if ((this.numreg == null && other.numreg != null) || (this.numreg != null && !this.numreg.equals(other.numreg))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.runt.sagir.entities.ArcCarAdicional[ numreg=" + numreg + " ]";
    }
    
}
