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
    @SqlResultSetMapping(name = "boletinCambioPropietario", entities = {
        @EntityResult(entityClass = BoletinCambioPropietario.class, fields = {
            @FieldResult(name = "usuario", column = "USUARIO"),
            @FieldResult(name = "placa", column = "PLACA"),
            @FieldResult(name = "estado", column = "ESTADO"),
            @FieldResult(name = "nombreOrganismo", column = "NOMBREORGANISMO")
        })
    })
})
public class BoletinCambioPropietario implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private String usuario;
    @Id
    private String placa;
    private String estado;
    private String nombreOrganismo;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombreOrganismo() {
        return nombreOrganismo;
    }

    public void setNombreOrganismo(String nombreOrganismo) {
        this.nombreOrganismo = nombreOrganismo;
    }
    
    
    
}
