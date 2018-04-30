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
public class PathDTO {
    
    private String pathFile;
    private String pathGeneral;
    private String mensaje;
    private CargueArchivoMensajes estado;

    public String getPathFile() {
        return pathFile;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }

    public String getPathGeneral() {
        return pathGeneral;
    }

    public void setPathGeneral(String pathGeneral) {
        this.pathGeneral = pathGeneral;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public CargueArchivoMensajes getEstado() {
        return estado;
    }

    public void setEstado(CargueArchivoMensajes estado) {
        this.estado = estado;
    }
  
}
