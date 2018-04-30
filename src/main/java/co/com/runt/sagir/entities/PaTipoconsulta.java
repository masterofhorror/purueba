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
 * @author Dospina
 */
@Entity
@Table(name = "PA_TIPOCONSULTA", schema = "CSWCONSULTAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PaTipoconsulta.findAll", query = "SELECT p FROM PaTipoconsulta p"),
    @NamedQuery(name = "PaTipoconsulta.findByTipoconsultaCodigo", query = "SELECT p FROM PaTipoconsulta p WHERE p.tipoconsultaCodigo = :tipoconsultaCodigo"),
    @NamedQuery(name = "PaTipoconsulta.findByTipoconsultaNombre", query = "SELECT p FROM PaTipoconsulta p WHERE p.tipoconsultaNombre = :tipoconsultaNombre"),
    @NamedQuery(name = "PaTipoconsulta.findByTipoconsultaDescripcion", query = "SELECT p FROM PaTipoconsulta p WHERE p.tipoconsultaDescripcion = :tipoconsultaDescripcion")})
public class PaTipoconsulta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "TIPOCONSULTA_CODIGO")
    private String tipoconsultaCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "TIPOCONSULTA_NOMBRE")
    private String tipoconsultaNombre;
    @Size(max = 300)
    @Column(name = "TIPOCONSULTA_DESCRIPCION")
    private String tipoconsultaDescripcion;

    public PaTipoconsulta() {
        //Default constructor
    }

    public PaTipoconsulta(String tipoconsultaCodigo) {
        this.tipoconsultaCodigo = tipoconsultaCodigo;
    }

    public PaTipoconsulta(String tipoconsultaCodigo, String tipoconsultaNombre) {
        this.tipoconsultaCodigo = tipoconsultaCodigo;
        this.tipoconsultaNombre = tipoconsultaNombre;
    }

    public String getTipoconsultaCodigo() {
        return tipoconsultaCodigo;
    }

    public void setTipoconsultaCodigo(String tipoconsultaCodigo) {
        this.tipoconsultaCodigo = tipoconsultaCodigo;
    }

    public String getTipoconsultaNombre() {
        return tipoconsultaNombre;
    }

    public void setTipoconsultaNombre(String tipoconsultaNombre) {
        this.tipoconsultaNombre = tipoconsultaNombre;
    }

    public String getTipoconsultaDescripcion() {
        return tipoconsultaDescripcion;
    }

    public void setTipoconsultaDescripcion(String tipoconsultaDescripcion) {
        this.tipoconsultaDescripcion = tipoconsultaDescripcion;
    }

//    @XmlTransient
//    @JsonIgnore
//    public List<LogConsulta> getLogConsultaList() {
//        return logConsultaList;
//    }
//
//    public void setLogConsultaList(List<LogConsulta> logConsultaList) {
//        this.logConsultaList = logConsultaList;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipoconsultaCodigo != null ? tipoconsultaCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof PaTipoconsulta)) {
            return false;
        }
        PaTipoconsulta other = (PaTipoconsulta) object;
        return !((this.tipoconsultaCodigo == null && other.tipoconsultaCodigo != null) || (this.tipoconsultaCodigo != null && !this.tipoconsultaCodigo.equals(other.tipoconsultaCodigo)));
    }

    @Override
    public String toString() {
        return "co.com.runt.consultacomparendos.entidades.PaTipoconsulta[ tipoconsultaCodigo=" + tipoconsultaCodigo + " ]";
    }

}
