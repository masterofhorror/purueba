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
public class ConfiguracionProcesamientoDTO {
    
    private int hora;
    private int horasMax;
    private int hilos;

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getHorasMax() {
        return horasMax;
    }

    public void setHorasMax(int horasMax) {
        this.horasMax = horasMax;
    }

    public int getHilos() {
        return hilos;
    }

    public void setHilos(int hilos) {
        this.hilos = hilos;
    }
    
    
}
