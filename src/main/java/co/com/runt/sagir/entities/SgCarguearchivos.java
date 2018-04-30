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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author dsalamanca
 */
@Entity
@Table(name = "TBL_CARGUEARCHIVOS", schema = "CSWSAGIR")
@Cacheable
@NamedQueries({
    @NamedQuery(name = "SgCarguearchivos.findAll", query = "SELECT s FROM SgCarguearchivos s"),
    @NamedQuery(name = "SgCarguearchivos.findByCarguearchivosId", query = "SELECT s FROM SgCarguearchivos s WHERE s.carguearchivosId = :codCarga"),
    @NamedQuery(name = "SgCarguearchivos.findByCarguearchivosFecha", query = "SELECT s FROM SgCarguearchivos s WHERE s.carguearchivosFecha = :carguearchivosFecha"),
    @NamedQuery(name = "SgCarguearchivos.findByCarguearchivosUsuario", query = "SELECT s FROM SgCarguearchivos s WHERE s.carguearchivosUsuario = :carguearchivosUsuario"),
    @NamedQuery(name = "SgCarguearchivos.findByCarguearchivosNombreDatos", query = "SELECT s FROM SgCarguearchivos s WHERE s.carguearchivosNombreDatos = :carguearchivosNombreDatos"),
    @NamedQuery(name = "SgCarguearchivos.findByCarguearchivosEstado", query = "SELECT s FROM SgCarguearchivos s WHERE s.carguearchivosEstado = :carguearchivosEstado AND s.tipoCargue = 'PROP'"),
    @NamedQuery(name = "SgCarguearchivos.findByConsultaArchivo", query = "SELECT s.carguearchivosDatos FROM SgCarguearchivos s WHERE s.carguearchivosEstado = :carguearchivosEstado AND s.tipoCargue = 'PROP'"),
    @NamedQuery(name = "SgCarguearchivos.findByCarguearchivosIdautra", query = "SELECT s FROM SgCarguearchivos s WHERE s.carguearchivosIdautra = :carguearchivosIdautra AND s.carguearchivosFecha >= :fecha AND s.tipoCargue = 'PROP' ORDER BY s.carguearchivosFecha DESC"),
    @NamedQuery(name = "SgCarguearchivos.findByFecha", query = "SELECT s FROM SgCarguearchivos s WHERE s.carguearchivosIdautra = :carguearchivosIdautra AND FUNC('TRUNC',s.carguearchivosFecha) = FUNC('TRUNC',:fecha) AND s.tipoCargue = 'PROP'"),
    @NamedQuery(name = "SgCarguearchivos.findByNombreYRutaArchivo", query = "SELECT s FROM SgCarguearchivos s WHERE s.carguearchivosDatos = :carguearchivosDatos AND s.carguearchivosNombreDatos = :carguearchivosNombreDatos"),
    @NamedQuery(name = "SgCarguearchivos.findByTicket", query = "SELECT s FROM SgCarguearchivos s WHERE s.carguearchivosDatos LIKE CONCAT('%',:nroTicket,'%') AND s.carguearchivosEstado = 'SIN_PROCESAR'"),
    @NamedQuery(name = "SgCarguearchivos.findCountByTicket", query = "SELECT COUNT(s) FROM SgCarguearchivos s WHERE s.carguearchivosDatos LIKE CONCAT('%',:nroTicket,'%')"),
    @NamedQuery(name = "SgCarguearchivos.findByByIdSecretariaProcMigra", query = "SELECT s FROM SgCarguearchivos s WHERE s.carguearchivosDatos LIKE CONCAT('%',:idSecretaria,'%') AND s.tipoCargue = 'PROCMIGRARNC' AND s.carguearchivosFecha = CURRENT_DATE AND s.carguearchivosEstado = 'SIN_PROCESAR'"),
    @NamedQuery(name = "SgCarguearchivos.findProcMigraByNombreArchivo", query = "SELECT s FROM SgCarguearchivos s WHERE s.carguearchivosDatos LIKE CONCAT('%',:nombreArchivo,'%') AND s.tipoCargue = 'PROCMIGRARNC' AND s.carguearchivosEstado = 'SIN_PROCESAR'"),
    @NamedQuery(name = "SgCarguearchivos.findCountByTicketProcesoMigracionRNC", query = "SELECT COUNT(s) FROM SgCarguearchivos s WHERE s.carguearchivosDatos LIKE CONCAT('%',:nrtoTicket,'%') AND s.tipoCargue = 'PROCMIGRARNC'"),
    @NamedQuery(name = "SgCarguearchivos.findPartFile", query = "SELECT s FROM SgCarguearchivos s WHERE s.carguearchivosDatos = :pathFile AND s.carguearchivosEstado = 'SIN_PROCESAR'"),
    @NamedQuery(name = "SgCarguearchivos.findByAutoridad", query = "SELECT COUNT(s) FROM SgCarguearchivos s WHERE s.carguearchivosNombreDatos LIKE CONCAT('%',:autoridad,'%') AND FUNC('TRUNC', s.carguearchivosFecha) = FUNC('TRUNC', :fecha)"),
    @NamedQuery(name = "SgCarguearchivos.findByCarguearchivosIp", query = "SELECT s FROM SgCarguearchivos s WHERE s.carguearchivosIp = :carguearchivosIp")})
