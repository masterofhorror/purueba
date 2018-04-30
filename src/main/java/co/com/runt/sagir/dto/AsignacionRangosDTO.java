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
public class AsignacionRangosDTO {

    private OrganismosTransitoMigruntDTO organismoTransito;
    private String especieVenal;

    private String desde;
    private String hasta;

    private String fechaAsignacion;
    private String resolucion;
    private String observaciones;

    public OrganismosTransitoMigruntDTO getOrganismoTransito() {
        return organismoTransito;
    }

    public void setOrganismoTransito(OrganismosTransitoMigruntDTO organismoTransito) {
        this.organismoTransito = organismoTransito;
    }

    public String getEspecieVenal() {
        return especieVenal;
    }

    public void setEspecieVenal(String especieVenal) {
        this.especieVenal = especieVenal;
    }

    public String getDesde() {
        return desde;
    }

    public void setDesde(String desde) {
        this.desde = desde;
    }

    public String getHasta() {
        return hasta;
    }

    public void setHasta(String hasta) {
        this.hasta = hasta;
    }

    public String getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(String fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public String getResolucion() {
        return resolucion;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

}
