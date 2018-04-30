/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.entities;

import co.com.runt.sagir.dto.ArchivoPropietarioDTO;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dsalamanca
 */
@Entity
@Table(name = "ARC_CAR", schema = "MIGRACIONQX")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ArcCar.findAll", query = "SELECT a FROM ArcCar a"),
    @NamedQuery(name = "ArcCar.findByNumreg", query = "SELECT COUNT(a) FROM ArcCar a WHERE a.numreg = :numreg"),
    @NamedQuery(name = "ArcCar.findByCodestado", query = "SELECT a FROM ArcCar a WHERE a.codestado = :codestado"),
    @NamedQuery(name = "ArcCar.findByNomarc", query = "SELECT a FROM ArcCar a WHERE a.nomarc = :nomarc"),
    @NamedQuery(name = "ArcCar.findByDeserr", query = "SELECT a FROM ArcCar a WHERE a.deserr = :deserr"),
    @NamedQuery(name = "ArcCar.findByNomot", query = "SELECT a FROM ArcCar a WHERE a.nomot = :nomot"),
    @NamedQuery(name = "ArcCar.findByDesreg", query = "SELECT a FROM ArcCar a WHERE a.desreg = :desreg"),
    @NamedQuery(name = "ArcCar.findByFecProceso", query = "SELECT a FROM ArcCar a WHERE a.fecProceso = :fecProceso"),
    @NamedQuery(name = "ArcCar.findByCodCarga", query = "SELECT a FROM ArcCar a WHERE a.codCarga = :codCarga"),
    @NamedQuery(name = "ArcCar.findByIdentificador", query = "SELECT a FROM ArcCar a WHERE a.identificador = :identificador"),
    @NamedQuery(name = "ArcCar.findByObservacion", query = "SELECT a FROM ArcCar a WHERE a.observacion = :observacion"),
    @NamedQuery(name = "ArcCar.findByCodCargaError", query = "SELECT a FROM ArcCar a WHERE a.codCargaError = :codCargaError")})
public class ArcCar implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator (name = "MIGRACIONQX.SEQ_ARC_CAR", allocationSize = 1, sequenceName = "MIGRACIONQX.SEQ_ARC_CAR")
    @GeneratedValue (generator = "MIGRACIONQX.SEQ_ARC_CAR", strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "NUMREG")
    private Long numreg;
    @Basic(optional = false)
    @Column(name = "TIPOARC")
    private Integer tipoArc;
    @Basic(optional = false)
    @Column(name = "CODESTADO")
    private Integer codestado;
    @Basic(optional = false)
    @Column(name = "NOMARC")
    private String nomarc;
    @Column(name = "DESERR")
    private String deserr;
    @Basic(optional = false)
    @Column(name = "NOMOT")
    private String nomot;
    @Basic(optional = false)
    @Column(name = "DESREG")
    private String desreg;
    @Column(name = "FEC_PROCESO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecProceso;
    @Column(name = "COD_CARGA")
    private Integer codCarga;
    @Column(name = "IDENTIFICADOR")
    private String identificador;
    @Column(name = "OBSERVACION")
    private String observacion;
    @Column(name = "COD_CARGA_ERROR")
    private Integer codCargaError;
    @JoinColumn(name = "COD_PROCESO", referencedColumnName = "COD_PROCESO")
    @ManyToOne
    private ConfArcCar codProceso;

    public ArcCar() {
        //Constructor vacio
    }

    public ArcCar(Long numreg) {
        this.numreg = numreg;
    }

    public ArcCar(Long numreg, Integer codestado, String nomarc, String nomot, String desreg) {
        this.numreg = numreg;
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

    public Integer getTipoArc() {
        return tipoArc;
    }

    public void setTipoArc(Integer tipoArc) {
        this.tipoArc = tipoArc;
    }

    public Integer getCodestado() {
        return codestado;
    }

    public void setCodestado(Integer codestado) {
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

    public Date getFecProceso() {
        return fecProceso;
    }

    public void setFecProceso(Date fecProceso) {
        this.fecProceso = fecProceso;
    }

    public Integer getCodCarga() {
        return codCarga;
    }

    public void setCodCarga(Integer codCarga) {
        this.codCarga = codCarga;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Integer getCodCargaError() {
        return codCargaError;
    }

    public void setCodCargaError(Integer codCargaError) {
        this.codCargaError = codCargaError;
    }

    public ConfArcCar getCodProceso() {
        return codProceso;
    }

    public void setCodProceso(ConfArcCar codProceso) {
        this.codProceso = codProceso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numreg != null ? numreg.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ArcCar)) {
            return false;
        }
        ArcCar other = (ArcCar) object;
        if ((this.numreg == null && other.numreg != null) || (this.numreg != null && !this.numreg.equals(other.numreg))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mapeoMigracionQx.ArcCar[ numreg=" + numreg + " ]";
    }

    public void setDesreg(List<ArchivoPropietarioDTO> datosArchivo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
