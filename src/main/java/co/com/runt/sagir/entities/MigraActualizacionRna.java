/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author dsalamanca
 */
@Entity
@Cacheable(false)
@Table(name = "MG_ACTUALIZACION_RNA", schema = "MIGRACIONQX")
@NamedQueries({
    @NamedQuery(name = "MigraActualizacionRna.findByAll", query = "SELECT m FROM MigraActualizacionRna m"),
    @NamedQuery(name = "MigraActualizacionRna.findByCountPlacaCodCarga", query = "SELECT COUNT(m) FROM MigraActualizacionRna m WHERE m.placa = :placa AND m.codCarga = :codCarga AND m.estadoProceso IN ('P', 'E', 'V')"),
    @NamedQuery(name = "MigraActualizacionRna.findByPlacaCodCarga", query = "SELECT m FROM MigraActualizacionRna m WHERE m.placa = :placa AND m.codCarga = :codCarga AND m.estadoProceso IN ('P', 'E', 'V')"),
    @NamedQuery(name = "MigraActualizacionRna.findByCountCodCarga", query = "SELECT COUNT(m) FROM MigraActualizacionRna m WHERE m.codCarga = :codCarga AND m.estadoProceso IN ('P', 'E', 'V')"),
    @NamedQuery(name = "MigraActualizacionRna.findByCodCarga", query = "SELECT m FROM MigraActualizacionRna m WHERE m.codCarga = :codCarga AND m.estadoProceso IN ('P', 'E', 'V')")
})
public class MigraActualizacionRna implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "MIGRARUNT.CARGUE_PRODUCCION_SEQ", allocationSize = 1, sequenceName = "MIGRARUNT.CARGUE_PRODUCCION_SEQ")
    @GeneratedValue (generator = "MIGRARUNT.CARGUE_PRODUCCION_SEQ", strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "COD_CARGA_CONTROL")
    private Integer cargaControl;
    @Column(name = "COD_CARGA")
    private Integer codCarga;
    @Column(name = "NRO_PLACA")
    private String placa;
    @Column(name = "ESTADO_PROCESO")
    private String estadoProceso;
    @Column(name = "USUARIO_PROCESA")
    private Integer usuario;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_PROCESO")
    private Date fechaProceso;

    public Integer getCodCarga() {
        return codCarga;
    }

    public void setCodCarga(Integer codCarga) {
        this.codCarga = codCarga;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Integer getCargaControl() {
        return cargaControl;
    }

    public void setCargaControl(Integer cargaControl) {
        this.cargaControl = cargaControl;
    }

    public String getEstadoProceso() {
        return estadoProceso;
    }

    public void setEstadoProceso(String estadoProceso) {
        this.estadoProceso = estadoProceso;
    }

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    public Date getFechaProceso() {
        return fechaProceso;
    }

    public void setFechaProceso(Date fechaProceso) {
        this.fechaProceso = fechaProceso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cargaControl != null ? cargaControl.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {  
        if (!(object instanceof MigraActualizacionRna)) {
            return false;
        }
        MigraActualizacionRna other = (MigraActualizacionRna) object;
        if ((this.cargaControl == null && other.cargaControl != null) || (this.cargaControl != null && !this.cargaControl.equals(other.cargaControl))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "mapeoMigracionQx.MigraActualizacionRna[ cargaControl=" + cargaControl + " ]";
    }
    
}
