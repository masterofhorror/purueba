/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
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
@Table(name = "COM_CONSTANTE", schema = "RUNTPROD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComConstantes.findAll", query = "SELECT c FROM ComConstantes c"),
    @NamedQuery(name = "ComConstantes.findValorByIdGrupoConstante", query = "SELECT c FROM ComConstantes c WHERE c.constanteValor = :constanteValor AND c.constantesPK.constanteGrupo = :constanteGrupo"),
    @NamedQuery(name = "ComConstantes.findValorByNodo", query = "SELECT c FROM ComConstantes c WHERE c.constantesPK.constanteGrupo = :constanteGrupo AND c.constantesPK.constanteNombre = :constanteNombre"),
    @NamedQuery(name = "ComConstantes.findConstante", query = "SELECT c FROM ComConstantes c WHERE c.constantesPK.constanteGrupo = :grupo AND c.constantesPK.constanteNombre = :nombre"),
    @NamedQuery(name = "ComConstantes.findByGrupo", query = "SELECT c FROM ComConstantes c WHERE c.constantesPK.constanteGrupo = :constanteGrupo"),
    @NamedQuery(name = "ComConstantes.findByFechaInicioRunt", query = "SELECT c FROM ComConstantes c WHERE c.constantesPK.constanteNombre = 'FECHA_INICIO_OPERACION_RUNT'"),
    @NamedQuery(name = "ComConstantes.findConstanteNombre", query = "SELECT c FROM ComConstantes c WHERE c.constantesPK.constanteNombre = :constanteNombre"),
    @NamedQuery(name = "ComConstantes.findByConstanteValor", query = "SELECT c FROM ComConstantes c WHERE c.constanteValor = :constanteValor"),
    @NamedQuery(name = "ComConstantes.findByConstanteEstado", query = "SELECT c FROM ComConstantes c WHERE c.constanteEstado = :constanteEstado")})
public class ComConstantes implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ComConstantesPK constantesPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2500)
    @Column(name = "CONSTANTE_VALOR")
    private String constanteValor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CONSTANTE_ESTADO")
    private Character constanteEstado;
    @Lob
    @Column(name = "CONSTANTE_VECTOR")
    private Serializable constanteVector;

    public ComConstantes() {
        //Default constructor
    }

    public ComConstantes(ComConstantesPK constantesPK) {
        this.constantesPK = constantesPK;
    }

    public ComConstantes(ComConstantesPK constantesPK, String constanteValor, Character constanteEstado) {
        this.constantesPK = constantesPK;
        this.constanteValor = constanteValor;
        this.constanteEstado = constanteEstado;
    }

    public ComConstantes(String constanteGrupo, String constanteNombre) {
        this.constantesPK = new ComConstantesPK(constanteGrupo, constanteNombre);
    }

    public ComConstantesPK getConstantesPK() {
        return constantesPK;
    }

    public void setConstantesPK(ComConstantesPK constantesPK) {
        this.constantesPK = constantesPK;
    }

    public String getConstanteValor() {
        return constanteValor;
    }

    public void setConstanteValor(String constanteValor) {
        this.constanteValor = constanteValor;
    }

    public Character getConstanteEstado() {
        return constanteEstado;
    }

    public void setConstanteEstado(Character constanteEstado) {
        this.constanteEstado = constanteEstado;
    }

    public Serializable getConstanteVector() {
        return constanteVector;
    }

    public void setConstanteVector(Serializable constanteVector) {
        this.constanteVector = constanteVector;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (constantesPK != null ? constantesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ComConstantes)) {
            return false;
        }
        ComConstantes other = (ComConstantes) object;
        return !((this.constantesPK == null && other.constantesPK != null) || (this.constantesPK != null && !this.constantesPK.equals(other.constantesPK)));
    }

    @Override
    public String toString() {
        return "co.com.runt.sagir.entities.Constantes[ constantesPK=" + constantesPK + " ]";
    }


    
}
