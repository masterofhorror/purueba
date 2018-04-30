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
    @SqlResultSetMapping(name = "consultaModificacionesRealizadasCasosEspeciales",
            entities = {
                @EntityResult(entityClass = InfoModificacionesRealizadasCasosEspeciales.class,
                        fields = {
                            @FieldResult(name = "id", column = "id")
                            ,
                            @FieldResult(name = "placa", column = "placa")
                            ,
                            @FieldResult(name = "fechaCargue", column = "fecha_cargue")
                            ,
                            @FieldResult(name = "medio", column = "medio")
                            ,
                            @FieldResult(name = "descripcion", column = "desc_proc")
                        })
            })
})
public class InfoModificacionesRealizadasCasosEspeciales implements Serializable {

    @Id
    private Long id;

    private String placa;
    private String fechaCargue;
    private String medio;
    private String descripcion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getFechaCargue() {
        return fechaCargue;
    }

    public void setFechaCargue(String fechaCargue) {
        this.fechaCargue = fechaCargue;
    }

    public String getMedio() {
        return medio;
    }

    public void setMedio(String medio) {
        this.medio = medio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
