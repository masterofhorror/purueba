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
public class ConsultaFormatoPlacaDTO {
    
    private String placaProduccion;
    private String placaMigrada;
    private String idSecretaria;
    private String descModalidad;
    private String estadoCargue;

    public String getPlacaProduccion() {
        return placaProduccion;
    }

    public void setPlacaProduccion(String placaProduccion) {
        this.placaProduccion = placaProduccion;
    }

    public String getPlacaMigrada() {
        return placaMigrada;
    }

    public void setPlacaMigrada(String placaMigrada) {
        this.placaMigrada = placaMigrada;
    }

    public String getIdSecretaria() {
        return idSecretaria;
    }

    public void setIdSecretaria(String idSecretaria) {
        this.idSecretaria = idSecretaria;
    }

    public String getDescModalidad() {
        return descModalidad;
    }

    public void setDescModalidad(String descModalidad) {
        this.descModalidad = descModalidad;
    }

    public String getEstadoCargue() {
        return estadoCargue;
    }

    public void setEstadoCargue(String estadoCargue) {
        this.estadoCargue = estadoCargue;
    }

}
