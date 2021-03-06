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
    @SqlResultSetMapping(name = "poblamientoRestricciones", entities = {
        @EntityResult(entityClass = PoblamientoRestricciones.class, fields = {
            @FieldResult(name = "linea", column = "LINEA"),
            @FieldResult(name = "codCarga", column = "CODCARGA"),
            @FieldResult(name = "liceCondu", column = "LICECONDU"),
            @FieldResult(name = "codCriterio", column = "COD_CRITERIO"),
            @FieldResult(name = "descripcionError", column = "DESCRIPCION_DEL_ERROR")
        })
    })
})
public class PoblamientoRestricciones implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    private Long linea;
    private Long codCarga;
    private String liceCondu;
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

    public String getLiceCondu() {
        return liceCondu;
    }

    public void setLiceCondu(String liceCondu) {
        this.liceCondu = liceCondu;
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
