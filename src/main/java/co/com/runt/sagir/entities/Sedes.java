/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.entities;

import java.io.Serializable;
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
@Table(name = "TBL_SEDES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sedes.findAll", query = "SELECT s FROM Sedes s"),
    @NamedQuery(name = "Sedes.findByIdSecretaria", query = "SELECT s FROM Sedes s WHERE s.idSecretaria = :idSecretaria"),
    @NamedQuery(name = "Sedes.findByIdSede", query = "SELECT COUNT(s) FROM Sedes s WHERE s.idSede = :idSede AND s.idSecretaria = :idSecretaria"),
    @NamedQuery(name = "Sedes.findByEstadoSede", query = "SELECT s FROM Sedes s WHERE s.estadoSede = :estadoSede")})
public class Sedes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_SECRETARIA")
    private Long idSecretaria;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_SEDE")
    private Long idSede;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "ESTADO_SEDE")
    private String estadoSede;

    public Sedes() {
        //Default constructor
    }

    public Sedes(Long idSecretaria) {
        this.idSecretaria = idSecretaria;
    }

    public Sedes(Long idSecretaria, long idSede, String estadoSede) {
        this.idSecretaria = idSecretaria;
        this.idSede = idSede;
        this.estadoSede = estadoSede;
    }

    public Long getIdSecretaria() {
        return idSecretaria;
    }

    public void setIdSecretaria(Long idSecretaria) {
        this.idSecretaria = idSecretaria;
    }

    public long getIdSede() {
        return idSede;
    }

    public void setIdSede(long idSede) {
        this.idSede = idSede;
    }

    public String getEstadoSede() {
        return estadoSede;
    }

    public void setEstadoSede(String estadoSede) {
        this.estadoSede = estadoSede;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSecretaria != null ? idSecretaria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Sedes)) {
            return false;
        }
        Sedes other = (Sedes) object;
        if ((this.idSecretaria == null && other.idSecretaria != null) || (this.idSecretaria != null && !this.idSecretaria.equals(other.idSecretaria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.runt.sagir.entities.Sedes[ idSecretaria=" + idSecretaria + " ]";
    }
    
}
