/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dto;

import co.com.runt.sagir.entities.ComConstantesPK;
import java.io.Serializable;

/**
 *
 * @author dsalamanca
 */
public class ComConstantesDTO {
    
    protected ComConstantesPK constantesPK;
    private String constanteValor;
    private Character constanteEstado;
    private Serializable constanteVector;

    public ComConstantesPK getConstantesPK() {
        return constantesPK;
    }

    public void setConstantesPK(ComConstantesPK constantesPK) {
        this.constantesPK = constantesPK;
    }

    public String getConstanteValor() {
        return constanteValor;
    }

    public void setConstanteValor(String constanteValor) {
        this.constanteValor = constanteValor;
    }

    public Character getConstanteEstado() {
        return constanteEstado;
    }

    public void setConstanteEstado(Character constanteEstado) {
        this.constanteEstado = constanteEstado;
    }

    public Serializable getConstanteVector() {
        return constanteVector;
    }

    public void setConstanteVector(Serializable constanteVector) {
        this.constanteVector = constanteVector;
    }
    
    
}
