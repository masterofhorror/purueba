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
@SqlResultSetMappings ({
    @SqlResultSetMapping(name = "consultaRechazoRNA", entities = {
        @EntityResult (entityClass = ConsultaRechazosRNAMigra.class, fields = {
            @FieldResult(name = "placa",column = "PLACA"),
            @FieldResult(name = "secretaria",column = "SECRETARIA"),
            @FieldResult(name = "carga",column = "CARGA"),
            @FieldResult(name = "codCriterio",column = "CODCRITERIO"),
            @FieldResult(name = "criterio",column = "CRITERIO")
        })
    })
})
public class ConsultaRechazosRNAMigra implements Serializable{
    
    @Id
    private String placa;
    private String secretaria;
    private String carga;
    private String codCriterio;
    private String criterio;

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

    public String getCarga() {
        return carga;
    }

    public void setCarga(String carga) {
        this.carga = carga;
    }

    public String getCodCriterio() {
        return codCriterio;
    }

    public void setCodCriterio(String codCriterio) {
        this.codCriterio = codCriterio;
    }

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    
}
