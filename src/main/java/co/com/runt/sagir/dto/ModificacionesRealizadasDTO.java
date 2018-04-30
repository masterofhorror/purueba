/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dto;

import java.util.List;

/**
 *
 * @author Hmoreno
 */
public class ModificacionesRealizadasDTO {

    private List<ModificacionesRealizadasActualizacionDTO> actualizaciones;
    private List<ModificacionesRealizadasCasosEspecialesDTO> casosEspeciales;

    public List<ModificacionesRealizadasActualizacionDTO> getActualizaciones() {
        return actualizaciones;
    }

    public void setActualizaciones(List<ModificacionesRealizadasActualizacionDTO> actualizaciones) {
        this.actualizaciones = actualizaciones;
    }

    public List<ModificacionesRealizadasCasosEspecialesDTO> getCasosEspeciales() {
        return casosEspeciales;
    }

    public void setCasosEspeciales(List<ModificacionesRealizadasCasosEspecialesDTO> casosEspeciales) {
        this.casosEspeciales = casosEspeciales;
    }

}
