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
    @SqlResultSetMapping(name = "consultaFinalRNC", entities = {
        @EntityResult(entityClass = ConsultaRNCFinalMigra.class, fields = {
            @FieldResult(name = "nroLc", column = "NROLC"),
            @FieldResult(name = "categoria", column = "CATEGORIA"),
            @FieldResult(name = "autoridad", column = "AUTORIDAD"),
            @FieldResult(name = "ot", column = "OT"),
            @FieldResult(name = "idPersona", column = "IDPERSONA"),
            @FieldResult(name = "tipoDoc", column = "TIPODOC"),
            @FieldResult(name = "nroDoc", column = "NRODOC"),
            @FieldResult(name = "nroCertCrc", column = "NROCERTCRC"),
            @FieldResult(name = "nroSolicitud", column = "NROSOLICITUD"),
            @FieldResult(name = "motivoCancelacion", column = "MOTIVOCANCELACION"),
            @FieldResult(name = "nroCertCea", column = "NROCERTCEA"),
            @FieldResult(name = "licExtranj", column = "LICEXTRANJ"),
            @FieldResult(name = "estadoImpre", column = "ESTADOIMPRE"),
            @FieldResult(name = "fechaExp", column = "FECHAEXP"),
            @FieldResult(name = "fechaNovedad", column = "FECHANOVEDAD"),
            @FieldResult(name = "fechaVenci", column = "FECHAVENCI"),
            @FieldResult(name = "estado", column = "ESTADO"),
            @FieldResult(name = "liceconduMigrado", column = "LICECONDUMIGRADO"),
            @FieldResult(name = "fechaMigra", column = "FECHAMIGRA"),
            @FieldResult(name = "fechaActua", column = "FECHAACTUA"),
            @FieldResult(name = "fechaCance", column = "FECHACANCE"),
            @FieldResult(name = "login", column = "LOGIN"),
            @FieldResult(name = "fechaCaptu", column = "FECHACAPTU"),
            @FieldResult(name = "categoriAnt", column = "CATEGORIANT"),
            @FieldResult(name = "descMigrado", column = "DESCMIGRADO"),
            @FieldResult(name = "idPersonaProduccion", column = "IDPERSONAPRODUCCION"),
            @FieldResult(name = "nroLcProducc", column = "NROLCPRODUCC"),
            @FieldResult(name = "fechaQa", column = "FECHAQA")
        })
    })
})
public class ConsultaRNCFinalMigra implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    private String nroLc;
    private String categoria;
    private String autoridad;
    private String ot;
    private String idPersona;
    private String tipoDoc;
    private String nroDoc;
    private String nroCertCrc;
    private String nroSolicitud;
    private String motivoCancelacion;
    private String nroCertCea;
    private String licExtranj;
    private String estadoImpre;
    private String fechaExp;
    private String fechaNovedad;
    private String fechaVenci;
    private String estado;
    private String liceconduMigrado;
    private String fechaMigra;
    private String fechaActua;
    private String fechaCance;
    private String login;
    private String fechaCaptu;
    private String categoriAnt;
    private String descMigrado;
    private String idPersonaProduccion;
    private String nroLcProducc;
    private String fechaQa;

    public String getNroLc() {
        return nroLc;
    }

    public void setNroLc(String nroLc) {
        this.nroLc = nroLc;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getAutoridad() {
        return autoridad;
    }

    public void setAutoridad(String autoridad) {
        this.autoridad = autoridad;
    }

    public String getOt() {
        return ot;
    }

    public void setOt(String ot) {
        this.ot = ot;
    }

    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(String nroDoc) {
        this.nroDoc = nroDoc;
    }

    public String getNroCertCrc() {
        return nroCertCrc;
    }

    public void setNroCertCrc(String nroCertCrc) {
        this.nroCertCrc = nroCertCrc;
    }

    public String getNroSolicitud() {
        return nroSolicitud;
    }

    public void setNroSolicitud(String nroSolicitud) {
        this.nroSolicitud = nroSolicitud;
    }

    public String getMotivoCancelacion() {
        return motivoCancelacion;
    }

    public void setMotivoCancelacion(String motivoCancelacion) {
        this.motivoCancelacion = motivoCancelacion;
    }

    public String getNroCertCea() {
        return nroCertCea;
    }

    public void setNroCertCea(String nroCertCea) {
        this.nroCertCea = nroCertCea;
    }

    public String getLicExtranj() {
        return licExtranj;
    }

    public void setLicExtranj(String licExtranj) {
        this.licExtranj = licExtranj;
    }

    public String getEstadoImpre() {
        return estadoImpre;
    }

    public void setEstadoImpre(String estadoImpre) {
        this.estadoImpre = estadoImpre;
    }

    public String getFechaExp() {
        return fechaExp;
    }

    public void setFechaExp(String fechaExp) {
        this.fechaExp = fechaExp;
    }

    public String getFechaNovedad() {
        return fechaNovedad;
    }

    public void setFechaNovedad(String fechaNovedad) {
        this.fechaNovedad = fechaNovedad;
    }

    public String getFechaVenci() {
        return fechaVenci;
    }

    public void setFechaVenci(String fechaVenci) {
        this.fechaVenci = fechaVenci;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getLiceconduMigrado() {
        return liceconduMigrado;
    }

    public void setLiceconduMigrado(String liceconduMigrado) {
        this.liceconduMigrado = liceconduMigrado;
    }

    public String getFechaMigra() {
        return fechaMigra;
    }

    public void setFechaMigra(String fechaMigra) {
        this.fechaMigra = fechaMigra;
    }

    public String getFechaActua() {
        return fechaActua;
    }

    public void setFechaActua(String fechaActua) {
        this.fechaActua = fechaActua;
    }

    public String getFechaCance() {
        return fechaCance;
    }

    public void setFechaCance(String fechaCance) {
        this.fechaCance = fechaCance;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFechaCaptu() {
        return fechaCaptu;
    }

    public void setFechaCaptu(String fechaCaptu) {
        this.fechaCaptu = fechaCaptu;
    }

    public String getCategoriAnt() {
        return categoriAnt;
    }

    public void setCategoriAnt(String categoriAnt) {
        this.categoriAnt = categoriAnt;
    }

    public String getDescMigrado() {
        return descMigrado;
    }

    public void setDescMigrado(String descMigrado) {
        this.descMigrado = descMigrado;
    }

    public String getIdPersonaProduccion() {
        return idPersonaProduccion;
    }

    public void setIdPersonaProduccion(String idPersonaProduccion) {
        this.idPersonaProduccion = idPersonaProduccion;
    }

    public String getNroLcProducc() {
        return nroLcProducc;
    }

    public void setNroLcProducc(String nroLcProducc) {
        this.nroLcProducc = nroLcProducc;
    }

    public String getFechaQa() {
        return fechaQa;
    }

    public void setFechaQa(String fechaQa) {
        this.fechaQa = fechaQa;
    }
    
    

}
