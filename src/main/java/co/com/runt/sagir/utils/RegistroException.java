/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.com.runt.sagir.utils;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author daperador
 */
public class RegistroException extends WebApplicationException{
    
    private static final Integer CODIGO_ERROR=511;
    private static final long serialVersionUID = 1L;
    
    private String mensaje;
    
    /**
     *
     * @param mensaje
     */
    public RegistroException(final String mensaje){
        super(Response.status(CODIGO_ERROR).entity(mensaje).type(MediaType.TEXT_PLAIN).build());
        this.mensaje=mensaje;
    }
    
    /**
     *
     * @param cause
     */
    public RegistroException(final Throwable cause){
        super(Response.status(CODIGO_ERROR).entity(mensajeRaiz(cause)).type(MediaType.TEXT_PLAIN).build());
        
    }
    
    private static String mensajeRaiz(final Throwable cause){
        if(cause.getCause()!=null){
            return mensajeRaiz(cause.getCause());
        }else{
            if(cause instanceof RegistroException){
                return ((RegistroException) cause).getMensaje();
            }else{
                return cause.getMessage();
            }
        }
    }

    /**
     *
     * @return
     */
    public String getMensaje() {
        return mensaje;
    }
}
