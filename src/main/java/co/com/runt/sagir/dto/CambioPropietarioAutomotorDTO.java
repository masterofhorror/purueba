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
public class CambioPropietarioAutomotorDTO {

    private String nroVehiculo;
    private String placa;
    private String estadoVehic;

    public String getNroVehiculo() {
        return nroVehiculo;
    }

    public void setNroVehiculo(String nroVehiculo) {
        this.nroVehiculo = nroVehiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getEstadoVehic() {
        return estadoVehic;
    }

    public void setEstadoVehic(String estadoVehic) {
        this.estadoVehic = estadoVehic;
    }

}
