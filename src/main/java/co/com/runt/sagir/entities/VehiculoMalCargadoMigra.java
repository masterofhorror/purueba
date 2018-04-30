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
    @SqlResultSetMapping(name = "vehiculoMalCargadoMigra", entities = {
        @EntityResult(entityClass = VehiculoMalCargadoMigra.class, fields = {
            @FieldResult(name = "idVehiculo", column = "IDVEHICULO"),
            @FieldResult(name = "placa", column = "PLACA"),
            @FieldResult(name = "idAutoridad", column = "IDAUTORIDAD"),
            @FieldResult(name = "secretaria", column = "SECRETARIA"),
            @FieldResult(name = "carga", column = "CARGA")
        })
    })
})
public class VehiculoMalCargadoMigra implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    
    private Long idVehiculo;
    private String placa;
    private Long idAutoridad;
    private String secretaria;
    @Id
    private Integer carga;

    public Long getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Long idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Long getIdAutoridad() {
        return idAutoridad;
    }

    public void setIdAutoridad(Long idAutoridad) {
        this.idAutoridad = idAutoridad;
    }

    public String getSecretaria() {
        return secretaria;
    }

    public void setSecretaria(String secretaria) {
        this.secretaria = secretaria;
    }

    public Integer getCarga() {
        return carga;
    }

    public void setCarga(Integer carga) {
        this.carga = carga;
    }
    
    
    
}
