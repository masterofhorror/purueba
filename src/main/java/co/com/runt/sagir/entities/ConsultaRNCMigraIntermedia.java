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
    @SqlResultSetMapping(name = "consultaRNCMigraIntermedia", entities = {
        @EntityResult(entityClass = ConsultaRNCMigraIntermedia.class, fields = {
            @FieldResult(name = "tipodoc", column = "TIPODOC"),
            @FieldResult(name = "nrodoc", column = "NRODOC"),
            @FieldResult(name = "nrolic", column = "NROLIC"),
            @FieldResult(name = "carga", column = "CARGA"),
            @FieldResult(name = "fechatramite", column = "FECHATRAMITE"),
            @FieldResult(name = "oficinaexpide", column = "OFICINAEXPIDE"),
            @FieldResult(name = "tipotrami", column = "TIPOTRAMI"),
            @FieldResult(name = "categoria", column = "CATEGORIA"),
            @FieldResult(name = "idcea", column = "IDCEA"),
            @FieldResult(name = "divipolcea", column = "DIVIPOLCEA"),
            @FieldResult(name = "nrocercea", column = "NROCERCEA"),
            @FieldResult(name = "estadolc", column = "ESTADOLC"),
            @FieldResult(name = "comprobpago", column = "COMPROBPAGO"),
            @FieldResult(name = "codespven", column = "CODESPVEN"),
            @FieldResult(name = "tipoidentantigua", column = "TIPOIDENTANTIGUA"),
            @FieldResult(name = "nroidentiantigua", column = "NROIDENTIANTIGUA"),
            @FieldResult(name = "categorianueva", column = "CATEGORIANUEVA"),
            @FieldResult(name = "certificadocrc", column = "CERTIFICADOCRC"),
            @FieldResult(name = "fechavenci", column = "FECHAVENCI"),
            @FieldResult(name = "servicio", column = "SERVICIO"),
            @FieldResult(name = "consecutivosirev", column = "CONSECUTIVOSIREV"),
            @FieldResult(name = "migrado", column = "MIGRADO"),
            @FieldResult(name = "homologacion", column = "HOMOLOGACION"),
            @FieldResult(name = "homol1990", column = "HOMOL1990")
        })
    })
})
public class ConsultaRNCMigraIntermedia implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Id
    private String nrolic;
    private String tipodoc;
    private String nrodoc;    
    private String carga;
    private String fechatramite;
    private String oficinaexpide;
    private String tipotrami;
    private String categoria;
    private String idcea;
    private String divipolcea;
    private String nrocercea;
    private String estadolc;
    private String comprobpago;
    private String codespven;
    private String tipoidentantigua;
    private String nroidentiantigua;
    private String categorianueva;
    private String certificadocrc;
    private String fechavenci;
    private String servicio;
    private String consecutivosirev;
    private String migrado;
    private String homologacion;
    private String homol1990;

    public String getTipodoc() {
        return tipodoc;
    }

    public void setTipodoc(String tipodoc) {
        this.tipodoc = tipodoc;
    }

    public String getNrodoc() {
        return nrodoc;
    }

    public void setNrodoc(String nrodoc) {
        this.nrodoc = nrodoc;
    }

    public String getNrolic() {
        return nrolic;
    }

    public void setNrolic(String nrolic) {
        this.nrolic = nrolic;
    }

    public String getCarga() {
        return carga;
    }

    public void setCarga(String carga) {
        this.carga = carga;
    }

    public String getFechatramite() {
        return fechatramite;
    }

    public void setFechatramite(String fechatramite) {
        this.fechatramite = fechatramite;
    }

    public String getOficinaexpide() {
        return oficinaexpide;
    }

    public void setOficinaexpide(String oficinaexpide) {
        this.oficinaexpide = oficinaexpide;
    }

    public String getTipotrami() {
        return tipotrami;
    }

    public void setTipotrami(String tipotrami) {
        this.tipotrami = tipotrami;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getIdcea() {
        return idcea;
    }

    public void setIdcea(String idcea) {
        this.idcea = idcea;
    }

    public String getDivipolcea() {
        return divipolcea;
    }

    public void setDivipolcea(String divipolcea) {
        this.divipolcea = divipolcea;
    }

    public String getNrocercea() {
        return nrocercea;
    }

    public void setNrocercea(String nrocercea) {
        this.nrocercea = nrocercea;
    }

    public String getEstadolc() {
        return estadolc;
    }

    public void setEstadolc(String estadolc) {
        this.estadolc = estadolc;
    }

    public String getComprobpago() {
        return comprobpago;
    }

    public void setComprobpago(String comprobpago) {
        this.comprobpago = comprobpago;
    }

    public String getCodespven() {
        return codespven;
    }

    public void setCodespven(String codespven) {
        this.codespven = codespven;
    }

    public String getTipoidentantigua() {
        return tipoidentantigua;
    }

    public void setTipoidentantigua(String tipoidentantigua) {
        this.tipoidentantigua = tipoidentantigua;
    }

    public String getNroidentiantigua() {
        return nroidentiantigua;
    }

    public void setNroidentiantigua(String nroidentiantigua) {
        this.nroidentiantigua = nroidentiantigua;
    }

    public String getCategorianueva() {
        return categorianueva;
    }

    public void setCategorianueva(String categorianueva) {
        this.categorianueva = categorianueva;
    }

    public String getCertificadocrc() {
        return certificadocrc;
    }

    public void setCertificadocrc(String certificadocrc) {
        this.certificadocrc = certificadocrc;
    }

    public String getFechavenci() {
        return fechavenci;
    }

    public void setFechavenci(String fechavenci) {
        this.fechavenci = fechavenci;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getConsecutivosirev() {
        return consecutivosirev;
    }

    public void setConsecutivosirev(String consecutivosirev) {
        this.consecutivosirev = consecutivosirev;
    }

    public String getMigrado() {
        return migrado;
    }

    public void setMigrado(String migrado) {
        this.migrado = migrado;
    }

    public String getHomologacion() {
        return homologacion;
    }

    public void setHomologacion(String homologacion) {
        this.homologacion = homologacion;
    }

    public String getHomol1990() {
        return homol1990;
    }

    public void setHomol1990(String homol1990) {
        this.homol1990 = homol1990;
    }
    
    
}
