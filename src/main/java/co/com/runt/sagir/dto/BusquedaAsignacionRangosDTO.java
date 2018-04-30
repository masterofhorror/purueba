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
public class BusquedaAsignacionRangosDTO {

    private String fecha;
    private OrganismosTransitoMigruntDTO organismoTransito;
    private String placa;

    private Integer limit;
    private Integer offset;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public OrganismosTransitoMigruntDTO getOrganismoTransito() {
        return organismoTransito;
    }

    public void setOrganismoTransito(OrganismosTransitoMigruntDTO organismoTransito) {
        this.organismoTransito = organismoTransito;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

}
