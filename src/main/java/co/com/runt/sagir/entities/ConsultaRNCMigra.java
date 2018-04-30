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
    @SqlResultSetMapping(name = "consultaRNCMigracion", entities = {
        @EntityResult(entityClass = ConsultaRNCMigra.class, fields = {
            @FieldResult(name = "tipDoc", column = "TIPDOC"),
            @FieldResult(name = "nroDoc", column = "NRODOC"),
            @FieldResult(name = "nroLic", column = "NROLIC"),
            @FieldResult(name = "fechExped", column = "FECHEXPED"),
            @FieldResult(name = "fechaVenci", column = "FECHAVENCI"),
            @FieldResult(name = "categoria", column = "CATEGORIA"),
            @FieldResult(name = "idAutori", column = "IDAUTORI"),
            @FieldResult(name = "autoridad", column = "AUTORIDAD"),
            @FieldResult(name = "carga", column = "CARGA"),
            @FieldResult(name = "fechaCarga", column = "FECHACARGA"),
            @FieldResult(name = "folio", column = "FOLIO"),
            @FieldResult(name = "boletin", column = "BOLETIN"),
            @FieldResult(name = "tipoTrami", column = "TIPOTRAMI"),
            @FieldResult(name = "fechaTrami", column = "FECHATRAMI"),
            @FieldResult(name = "codCea", column = "CODCEA"),
            @FieldResult(name = "divipolCea", column = "DIVIPOLCEA"),
            @FieldResult(name = "certCea", column = "CERTCEA"),
            @FieldResult(name = "estadoRang", column = "ESTADORANG"),
            @FieldResult(name = "compPago", column = "COMPPAGO"),
            @FieldResult(name = "codEspven", column = "CODESPVEN"),
            @FieldResult(name = "certiCrc", column = "CERTICRC"),
            @FieldResult(name = "tipoIdentiAntigua", column = "TIPOIDENTIANTIGUA"),
            @FieldResult(name = "identiAntigua", column = "IDENTIANTIGUA"),
            @FieldResult(name = "categoriNueva", column = "CATEGORINUEVA"),
            @FieldResult(name = "servicio", column = "SERVICIO"),
            @FieldResult(name = "consecutivoSirev", column = "CONSECUTIVOSIREV"),
            @FieldResult(name = "rechazo", column = "RECHAZO")
        })
    })
})
public class ConsultaRNCMigra implements Serializable{
    
    private static final long serialVersionUID = 1L;

    
    private String nroLic;
    private String tipDoc;
    private String nroDoc;    
    private String fechExped;
    private String fechaVenci;
    private String categoria;
    private String idAutori;
    private String autoridad;
    @Id
    private String carga;
    private String fechaCarga;
    private String folio;
    private String boletin;
    private String tipoTrami;
    private String fechaTrami;
    private String codCea;
    private String divipolCea;
    private String certCea;
    private String estadoRang;
    private String compPago;
    private String codEspven;
    private String certiCrc;
    private String tipoIdentiAntigua;
    private String identiAntigua;
    private String categoriNueva;
    private String servicio;
    private String consecutivoSirev;
    private String rechazo;

    public String getNroLic() {
        return nroLic;
    }

    public void setNroLic(String nroLic) {
        this.nroLic = nroLic;
    }

    public String getTipDoc() {
        return tipDoc;
    }

    public void setTipDoc(String tipDoc) {
        this.tipDoc = tipDoc;
    }

    public String getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(String nroDoc) {
        this.nroDoc = nroDoc;
    }

    public String getFechExped() {
        return fechExped;
    }

    public void setFechExped(String fechExped) {
        this.fechExped = fechExped;
    }

    public String getFechaVenci() {
        return fechaVenci;
    }

    public void setFechaVenci(String fechaVenci) {
        this.fechaVenci = fechaVenci;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getIdAutori() {
        return idAutori;
    }

    public void setIdAutori(String idAutori) {
        this.idAutori = idAutori;
    }

    public String getAutoridad() {
        return autoridad;
    }

    public void setAutoridad(String autoridad) {
        this.autoridad = autoridad;
    }

    public String getCarga() {
        return carga;
    }

    public void setCarga(String carga) {
        this.carga = carga;
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

    public String getTipoTrami() {
        return tipoTrami;
    }

    public void setTipoTrami(String tipoTrami) {
        this.tipoTrami = tipoTrami;
    }

    public String getFechaTrami() {
        return fechaTrami;
    }

    public void setFechaTrami(String fechaTrami) {
        this.fechaTrami = fechaTrami;
    }

    public String getCodCea() {
        return codCea;
    }

    public void setCodCea(String codCea) {
        this.codCea = codCea;
    }

    public String getDivipolCea() {
        return divipolCea;
    }

    public void setDivipolCea(String divipolCea) {
        this.divipolCea = divipolCea;
    }

    public String getCertCea() {
        return certCea;
    }

    public void setCertCea(String certCea) {
        this.certCea = certCea;
    }

    public String getEstadoRang() {
        return estadoRang;
    }

    public void setEstadoRang(String estadoRang) {
        this.estadoRang = estadoRang;
    }

    public String getCompPago() {
        return compPago;
    }

    public void setCompPago(String compPago) {
        this.compPago = compPago;
    }

    public String getCodEspven() {
        return codEspven;
    }

    public void setCodEspven(String codEspven) {
        this.codEspven = codEspven;
    }

    public String getCertiCrc() {
        return certiCrc;
    }

    public void setCertiCrc(String certiCrc) {
        this.certiCrc = certiCrc;
    }

    public String getTipoIdentiAntigua() {
        return tipoIdentiAntigua;
    }

    public void setTipoIdentiAntigua(String tipoIdentiAntigua) {
        this.tipoIdentiAntigua = tipoIdentiAntigua;
    }

    public String getIdentiAntigua() {
        return identiAntigua;
    }

    public void setIdentiAntigua(String identiAntigua) {
        this.identiAntigua = identiAntigua;
    }

    public String getCategoriNueva() {
        return categoriNueva;
    }

    public void setCategoriNueva(String categoriNueva) {
        this.categoriNueva = categoriNueva;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getConsecutivoSirev() {
        return consecutivoSirev;
    }

    public void setConsecutivoSirev(String consecutivoSirev) {
        this.consecutivoSirev = consecutivoSirev;
    }

    public String getRechazo() {
        return rechazo;
    }

    public void setRechazo(String rechazo) {
        this.rechazo = rechazo;
    }
    
    

}
