/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.dto;

import co.com.runt.sagir.common.Mensajes;
import co.com.runt.sagir.entities.CodigoCarga;
import java.util.List;

/**
 *
 * @author APENA
 */
public class MensajeProgramadoDTO {
    
    private Mensajes codmensaje;
    private Object object;
    private String mensaje;
    private List<CodigoCarga> codCargaProgramado;

    public Mensajes getCodmensaje() {
        return codmensaje;
    }

    public void setCodmensaje(Mensajes codmensaje) {
        this.codmensaje = codmensaje;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<CodigoCarga> getCodCargaProgramado() {
        return codCargaProgramado;
    }

    public void setCodCargaProgramado(List<CodigoCarga> codCargaProgramado) {
        this.codCargaProgramado = codCargaProgramado;
    }
    
    
}
