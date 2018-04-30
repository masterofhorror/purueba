/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dto;

import co.com.runt.sagir.common.CargueArchivoMensajes;

/**
 *
 * @author dsalamanca
 */
public class CargueArchivoRespuestaDTO {

    private String mensaje;
    private Long idCargue;
    private CargueArchivoMensajes estado;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Long getIdCargue() {
        return idCargue;
    }

    public void setIdCargue(Long idCargue) {
        this.idCargue = idCargue;
    }

    public CargueArchivoMensajes getEstado() {
        return estado;
    }

    public void setEstado(CargueArchivoMensajes estado) {
        this.estado = estado;
    }

}
