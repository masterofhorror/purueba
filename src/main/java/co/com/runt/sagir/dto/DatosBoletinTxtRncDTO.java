/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dto;

/**
 *
 * @author dsalamanca
 */
public class DatosBoletinTxtRncDTO {
    
    private String tipoDoc;
    private String nroLicencias;
    private Long codError;
    private String descripcion;
    private String placa;
    private Long secretaria;
    private Long codCarga;
    private String fecha;

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Long getSecretaria() {
        return secretaria;
    }

    public void setSecretaria(Long secretaria) {
        this.secretaria = secretaria;
    }

    public Long getCodCarga() {
        return codCarga;
    }

    public void setCodCarga(Long codCarga) {
        this.codCarga = codCarga;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getNroLicencias() {
        return nroLicencias;
    }

    public void setNroLicencias(String nroLicencias) {
        this.nroLicencias = nroLicencias;
    }

    public Long getCodError() {
        return codError;
    }

    public void setCodError(Long codError) {
        this.codError = codError;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
 
}
