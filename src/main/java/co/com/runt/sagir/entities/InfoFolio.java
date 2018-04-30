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
 * @author Hmoreno
 */
@Entity
@SqlResultSetMappings({
    @SqlResultSetMapping(name = "consultaInfoFolio",
            entities = {
                @EntityResult(entityClass = InfoFolio.class,
                        fields = {
                            @FieldResult(name = "idFolio", column = "ARCHMIGRA_IDENTIFIC")
                            ,
                            @FieldResult(name = "fechaEnvio", column = "ARCHMIGRA_FECHENVIO")
                        })
            })
})
public class InfoFolio implements Serializable {

    @Id
    private Long idFolio;

    private String fechaEnvio;

    public Long getIdFolio() {
        return idFolio;
    }

    public void setIdFolio(Long idFolio) {
        this.idFolio = idFolio;
    }

    public String getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(String fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

}
