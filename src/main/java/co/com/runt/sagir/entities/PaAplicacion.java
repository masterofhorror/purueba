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
@Table(name = "PA_APLICACION", schema = "CSWCONSULTAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PaAplicacion.findAll", query = "SELECT p FROM PaAplicacion p"),
    @NamedQuery(name = "PaAplicacion.findByAplicacionCodigo", query = "SELECT p FROM PaAplicacion p WHERE p.aplicacionCodigo = :aplicacionCodigo"),
    @NamedQuery(name = "PaAplicacion.findByAplicacionNombre", query = "SELECT p FROM PaAplicacion p WHERE p.aplicacionNombre = :aplicacionNombre")})
public class PaAplicacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "APLICACION_CODIGO")
    private String aplicacionCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "APLICACION_NOMBRE")
    private String aplicacionNombre;

    public PaAplicacion() {
        //Default constructor
    }

    public PaAplicacion(String aplicacionCodigo) {
        this.aplicacionCodigo = aplicacionCodigo;
    }

    public PaAplicacion(String aplicacionCodigo, String aplicacionNombre) {
        this.aplicacionCodigo = aplicacionCodigo;
        this.aplicacionNombre = aplicacionNombre;
    }

    public String getAplicacionCodigo() {
        return aplicacionCodigo;
    }

    public void setAplicacionCodigo(String aplicacionCodigo) {
        this.aplicacionCodigo = aplicacionCodigo;
    }

    public String getAplicacionNombre() {
        return aplicacionNombre;
    }

    public void setAplicacionNombre(String aplicacionNombre) {
        this.aplicacionNombre = aplicacionNombre;
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
        hash += (aplicacionCodigo != null ? aplicacionCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof PaAplicacion)) {
            return false;
        }
        PaAplicacion other = (PaAplicacion) object;
        return !((this.aplicacionCodigo == null && other.aplicacionCodigo != null) || (this.aplicacionCodigo != null && !this.aplicacionCodigo.equals(other.aplicacionCodigo)));
    }

    @Override
    public String toString() {
        return "co.com.runt.consultacomparendos.entidades.PaAplicacion[ aplicacionCodigo=" + aplicacionCodigo + " ]";
    }

}
