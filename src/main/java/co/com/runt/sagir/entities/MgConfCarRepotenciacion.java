/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.entities;

import java.io.Serializable;
import java.util.Date;
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
@Cacheable
@Table(name = "MG_CONF_CAR_REPOTENCIACION", schema = "MIGRACIONQX")
@NamedQueries({
    @NamedQuery(name = "MgConfCarRepotenciacion.findByNombreArchivo", query = "SELECT m FROM MgConfCarRepotenciacion m WHERE m.nombreArchivo = :nombreArchivo"),
    @NamedQuery(name = "MgConfCarRepotenciacion.findByNroTicket", query = "SELECT m FROM MgConfCarRepotenciacion m WHERE m.nombreArchivo LIKE CONCAT('%',:nroTicket,'%')")
})
public class MgConfCarRepotenciacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "MIGRACIONQX.MG_SEQ_CAR_REPOT", allocationSize = 1, sequenceName = "MIGRACIONQX.MG_SEQ_CAR_REPOT")
    @GeneratedValue (generator = "MIGRACIONQX.MG_SEQ_CAR_REPOT", strategy = GenerationType.SEQUENCE)
    @Column(name = "COD_CARGA")
    private Long codCarga;
    @Column(name = "FECHA_CARGA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCarga;
    @Column(name = "ID_SECRETARIA")
    private Long idSecretaria;
    @Column(name = "NOM_ARCHIVO")
    private String nombreArchivo;
    @Column(name = "CANT_REPORTA")
    private Integer cantidadReportada;
    @Column(name = "CANT_ACTUALIZADOS")
    private Integer cantidadActualizados;
    @Column(name = "COD_ESTADO")
    private Integer codEstado;

    public Long getCodCarga() {
        return codCarga;
    }

    public void setCodCarga(Long codCarga) {
        this.codCarga = codCarga;
    }

    public Date getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(Date fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public Long getIdSecretaria() {
        return idSecretaria;
    }

    public void setIdSecretaria(Long idSecretaria) {
        this.idSecretaria = idSecretaria;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public Integer getCantidadReportada() {
        return cantidadReportada;
    }

    public void setCantidadReportada(Integer cantidadReportada) {
        this.cantidadReportada = cantidadReportada;
    }

    public Integer getCantidadActualizados() {
        return cantidadActualizados;
    }

    public void setCantidadActualizados(Integer cantidadActualizados) {
        this.cantidadActualizados = cantidadActualizados;
    }

    public Integer getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(Integer codEstado) {
        this.codEstado = codEstado;
    }
    
    

}
