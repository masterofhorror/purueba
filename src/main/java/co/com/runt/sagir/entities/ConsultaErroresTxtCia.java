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
 * @author apena
 */
@Entity
@SqlResultSetMappings({
    @SqlResultSetMapping(name = "consultaErroresTxtCia", entities = {
        @EntityResult (entityClass = ConsultaErroresTxtCia.class, fields = {
            @FieldResult(name = "regConsul", column = "REG_CONSUL"),
            @FieldResult(name = "nroComparendo", column = "NRO_COMPARENDO"),
            @FieldResult(name = "tipoDocAlumno", column = "TIPO_DOC_ALUMNO"),
            @FieldResult(name = "nroDocAlumno", column = "NRO_DOC_ALUMNO"),
            @FieldResult(name = "nroCertificado", column = "NRO_CERTIFICADO"),
            @FieldResult(name = "secValidacion", column = "SEC_VALIDACION"),
            @FieldResult(name = "nombreArchivo", column = "NOMBRE_ARCHIVO"),
            @FieldResult(name = "codCriterio", column = "COD_CRITERIO"),
            @FieldResult(name = "errorEstandar", column = "ERROR_ESTANDAR")
        })
    })
})
public class ConsultaErroresTxtCia implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    private String regConsul;
    private String nroComparendo;
    private String tipoDocAlumno;
    private String nroDocAlumno;
    private String nroCertificado;
    private String secValidacion;
    private String nombreArchivo;
    private String codCriterio;
    private String errorEstandar;

    public String getRegConsul() {
        return regConsul;
    }

    public void setRegConsul(String regConsul) {
        this.regConsul = regConsul;
    }

    public String getNroComparendo() {
        return nroComparendo;
    }

    public void setNroComparendo(String nroComparendo) {
        this.nroComparendo = nroComparendo;
    }

    public String getTipoDocAlumno() {
        return tipoDocAlumno;
    }

    public void setTipoDocAlumno(String tipoDocAlumno) {
        this.tipoDocAlumno = tipoDocAlumno;
    }

    public String getNroDocAlumno() {
        return nroDocAlumno;
    }

    public void setNroDocAlumno(String nroDocAlumno) {
        this.nroDocAlumno = nroDocAlumno;
    }

    public String getNroCertificado() {
        return nroCertificado;
    }

    public void setNroCertificado(String nroCertificado) {
        this.nroCertificado = nroCertificado;
    }

    public String getSecValidacion() {
        return secValidacion;
    }

    public void setSecValidacion(String secValidacion) {
        this.secValidacion = secValidacion;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getCodCriterio() {
        return codCriterio;
    }

    public void setCodCriterio(String codCriterio) {
        this.codCriterio = codCriterio;
    }

    public String getErrorEstandar() {
        return errorEstandar;
    }

    public void setErrorEstandar(String errorEstandar) {
        this.errorEstandar = errorEstandar;
    }

   
}
