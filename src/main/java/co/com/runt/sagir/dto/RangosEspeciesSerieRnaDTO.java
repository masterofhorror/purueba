/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dto;

/**
 *
 * @author Hmoreno
 */
public class RangosEspeciesSerieRnaDTO {

    private String placa;
    private Long idSecretaria;
    private String migradoPlacaSerie;
    private String fechaCargue;
    private Integer especieVenal;

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Long getIdSecretaria() {
        return idSecretaria;
    }

    public void setIdSecretaria(Long idSecretaria) {
        this.idSecretaria = idSecretaria;
    }

    public String getMigradoPlacaSerie() {
        return migradoPlacaSerie;
    }

    public void setMigradoPlacaSerie(String migradoPlacaSerie) {
        this.migradoPlacaSerie = migradoPlacaSerie;
    }

    public String getFechaCargue() {
        return fechaCargue;
    }

    public void setFechaCargue(String fechaCargue) {
        this.fechaCargue = fechaCargue;
    }

    public Integer getEspecieVenal() {
        return especieVenal;
    }

    public void setEspecieVenal(Integer especieVenal) {
        this.especieVenal = especieVenal;
    }

}
