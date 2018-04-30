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
 * @author Apena
 */

@Entity
@SqlResultSetMappings({
    @SqlResultSetMapping(name = "codigoCarga", entities = {
        @EntityResult (entityClass = CodigoCarga.class, fields = {
            @FieldResult(name = "codCarga", column = "COD_CARGA")
        })
    })
})
public class CodigoCarga implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    private Long codCarga;

    public Long getCodCarga() {
        return codCarga;
    }

    public void setCodCarga(Long codCarga) {
        this.codCarga = codCarga;
    }
    
    
}