public class SgCarguearchivos implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(name = "CSWSAGIR.SG_CARGUEARCHIVOS_SEQ", allocationSize = 1, sequenceName = "CSWSAGIR.SG_CARGUEARCHIVOS_SEQ")
    @GeneratedValue(generator = "CSWSAGIR.SG_CARGUEARCHIVOS_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "CARGUEARCHIVOS_ID")
    private Long carguearchivosId;
    @Basic(optional = false)
    @Column(name = "CARGUEARCHIVOS_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date carguearchivosFecha;
    @Basic(optional = false)
    @Size(min = 1, max = 50)
    @Column(name = "CARGUEARCHIVOS_USUARIO")
    private String carguearchivosUsuario;
    @Basic(optional = false)
    @Size(min = 1, max = 100)
    @Column(name = "CARGUEARCHIVOS_NOMBRE_DATOS")
    private String carguearchivosNombreDatos;
    @Column (name = "CARGUEARCHIVOS_CARGA")
    private Integer idCarga;
    
    @Column (name = "CARGUEARCHIVOS_TIPOCARGUE")
    private String tipoCargue;

    @Column(name = "CARGUEARCHIVOS_DATOS")
    private String carguearchivosDatos;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CARGUEARCHIVOS_ESTADO")
    private String carguearchivosEstado;

    @Size(max = 15)
    @Column(name = "CARGUEARCHIVOS_IDAUTRA")
    private String carguearchivosIdautra;
    @Size(max = 50)
    @Column(name = "CARGUEARCHIVOS_IP")
    private String carguearchivosIp;

    public SgCarguearchivos() {
        //Constructor vacio
    }

    public SgCarguearchivos(Long carguearchivosId) {
        this.carguearchivosId = carguearchivosId;
    }

    public SgCarguearchivos(Long carguearchivosId, Date carguearchivosFecha, String carguearchivosUsuario, String carguearchivosNombreDatos, String carguearchivosDatos, String carguearchivosEstado) {
        this.carguearchivosId = carguearchivosId;
        this.carguearchivosFecha = carguearchivosFecha;
        this.carguearchivosUsuario = carguearchivosUsuario;
        this.carguearchivosNombreDatos = carguearchivosNombreDatos;
        this.carguearchivosDatos = carguearchivosDatos;
        this.carguearchivosEstado = carguearchivosEstado;
    }

    public Long getCarguearchivosId() {
        return carguearchivosId;
    }

    public void setCarguearchivosId(Long carguearchivosId) {
        this.carguearchivosId = carguearchivosId;
    }

    public String getTipoCargue() {
        return tipoCargue;
    }

    public void setTipoCargue(String tipoCargue) {
        this.tipoCargue = tipoCargue;
    }

    public Date getCarguearchivosFecha() {
        return carguearchivosFecha;
    }

    public void setCarguearchivosFecha(Date carguearchivosFecha) {
        this.carguearchivosFecha = carguearchivosFecha;
    }

    public String getCarguearchivosUsuario() {
        return carguearchivosUsuario;
    }

    public void setCarguearchivosUsuario(String carguearchivosUsuario) {
        this.carguearchivosUsuario = carguearchivosUsuario;
    }

    public String getCarguearchivosNombreDatos() {
        return carguearchivosNombreDatos;
    }

    public void setCarguearchivosNombreDatos(String carguearchivosNombreDatos) {
        this.carguearchivosNombreDatos = carguearchivosNombreDatos;
    }

     public String getCarguearchivosDatos() {
        return carguearchivosDatos;
    }

    public void setCarguearchivosDatos(String carguearchivosDatos) {
        this.carguearchivosDatos = carguearchivosDatos;
    }

    public String getCarguearchivosEstado() {
        return carguearchivosEstado;
    }

    public void setCarguearchivosEstado(String carguearchivosEstado) {
        this.carguearchivosEstado = carguearchivosEstado;
    }

    public String getCarguearchivosIdautra() {
        return carguearchivosIdautra;
    }

    public void setCarguearchivosIdautra(String carguearchivosIdautra) {
        this.carguearchivosIdautra = carguearchivosIdautra;
    }

    public String getCarguearchivosIp() {
        return carguearchivosIp;
    }

    public void setCarguearchivosIp(String carguearchivosIp) {
        this.carguearchivosIp = carguearchivosIp;
    }

    public Integer getIdCarga() {
        return idCarga;
    }

    public void setIdCarga(Integer idCarga) {
        this.idCarga = idCarga;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (carguearchivosId != null ? carguearchivosId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) { 
        if (!(object instanceof SgCarguearchivos)) {
            return false;
        }
        SgCarguearchivos other = (SgCarguearchivos) object;
        if ((this.carguearchivosId == null && other.carguearchivosId != null) || (this.carguearchivosId != null && !this.carguearchivosId.equals(other.carguearchivosId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.runt.sagir.entities.SgCarguearchivos[ carguearchivosId=" + carguearchivosId + " ]";
    }

}
