/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author dsalamanca
 */
@Entity
@Table(name = "PA_MUNICPIO", schema = "RUNTPROD")
@Cacheable(false)
@NamedQueries({
    @NamedQuery(name = "PaMunicpio.findAll", query = "SELECT p FROM PaMunicpio p"),
    @NamedQuery(name = "PaMunicpio.findByMunicpioDivipol", query = "SELECT COUNT(p) FROM PaMunicpio p WHERE p.municpioDivipol = :municpioDivipol"),
    @NamedQuery(name = "PaMunicpio.findByMunicpioNombre", query = "SELECT p FROM PaMunicpio p WHERE p.municpioNombre = :municpioNombre"),
    @NamedQuery(name = "PaMunicpio.findByMunicpioRegespadu", query = "SELECT p FROM PaMunicpio p WHERE p.municpioRegespadu = :municpioRegespadu")})
public class PaMunicpio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "MUNICPIO_DIVIPOL")
    private Long municpioDivipol;
    @Column (name = "MUNICPIO_DEPARTAME_CODDPTO")
    private int municpioDepartameDoddpto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "MUNICPIO_NOMBRE")
    private String municpioNombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MUNICPIO_REGESPADU")
    private long municpioRegespadu;
    @Column (name = "MUNICPIO_ZOREGADUA_IDREGIMEN")
    private int municpioZoregaduaIdregimen;
    @Column (name = "MUNICPIO_AREAMETRO_NOMBRE")
    private String municpioAreametroNombre; 

    public PaMunicpio() {
        //Default constructor
    }

    public PaMunicpio(Long municpioDivipol) {
        this.municpioDivipol = municpioDivipol;
    }

    public PaMunicpio(Long municpioDivipol, String municpioNombre, long municpioRegespadu) {
        this.municpioDivipol = municpioDivipol;
        this.municpioNombre = municpioNombre;
        this.municpioRegespadu = municpioRegespadu;
    }

    public Long getMunicpioDivipol() {
        return municpioDivipol;
    }

    public void setMunicpioDivipol(Long municpioDivipol) {
        this.municpioDivipol = municpioDivipol;
    }

    public String getMunicpioNombre() {
        return municpioNombre;
    }

    public void setMunicpioNombre(String municpioNombre) {
        this.municpioNombre = municpioNombre;
    }

    public long getMunicpioRegespadu() {
        return municpioRegespadu;
    }

    public void setMunicpioRegespadu(long municpioRegespadu) {
        this.municpioRegespadu = municpioRegespadu;
    }

    public int getMunicpioDepartameDoddpto() {
        return municpioDepartameDoddpto;
    }

    public void setMunicpioDepartameDoddpto(int municpioDepartameDoddpto) {
        this.municpioDepartameDoddpto = municpioDepartameDoddpto;
    }

    public int getMunicpioZoregaduaIdregimen() {
        return municpioZoregaduaIdregimen;
    }

    public void setMunicpioZoregaduaIdregimen(int municpioZoregaduaIdregimen) {
        this.municpioZoregaduaIdregimen = municpioZoregaduaIdregimen;
    }

    public String getMunicpioAreametroNombre() {
        return municpioAreametroNombre;
    }

    public void setMunicpioAreametroNombre(String municpioAreametroNombre) {
        this.municpioAreametroNombre = municpioAreametroNombre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (municpioDivipol != null ? municpioDivipol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof PaMunicpio)) {
            return false;
        }
        PaMunicpio other = (PaMunicpio) object;
        if ((this.municpioDivipol == null && other.municpioDivipol != null) || (this.municpioDivipol != null && !this.municpioDivipol.equals(other.municpioDivipol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.runt.consultarna.entities.PaMunicpio[ municpioDivipol=" + municpioDivipol + " ]";
    }
    
}
