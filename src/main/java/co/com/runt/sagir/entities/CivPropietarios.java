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
    @SqlResultSetMapping(name = "civPropietarios", entities = {
        @EntityResult(entityClass = CivPropietarios.class, fields = {
            @FieldResult(name = "linea", column = "LINEA"),
            @FieldResult(name = "placa", column = "PLACA"),
            @FieldResult(name = "codCriterio", column = "CODCRITERIO"),
            @FieldResult(name = "descripcionError", column = "DESCRIPCION"),
            @FieldResult(name = "secretaria", column = "SECRETARIA"),
            @FieldResult(name = "codCarga", column = "CODCARGA")
        })
    })
})
public class CivPropietarios implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    private Long linea;
    private String placa;
    private Long codCriterio;
    private String descripcionError;
    private String secretaria;
    private Long codCarga;

    public Long getLinea() {
        return linea;
    }

    public void setLinea(Long linea) {
        this.linea = linea;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Long getCodCriterio() {
        return codCriterio;
    }

    public void setCodCriterio(Long codCriterio) {
        this.codCriterio = codCriterio;
    }

    public String getDescripcionError() {
        return descripcionError;
    }

    public void setDescripcionError(String descripcionError) {
        this.descripcionError = descripcionError;
    }

    public String getSecretaria() {
        return secretaria;
    }

    public void setSecretaria(String secretaria) {
        this.secretaria = secretaria;
    }

    public Long getCodCarga() {
        return codCarga;
    }

    public void setCodCarga(Long codCarga) {
        this.codCarga = codCarga;
    }
    
}
