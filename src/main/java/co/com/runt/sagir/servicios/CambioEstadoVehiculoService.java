/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.servicios;

import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.logica.CambioEstadoLogica;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author dsalamanca
 */
@Path("estadovehiculo/")
@Stateless
public class CambioEstadoVehiculoService extends ManejadorServicio{
    
    @EJB
    private CambioEstadoLogica servicioLogica;
    
    @GET
    @Path("cambioestadovehiculo/{placa}/{estado}/{nroticket}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO cambioEstadoVehiculo (@PathParam("placa") String placa, @PathParam("estado") Integer idEstado, @PathParam ("nroticket") String nroTicket){
        return servicioLogica.cambioEstadoVehiculo(placa, idEstado, nroTicket, getUsuario());
    }
    
    @GET
    @Path("cambioestadocancelado/{placa}/{estado}/{nroticket}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO cambioEstadoVehiculoCancelado (@PathParam("placa") String placa, @PathParam("estado") Integer idEstado, @PathParam ("nroticket") String nroTicket){
        return servicioLogica.cambioEstadoCancelado(placa, idEstado, nroTicket, getUsuario());
    }
    
    
    @GET
    @Path("consultaestadovehiculo/{placa}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO consultaCambioFormato (@PathParam ("placa") String placa){
        return servicioLogica.consultaVehiculo(placa);
    }
}
