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
public class CargueRnaDTO {

    private OrganismosTransitoMigruntDTO organismoTransito;
    private String tipoRegistro;//rna = 1
    private String tipoCargue;// cd - resolucion y otros
    private String archivoEmpaquetado;
    private String nombreArchivoEmpaquetado;
    private String tutela;

    public OrganismosTransitoMigruntDTO getOrganismoTransito() {
        return organismoTransito;
    }

    public void setOrganismoTransito(OrganismosTransitoMigruntDTO organismoTransito) {
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

    public String getTutela() {
        return tutela;
    }

    public void setTutela(String tutela) {
        this.tutela = tutela;
    }

}
