/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.entities;

import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author dsalamanca
 */
@Entity
@Cacheable
@Table(name = "ORGANISMOS_TRANSITO", schema = "MIGRUNT1")
@NamedQueries({
    @NamedQuery(name = "OrganismosTransitoMigrunt.findByAll", query = "SELECT o FROM OrganismosTransitoMigrunt o ORDER BY o.descripcion ASC"),
    @NamedQuery(name = "OrganismosTransitoMigrunt.findByIdSecretaria", query = "SELECT o FROM OrganismosTransitoMigrunt o WHERE o.idSecretaria = :idSecretaria")
})
public class OrganismosTransitoMigrunt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_SECRETARIA")
    private Long idSecretaria;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "TIPO_ORGANISMO")
    private String tipoOrganismo;
    @Column(name = "TIPO_ESTANDARD")
    private Integer tipoEstandard;
    @Column(name = "DESCRIPCION_CORTA")
    private String descripcionCorta;
    @Column(name = "ESTADO")
    private String estado;

    public Long getIdSecretaria() {
        return idSecretaria;
    }

    public void setIdSecretaria(Long idSecretaria) {
        this.idSecretaria = idSecretaria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoOrganismo() {
        return tipoOrganismo;
    }

    public void setTipoOrganismo(String tipoOrganismo) {
        this.tipoOrganismo = tipoOrganismo;
    }

    public Integer getTipoEstandard() {
        return tipoEstandard;
    }

    public void setTipoEstandard(Integer tipoEstandard) {
        this.tipoEstandard = tipoEstandard;
    }

    public String getDescripcionCorta() {
        return descripcionCorta;
    }

    public void setDescripcionCorta(String descripcionCorta) {
        this.descripcionCorta = descripcionCorta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    

}
