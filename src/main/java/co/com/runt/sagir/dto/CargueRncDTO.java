/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dto;

import co.com.runt.sagir.entities.OrganismosTransitoMigrunt;

/**
 *
 * @author dsalamanca
 */
public class CargueRncDTO {
    
    private OrganismosTransitoMigrunt organismoTransito;
    private String tipoRegistro;
    private String tipoCargue;
    private String nroTicket;
    private String archivoEmpaquetado;
    private String nombreArchivoEmpaquetado;

    public String getNroTicket() {
        return nroTicket;
    }

    public void setNroTicket(String nroTicket) {
        this.nroTicket = nroTicket;
    }

    public OrganismosTransitoMigrunt getOrganismoTransito() {
        return organismoTransito;
    }

    public void setOrganismoTransito(OrganismosTransitoMigrunt organismoTransito) {
        this.organismoTransito = organismoTransito;
    }

    public String getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public String getTipoCargue() {
        return tipoCargue;
    }

    public void setTipoCargue(String tipoCargue) {
        this.tipoCargue = tipoCargue;
    }

    public String getArchivoEmpaquetado() {
        return archivoEmpaquetado;
    }

    public void setArchivoEmpaquetado(String archivoEmpaquetado) {
        this.archivoEmpaquetado = archivoEmpaquetado;
    }

    public String getNombreArchivoEmpaquetado() {
        return nombreArchivoEmpaquetado;
    }

    public void setNombreArchivoEmpaquetado(String nombreArchivoEmpaquetado) {
        this.nombreArchivoEmpaquetado = nombreArchivoEmpaquetado;
    }
    
    
    
}
