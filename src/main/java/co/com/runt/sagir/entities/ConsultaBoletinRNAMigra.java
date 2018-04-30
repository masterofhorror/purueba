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
 * @author Dsalamanca
 */
@Entity
@SqlResultSetMappings({
    @SqlResultSetMapping(name = "consultaBoletinRNAMigra", entities = {
        @EntityResult(entityClass = ConsultaBoletinRNAMigra.class,fields = {
            @FieldResult(name = "placa", column = "PLACA"),
            @FieldResult(name = "secretaria", column = "SECRETARIA"),
            @FieldResult(name = "fechaCarga", column = "FECHACARGA"),
            @FieldResult(name = "folio", column = "FOLIO"),
            @FieldResult(name = "boletin", column = "BOLETIN"),
            @FieldResult(name = "codCriterio", column = "CODCRITERIO"),
            @FieldResult(name = "descripcion", column = "DESCRIPCION")
        })
    })
})
public class ConsultaBoletinRNAMigra implements Serializable {
    
    @Id
    private String placa;
    private String secretaria;
    private String fechaCarga;
    private String folio;
    private String boletin;
    private String codCriterio;
    private String descripcion;

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getSecretaria() {
        return secretaria;
    }

    public void setSecretaria(String secretaria) {
        this.secretaria = secretaria;
    }

    public String getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(String fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getBoletin() {
        return boletin;
    }

    public void setBoletin(String boletin) {
        this.boletin = boletin;
    }

    public String getCodCriterio() {
        return codCriterio;
    }

    public void setCodCriterio(String codCriterio) {
        this.codCriterio = codCriterio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
}
