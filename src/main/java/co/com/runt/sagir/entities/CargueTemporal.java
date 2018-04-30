/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author APENA
 */
@Entity
@Table(name = "CARGUE_TEMPORAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CargueTemporal.findAll", query = "SELECT c FROM CargueTemporal c"),
    @NamedQuery(name = "CargueTemporal.findByTempId", query = "SELECT c FROM CargueTemporal c WHERE c.tempId = :tempId"),
    @NamedQuery(name = "CargueTemporal.findByTempDato", query = "SELECT c FROM CargueTemporal c WHERE c.tempDato = :tempDato")})
public class CargueTemporal implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator (name = "CARGUE_TEMPORAL_SEQ_GEN", allocationSize = 1, sequenceName = "CARGUE_TEMPORAL_SEQ")
    @GeneratedValue (generator = "CARGUE_TEMPORAL_SEQ_GEN", strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @NotNull
    @Column(name = "TEMP_ID")
    private BigDecimal tempId;
    @Size(max = 100)
    @Column(name = "TEMP_DATO")
    private String tempDato;

    public CargueTemporal() {
        //Constructor vacio
    }

    public CargueTemporal(BigDecimal tempId) {
        this.tempId = tempId;
    }
    
    public CargueTemporal (BigDecimal tempId, String tempDato){
        this.tempId = tempId;
        this.tempDato = tempDato;
    }

    public BigDecimal getTempId() {
        return tempId;
    }

    public void setTempId(BigDecimal tempId) {
        this.tempId = tempId;
    }

    public String getTempDato() {
        return tempDato;
    }

    public void setTempDato(String tempDato) {
        this.tempDato = tempDato;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tempId != null ? tempId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CargueTemporal)) {
            return false;
        }
        CargueTemporal other = (CargueTemporal) object;
        return !((this.tempId == null && other.tempId != null) || (this.tempId != null && !this.tempId.equals(other.tempId)));
    }

    @Override
    public String toString() {
        return "co.com.runt.sagir.entities.CargueTemporal[ tempId=" + tempId + " ]";
    }
    
}
