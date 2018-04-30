/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
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
@Table(name = "CARGA", schema = "MIGRACIONQX")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Carga.findAll", query = "SELECT c FROM Carga c"),
    @NamedQuery(name = "Carga.findByCountCodCarga", query = "SELECT COUNT(c) FROM Carga c WHERE c.codCarga = :codCarga"),
    @NamedQuery(name = "Carga.findByCodCarga", query = "SELECT c FROM Carga c WHERE c.codCarga = :codCarga AND c.codCarga IS NOT NULL ORDER BY c.fechaCarga DESC, c.codCarga ASC"),
    @NamedQuery(name = "Carga.findByTipoRegistro", query = "SELECT c FROM Carga c WHERE c.tipoRegistro = :tipoRegistro AND c.codCarga IS NOT NULL ORDER BY c.fechaCarga DESC, c.codCarga ASC"),
    @NamedQuery(name = "Carga.findByIdSecretaria", query = "SELECT c FROM Carga c WHERE c.idSecretaria.idSecretaria = :idAutoridad AND c.codCarga IS NOT NULL ORDER BY c.fechaCarga DESC, c.codCarga ASC"),
    @NamedQuery(name = "Carga.findByFechaCarga", query = "SELECT c FROM Carga c WHERE FUNC('TRUNC',c.fechaCarga) >= FUNC('TRUNC',:fechaInicio) AND FUNC('TRUNC',c.fechaCarga) <= FUNC('TRUNC',:fechaFin) AND c.codCarga IS NOT NULL ORDER BY c.fechaCarga DESC, c.codCarga ASC"),
    //Querys para identificar la cantidad de registros de las consultas
    @NamedQuery(name = "Carga.findByCountTipoRegistro", query = "SELECT COUNT(c) FROM Carga c WHERE c.tipoRegistro = :tipoRegistro AND c.codCarga IS NOT NULL"),
    @NamedQuery(name = "Carga.findByCountIdSecretaria", query = "SELECT COUNT(c) FROM Carga c WHERE c.idSecretaria.idSecretaria = :idSecretaria AND c.codCarga IS NOT NULL"),
    @NamedQuery(name = "Carga.findByCountFechaCarga", query = "SELECT COUNT(c) FROM Carga c WHERE c.fechaCarga BETWEEN :fechaInicio AND :fechaFin AND c.codCarga IS NOT NULL"),
    //Fin de las consultas de cantidad de registros
    @NamedQuery(name = "Carga.findByIdFolio", query = "SELECT c FROM Carga c WHERE c.idFolio = :idFolio"),
    @NamedQuery(name = "Carga.findByCodEstado", query = "SELECT c FROM Carga c WHERE c.codEstado = :codEstado"),
    @NamedQuery(name = "Carga.findByIdBoletin", query = "SELECT c FROM Carga c WHERE c.idBoletin = :idBoletin"),
    @NamedQuery(name = "Carga.findAutoridadByCarga", query = "SELECT c FROM Carga c WHERE c.codCarga = :codCarga"),
    @NamedQuery(name = "Carga.findByArchmigraIdentific", query = "SELECT c FROM Carga c WHERE c.archmigraIdentific = :archmigraIdentific")})
public class Carga implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "MIGRACIONQX.SEQ_COD_CARGA", allocationSize = 1, sequenceName = "MIGRACIONQX.SEQ_COD_CARGA")
    @GeneratedValue (generator = "MIGRACIONQX.SEQ_COD_CARGA", strategy = GenerationType.SEQUENCE)
    @Column(name = "COD_CARGA")
    private Integer codCarga;

    @JoinColumn(name = "ID_SECRETARIA", referencedColumnName = "ID_SECRETARIA")
    @OneToOne
    private OrganismosTransitoMigrunt idSecretaria;

    @Column(name = "FECHA_CARGA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCarga;
    @Column(name = "ID_FOLIO")
    private String idFolio;
    @Column(name = "COD_ESTADO")
    private Integer codEstado;
    @Column(name = "ID_BOLETIN")
    private Integer idBoletin;
    @Column(name = "ARCHMIGRA_IDENTIFIC")
    private Long archmigraIdentific;
    @Column(name = "TIPO_REGISTRO")
    private Integer tipoRegistro;

    public Integer getCodCarga() {
        return codCarga;
    }

    public void setCodCarga(Integer codCarga) {
        this.codCarga = codCarga;
    }

    public OrganismosTransitoMigrunt getIdSecretaria() {
        return idSecretaria;
    }

    public void setIdSecretaria(OrganismosTransitoMigrunt idSecretaria) {
        this.idSecretaria = idSecretaria;
    }

    public Integer getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(Integer codEstado) {
        this.codEstado = codEstado;
    }

    public Integer getIdBoletin() {
        return idBoletin;
    }

    public void setIdBoletin(Integer idBoletin) {
        this.idBoletin = idBoletin;
    }

    public Date getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(Date fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public String getIdFolio() {
        return idFolio;
    }

    public void setIdFolio(String idFolio) {
        this.idFolio = idFolio;
    }

    public Long getArchmigraIdentific() {
        return archmigraIdentific;
    }

    public void setArchmigraIdentific(Long archmigraIdentific) {
        this.archmigraIdentific = archmigraIdentific;
    }

    public Integer getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(Integer tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codCarga != null ? codCarga.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Carga)) {
            return false;
        }
        Carga other = (Carga) object;
        return !((this.codCarga == null && other.codCarga != null) || (this.codCarga != null && !this.codCarga.equals(other.codCarga)));
    }

    @Override
    public String toString() {
        return "mapeoMigracionQx.Carga[ codCarga=" + codCarga + " ]";
    }
    
}
