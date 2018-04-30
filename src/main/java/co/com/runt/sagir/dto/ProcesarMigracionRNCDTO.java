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
public class ProcesarMigracionRNCDTO {
    
    private String archivo;
    private String nombreArchivo;
    private String idArchivo;
    private Long idSecretaria;
    private Integer tipoRegistro;
    private String tipoCargue;

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
    
    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getIdArchivo() {
        return idArchivo;
    }

    public void setIdArchivo(String idArchivo) {
        this.idArchivo = idArchivo;
    }

    public Long getIdSecretaria() {
        return idSecretaria;
    }

    public void setIdSecretaria(Long idSecretaria) {
        this.idSecretaria = idSecretaria;
    }

    public Integer getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(Integer tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public String getTipoCargue() {
        return tipoCargue;
    }

    public void setTipoCargue(String tipoCargue) {
        this.tipoCargue = tipoCargue;
    }

        
}
