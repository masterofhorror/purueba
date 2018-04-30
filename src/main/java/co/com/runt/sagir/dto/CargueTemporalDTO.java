/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dto;

import java.math.BigDecimal;
import javax.ejb.Stateless;

/**
 *
 * @author APENA
 */
@Stateless
public class CargueTemporalDTO {

    private BigDecimal tempId;
    private String tempNombre;
    private String tempArchivo;

    public BigDecimal getTempId() {
        return tempId;
    }

    public void setTempId(BigDecimal tempId) {
        this.tempId = tempId;
    }

    public String getTempNombre() {
        return tempNombre;
    }

    public void setTempNombre(String tempNombre) {
        this.tempNombre = tempNombre;
    }

    public String getTempArchivo() {
        return tempArchivo;
    }

    public void setTempArchivo(String tempArchivo) {
        this.tempArchivo = tempArchivo;
    }

    
}
