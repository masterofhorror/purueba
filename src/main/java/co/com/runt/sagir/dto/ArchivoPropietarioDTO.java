/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dto;


import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dsalamanca
 */
@XmlRootElement
public class ArchivoPropietarioDTO {
    
    private String oT;
    private String placa;
    private Integer linea;
    private String datos;

    public String getoT() {
        return oT;
    }

    public void setoT(String oT) {
        this.oT = oT;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Integer getLinea() {
        return linea;
    }

    public void setLinea(Integer linea) {
        this.linea = linea;
    }

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }
    
    
    
}
