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
    @SqlResultSetMapping(name = "pendietesMigrar", entities = {
        @EntityResult(entityClass = ExportPendientesMigrar.class, fields = {
            @FieldResult(name = "tabla", column = "TABLA"),
            @FieldResult(name = "total", column = "TOTAL")
        })
    })
})
public class ExportPendientesMigrar implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    private String tabla;
    private Long total;

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }  
}
