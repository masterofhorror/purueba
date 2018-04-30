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
 * @author APENA
 */

@Entity
@SqlResultSetMappings({
    @SqlResultSetMapping(name = "consultaRangoPlacaTotal", entities = {
        @EntityResult(entityClass = ConsultaRangoPlacaTotal.class, fields = {
            @FieldResult(name = "idSecretaria", column = "ID_SECRETARIA"),
            @FieldResult(name = "organismoTransito", column = "ORGANISMOTRANSITO"),
            @FieldResult(name = "total", column = "TOTAL")
        })
    })
})
public class ConsultaRangoPlacaTotal implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    private String idSecretaria;
    private String organismoTransito;
    private String total;

    public String getIdSecretaria() {
        return idSecretaria;
    }

    public void setIdSecretaria(String idSecretaria) {
        this.idSecretaria = idSecretaria;
    }

    public String getOrganismoTransito() {
        return organismoTransito;
    }

    public void setOrganismoTransito(String organismoTransito) {
        this.organismoTransito = organismoTransito;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    
}
