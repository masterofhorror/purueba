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
 * Consulta la información intermedia de migración
 *
 * @author Dsalamanca
 */
@Entity
@SqlResultSetMappings({
    @SqlResultSetMapping(name = "consultaIntermediaMigra", entities = {
        @EntityResult(entityClass = ConsultaIntermediaMigra.class, fields = {
            @FieldResult(name = "idVehic", column = "IDVEHIC"),
            @FieldResult(name = "placa", column = "PLACA"),
            @FieldResult(name = "autoRegistra", column = "AUTOREGISTRA"),
            @FieldResult(name = "secretaria", column = "SECRETARIA"),
            @FieldResult(name = "fechaMigra", column = "FECHAMIGRA"),
            @FieldResult(name = "codCarga", column = "CODCARGA"),
            @FieldResult(name = "estadoVehic", column = "ESTADOVEHIC"),
            @FieldResult(name = "migrado", column = "MIGRADO"),
            @FieldResult(name = "desError", column = "DESERROR")
        })
    })
})
public class ConsultaIntermediaMigra implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idVehic;
    private String placa;
    private String autoRegistra;
    private String secretaria;
    private String fechaMigra;
    @Id
    private String codCarga;
    private String estadoVehic;
    private String migrado;
    private String desError;

    public String getIdVehic() {
        return idVehic;
    }

    public void setIdVehic(String idVehic) {
        this.idVehic = idVehic;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getAutoRegistra() {
        return autoRegistra;
    }

    public void setAutoRegistra(String autoRegistra) {
        this.autoRegistra = autoRegistra;
    }

    public String getSecretaria() {
        return secretaria;
    }

    public void setSecretaria(String secretaria) {
        this.secretaria = secretaria;
    }

    public String getFechaMigra() {
        return fechaMigra;
    }

    public void setFechaMigra(String fechaMigra) {
        this.fechaMigra = fechaMigra;
    }

    public String getCodCarga() {
        return codCarga;
    }

    public void setCodCarga(String codCarga) {
        this.codCarga = codCarga;
    }

    public String getEstadoVehic() {
        return estadoVehic;
    }

    public void setEstadoVehic(String estadoVehic) {
        this.estadoVehic = estadoVehic;
    }

    public String getMigrado() {
        return migrado;
    }

    public void setMigrado(String migrado) {
        this.migrado = migrado;
    }

    public String getDesError() {
        return desError;
    }

    public void setDesError(String desError) {
        this.desError = desError;
    }

   
}
