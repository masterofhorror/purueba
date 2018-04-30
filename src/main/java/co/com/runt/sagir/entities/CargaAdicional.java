/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Hmoreno
 */
@Entity
@Table(name = "CARGA_ADICIONAL", schema = "MIGRACIONQX")
@NamedQueries({
    @NamedQuery(name = "CargaAdicional.findAll", query = "SELECT c FROM CargaAdicional c")})
public class CargaAdicional implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "COD_CARGA")
    private Long codCarga;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_SECRETARIA")
    private Long idSecretaria;

    @Column(name = "FECHA_CARGA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCarga;

    @Column(name = "TIPO_REGISTRO")
    private Integer tipoRegistro;

    @Size(max = 100)
    @Column(name = "ID_FOLIO")
    private String idFolio;

    @Column(name = "COD_ESTADO")
    private Integer codEstado;

    @Column(name = "ID_BOLETIN")
    private Long idBoletin;

    public Long getCodCarga() {
        return codCarga;
    }

    public void setCodCarga(Long codCarga) {
        this.codCarga = codCarga;
    }

    public Long getIdSecretaria() {
        return idSecretaria;
    }

    public void setIdSecretaria(Long idSecretaria) {
        this.idSecretaria = idSecretaria;
    }

    public Date getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(Date fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public Integer getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(Integer tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public String getIdFolio() {
        return idFolio;
    }

    public void setIdFolio(String idFolio) {
        this.idFolio = idFolio;
    }

    public Integer getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(Integer codEstado) {
        this.codEstado = codEstado;
    }

    public Long getIdBoletin() {
        return idBoletin;
    }

    public void setIdBoletin(Long idBoletin) {
        this.idBoletin = idBoletin;
    }

}
