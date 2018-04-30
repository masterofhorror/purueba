/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;

/**
 *
 * @author dsalamanca
 */
@Entity
@SqlResultSetMappings({
    @SqlResultSetMapping(name = "civPersonas", entities = {
        @EntityResult(entityClass = CivPersonas.class, fields = {
            @FieldResult(name = "linea", column = "LINEA"),
            @FieldResult(name = "codCarga", column = "CODCARGA"),
            @FieldResult(name = "tipoDent", column = "PERTIPID"),
            @FieldResult(name = "nroDent", column = "PERIDENT"),
            @FieldResult(name = "codCriterio", column = "PERCODCRIT"),
            @FieldResult(name = "descripcionError", column = "DESCRIPCION")
        })
    })
})
public class CivPersonas implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    private Long linea;
    private Long codCarga;
    private String nroDent;
    private String tipoDent;
    private Long codCriterio;
    private String descripcionError;

    public Long getLinea() {
        return linea;
    }

    public void setLinea(Long linea) {
        this.linea = linea;
    }

    public Long getCodCarga() {
        return codCarga;
    }

    public void setCodCarga(Long codCarga) {
        this.codCarga = codCarga;
    }

    public String getNroDent() {
        return nroDent;
    }

    public void setNroDent(String nroDent) {
        this.nroDent = nroDent;
    }

    public String getTipoDent() {
        return tipoDent;
    }

    public void setTipoDent(String tipoDent) {
        this.tipoDent = tipoDent;
    }

    public Long getCodCriterio() {
        return codCriterio;
    }

    public void setCodCriterio(Long codCriterio) {
        this.codCriterio = codCriterio;
    }

    public String getDescripcionError() {
        return descripcionError;
    }

    public void setDescripcionError(String descripcionError) {
        this.descripcionError = descripcionError;
    }

}
