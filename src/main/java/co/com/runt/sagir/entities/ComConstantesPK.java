/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author dsalamanca
 */
@Embeddable
public class ComConstantesPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CONSTANTE_GRUPO")
    private String constanteGrupo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "CONSTANTE_NOMBRE")
    private String constanteNombre;

    public ComConstantesPK() {
        //Default constructor
    }

    public ComConstantesPK(String constanteGrupo, String constanteNombre) {
        this.constanteGrupo = constanteGrupo;
        this.constanteNombre = constanteNombre;
    }

    public String getConstanteGrupo() {
        return constanteGrupo;
    }

    public void setConstanteGrupo(String constanteGrupo) {
        this.constanteGrupo = constanteGrupo;
    }

    public String getConstanteNombre() {
        return constanteNombre;
    }

    public void setConstanteNombre(String constanteNombre) {
        this.constanteNombre = constanteNombre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (constanteGrupo != null ? constanteGrupo.hashCode() : 0);
        hash += (constanteNombre != null ? constanteNombre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ComConstantesPK)) {
            return false;
        }
        ComConstantesPK other = (ComConstantesPK) object;
        if ((this.constanteGrupo == null && other.constanteGrupo != null) || (this.constanteGrupo != null && !this.constanteGrupo.equals(other.constanteGrupo))) {
            return false;
        }
        return !((this.constanteNombre == null && other.constanteNombre != null) || (this.constanteNombre != null && !this.constanteNombre.equals(other.constanteNombre)));
    }

    @Override
    public String toString() {
        return "co.com.runt.sagir.entities.ConstantesPK[ constanteGrupo=" + constanteGrupo + ", constanteNombre=" + constanteNombre + " ]";
    }
    
}
