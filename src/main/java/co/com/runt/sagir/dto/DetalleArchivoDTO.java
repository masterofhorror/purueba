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
public class DetalleArchivoDTO {

    private String oT;
    private String placa;
    private String tipoDoc;
    private String nroDoc;
    private String proindiv;
    private String fechaPropi;
    private String appell1;
    private String appell2;
    private String nombre1;
    private String nombre2;
    private String ciudad;
    private String direccion;
    private String telefono;
    private String salto;

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

    public String getProindiv() {
        return proindiv;
    }

    public void setProindiv(String proindiv) {
        this.proindiv = proindiv;
    }

    public String getFechaPropi() {
        return fechaPropi;
    }

    public void setFechaPropi(String fechaPropi) {
        this.fechaPropi = fechaPropi;
    }

    public String getAppell1() {
        return appell1;
    }

    public void setAppell1(String appell1) {
        this.appell1 = appell1;
    }

    public String getAppell2() {
        return appell2;
    }

    public void setAppell2(String appell2) {
        this.appell2 = appell2;
    }

    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getSalto() {
        return salto;
    }

    public void setSalto(String salto) {
        this.salto = salto;
    }
    
    
}
