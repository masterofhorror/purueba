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
public class CantidadSolicitudesPorPlacaDTO {
    
    private String placa;
    private Long idVehiculo;
    private String cantidadSolicitudes;

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Long getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Long idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getCantidadSolicitudes() {
        return cantidadSolicitudes;
    }

    public void setCantidadSolicitudes(String cantidadSolicitudes) {
        this.cantidadSolicitudes = cantidadSolicitudes;
    }
    
    
    
}
