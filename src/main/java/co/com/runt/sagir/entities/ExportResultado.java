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
    @SqlResultSetMapping(name = "resultado", entities = {
        @EntityResult(entityClass = ExportResultado.class, fields = {
            @FieldResult(name = "total", column = "TOTAL"),
            @FieldResult(name = "tabla", column = "TABLA"),
            @FieldResult(name = "descMigrado", column = "DESC_MIGRADO")
        })
    })
})
public class ExportResultado implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    private String tabla;
    private String descMigrado;
    private Long total;

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public String getDescMigrado() {
        return descMigrado;
    }

    public void setDescMigrado(String descMigrado) {
        this.descMigrado = descMigrado;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
    
    
    
}
