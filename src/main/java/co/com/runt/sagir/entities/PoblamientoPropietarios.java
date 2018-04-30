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
    @SqlResultSetMapping(name = "poblamientoPropietarios", entities = {
        @EntityResult(entityClass = PoblamientoPropietarios.class, fields = {
            @FieldResult(name = "linea", column = "LINEA"),
            @FieldResult(name = "secretaria", column = "SECRETARIA"),
            @FieldResult(name = "placa", column = "PLACA"),
            @FieldResult(name = "tipoDocumento", column = "TIP_DOCUMENTO"),
            @FieldResult(name = "nroDocumento", column = "NRO_DOCUMENTO"),
            @FieldResult(name = "fecha", column = "FECHA"),
            @FieldResult(name = "codCriterio", column = "COD_CRITERIO"),
            @FieldResult(name = "descripcionError", column = "DESCRIPCION_DEL_ERROR")
        })
    })
})
public class PoblamientoPropietarios implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    private Long linea;
    private String secretaria;
    private String placa;
    private String tipoDocumento;
    private String nroDocumento;
    private String fecha;
    private Long codCriterio;
    private String descripcionError;

    public Long getLinea() {
        return linea;
    }

    public void setLinea(Long linea) {
        this.linea = linea;
    }

    public String getSecretaria() {
        return secretaria;
    }

    public void setSecretaria(String secretaria) {
        this.secretaria = secretaria;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
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
    
}
