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
    @SqlResultSetMapping (name = "consultaRangoPlaca", entities = {
        @EntityResult(entityClass = ConsultaRangoPlaca.class, fields = {
            @FieldResult(name = "nroRegistro", column = "NROREGISTRO"),
            @FieldResult(name = "placa", column = "PLACA"),
            @FieldResult(name = "organismoTransito", column = "ORGANISMOTRANSITO")
        })
    })
})
public class ConsultaRangoPlaca implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    private Integer nroRegistro;
    private String placa;
    private String organismoTransito;

    public Integer getNroRegistro() {
        return nroRegistro;
    }

    public void setNroRegistro(Integer nroRegistro) {
        this.nroRegistro = nroRegistro;
    }
    
    

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getOrganismoTransito() {
        return organismoTransito;
    }

    public void setOrganismoTransito(String organismoTransito) {
        this.organismoTransito = organismoTransito;
    }
    
    
}
