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
    @SqlResultSetMapping(name = "consultaRechazosRNCMigra",entities = {
        @EntityResult(entityClass = ConsultaRechazosRNCMigra.class, fields = {
            @FieldResult (name = "tipoDoc", column = "TIPODOC"),
            @FieldResult (name = "nroDoc", column = "NRODOC"),
            @FieldResult (name = "nroLic", column = "NROLIC"),
            @FieldResult (name = "carga", column = "CARGA"),
            @FieldResult (name = "codCriterio", column = "CODCRITERIO"),
            @FieldResult (name = "criterio", column = "CRITERIO"),
            @FieldResult (name = "fechaTramite", column = "FECHATRAMITE"),
            @FieldResult (name = "categoria", column = "CATEGORIA")
        })
    })
})
public class ConsultaRechazosRNCMigra implements Serializable{
    
    @Id
    private String nroLic;
    private String tipoDoc;
    private String nroDoc;    
    private String carga;
    private String codCriterio;
    private String criterio;
    private String fechaTramite;
    private String categoria;

    public String getNroLic() {
        return nroLic;
    }

    public void setNroLic(String nroLic) {
        this.nroLic = nroLic;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(String nroDoc) {
        this.nroDoc = nroDoc;
    }

    public String getCarga() {
        return carga;
    }

    public void setCarga(String carga) {
        this.carga = carga;
    }

    public String getCodCriterio() {
        return codCriterio;
    }

    public void setCodCriterio(String codCriterio) {
        this.codCriterio = codCriterio;
    }

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    public String getFechaTramite() {
        return fechaTramite;
    }

    public void setFechaTramite(String fechaTramite) {
        this.fechaTramite = fechaTramite;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    
    
}
