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
    @SqlResultSetMapping(name = "vehiculoMalCargadoProd", entities = {
        @EntityResult(entityClass = VehiculoMalCargadoProd.class, fields = {
            @FieldResult(name = "idVehiculo", column = "IDVEHICULO"),
            @FieldResult(name = "idauttra", column = "IDAUTTRA"),
            @FieldResult(name = "autoridad", column = "AUTORIDAD"),
            @FieldResult(name = "estadoVehiculo", column = "ESTADOVEHICULO"),
            @FieldResult(name = "placa", column = "PLACA")
        })
    })
})
public class VehiculoMalCargadoProd implements Serializable{
    
     private static final long serialVersionUID = 1L;
     
     @Id
     private Long idVehiculo;
     private Long idauttra;
     private String autoridad;
     private String estadoVehiculo;
     private String placa;

    public Long getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Long idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public Long getIdauttra() {
        return idauttra;
    }

    public void setIdauttra(Long idauttra) {
        this.idauttra = idauttra;
    }

    public String getAutoridad() {
        return autoridad;
    }

    public void setAutoridad(String autoridad) {
        this.autoridad = autoridad;
    }

    public String getEstadoVehiculo() {
        return estadoVehiculo;
    }

    public void setEstadoVehiculo(String estadoVehiculo) {
        this.estadoVehiculo = estadoVehiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
     
     
    
}
