/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author dsalamanca
 */
@Entity
@Table(name = "PROPIETARIOS_VEHICULO", schema = "MIGRACIONQX")
@Cacheable
@NamedQueries({
    @NamedQuery(name = "PropietariosVehiculo.findAll", query = "SELECT p FROM PropietariosVehiculo p"),
    @NamedQuery(name = "PropietariosVehiculo.findByCountCodCarga", query = "SELECT COUNT(p) FROM PropietariosVehiculo p WHERE p.codCarga = :codCarga AND p.migradoPropietar IS NOT NULL AND p.migradoPropietar = 'M'"),
    @NamedQuery(name = "PropietariosVehiculo.findByCodCarga", query = "SELECT p FROM PropietariosVehiculo p WHERE p.codCarga = :codCarga AND p.indValido = 1 AND COALESCE(p.migradoPropietar, 'M') = 'N'")
})
public class PropietariosVehiculo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "NRO_PLACA")
    private String nroPlaca;
    @Column(name = "ID_SECRETARIA")
    private Long idSecretaria;
    @Column(name = "ID_USUARIO")
    private String idUsuario;
    @Column(name = "ID_DOCUMENTO")
    private String idDocumento;
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "ARCHIVO_CARGUE")
    private String archivoCargue;
    @Column(name = "IND_PROINDIVISO")
    private String indProindiviso;
    @Column(name = "IND_VALIDO")
    private Integer indValido;
    @Column(name = "COD_CRITERIO")
    private Long codCriterio;
    @Column(name = "MIGRADO_PERSONA")
    private String migradoPersona;
    @Column(name = "MIGRADO_PROPIETAR")
    private String migradoPropietar;
    @Id
    @Column(name = "COD_CARGA")
    private Integer codCarga;
    @Column(name = "NOVEDAD")
    private String novedad;
    @Column(name = "FECHA_NOVEDAD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNovedad;
    @Column(name = "NOV_COD_CARGANT")
    private Long novCodCargant;
    @Column(name = "COD_CARGA_TRASLADO")
    private Long codCargaTraslado;
    @Column(name = "NRO_PLACA_REPETIDA")
    private String nroPlacaReperida;

    public PropietariosVehiculo() {
        //Default constructor
    }

    public PropietariosVehiculo(String nroPlaca, Long idSecretaria, String idUsuario, String idDocumento, Date fecha, String archivoCargue, String indProindiviso, Integer indValido, Long codCriterio, String migradoPersona, String migradoPropietar, Integer codCarga, String novedad, Date fechaNovedad, Long novCodCargant, Long codCargaTraslado, String nroPlacaReperida) {
        this.nroPlaca = nroPlaca;
        this.idSecretaria = idSecretaria;
        this.idUsuario = idUsuario;
        this.idDocumento = idDocumento;
        this.fecha = fecha;
        this.archivoCargue = archivoCargue;
        this.indProindiviso = indProindiviso;
        this.indValido = indValido;
        this.codCriterio = codCriterio;
        this.migradoPersona = migradoPersona;
        this.migradoPropietar = migradoPropietar;
        this.codCarga = codCarga;
        this.novedad = novedad;
        this.fechaNovedad = fechaNovedad;
        this.novCodCargant = novCodCargant;
        this.codCargaTraslado = codCargaTraslado;
        this.nroPlacaReperida = nroPlacaReperida;
    }

    public String getNroPlaca() {
        return nroPlaca;
    }

    public void setNroPlaca(String nroPlaca) {
        this.nroPlaca = nroPlaca;
    }

    public Long getIdSecretaria() {
        return idSecretaria;
    }

    public void setIdSecretaria(Long idSecretaria) {
        this.idSecretaria = idSecretaria;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getArchivoCargue() {
        return archivoCargue;
    }

    public void setArchivoCargue(String archivoCargue) {
        this.archivoCargue = archivoCargue;
    }

    public String getIndProindiviso() {
        return indProindiviso;
    }

    public void setIndProindiviso(String indProindiviso) {
        this.indProindiviso = indProindiviso;
    }

    public Integer getIndValido() {
        return indValido;
    }

    public void setIndValido(Integer indValido) {
        this.indValido = indValido;
    }

    public Long getCodCriterio() {
        return codCriterio;
    }

    public void setCodCriterio(Long codCriterio) {
        this.codCriterio = codCriterio;
    }

    public String getMigradoPersona() {
        return migradoPersona;
    }

    public void setMigradoPersona(String migradoPersona) {
        this.migradoPersona = migradoPersona;
    }

    public String getMigradoPropietar() {
        return migradoPropietar;
    }

    public void setMigradoPropietar(String migradoPropietar) {
        this.migradoPropietar = migradoPropietar;
    }

    public Integer getCodCarga() {
        return codCarga;
    }

    public void setCodCarga(Integer codCarga) {
        this.codCarga = codCarga;
    }

    public String getNovedad() {
        return novedad;
    }

    public void setNovedad(String novedad) {
        this.novedad = novedad;
    }

    public Date getFechaNovedad() {
        return fechaNovedad;
    }

    public void setFechaNovedad(Date fechaNovedad) {
        this.fechaNovedad = fechaNovedad;
    }

    public Long getNovCodCargant() {
        return novCodCargant;
    }

    public void setNovCodCargant(Long novCodCargant) {
        this.novCodCargant = novCodCargant;
    }

    public Long getCodCargaTraslado() {
        return codCargaTraslado;
    }

    public void setCodCargaTraslado(Long codCargaTraslado) {
        this.codCargaTraslado = codCargaTraslado;
    }

    public String getNroPlacaReperida() {
        return nroPlacaReperida;
    }

    public void setNroPlacaReperida(String nroPlacaReperida) {
        this.nroPlacaReperida = nroPlacaReperida;
    }    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.nroPlaca);
        hash = 79 * hash + Objects.hashCode(this.idSecretaria);
        hash = 79 * hash + Objects.hashCode(this.idUsuario);
        hash = 79 * hash + Objects.hashCode(this.idDocumento);
        hash = 79 * hash + Objects.hashCode(this.codCarga);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PropietariosVehiculo other = (PropietariosVehiculo) obj;
        if (!Objects.equals(this.nroPlaca, other.nroPlaca)) {
            return false;
        }
        if (!Objects.equals(this.idSecretaria, other.idSecretaria)) {
            return false;
        }
        if (!Objects.equals(this.idUsuario, other.idUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idDocumento, other.idDocumento)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        return Objects.equals(this.codCarga, other.codCarga);
    }

    @Override
    public String toString() {
        return "PropietariosVehiculo{" + "nroPlaca=" + nroPlaca + ", idSecretaria=" + idSecretaria + ", idUsuario=" + idUsuario + ", idDocumento=" + idDocumento + ", fecha=" + fecha + ", codCarga=" + codCarga + '}';
    } 

}
