/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.servicios;

import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.logica.CarguePuntualLogica;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author APENA
 */
@Path("/carguepuntual")
@Stateless
public class CarguePuntualService extends ManejadorServicio{
    
    @EJB
    private CarguePuntualLogica carguePuntualLogica;
    
    @GET
    @Path("/cargue/{placa}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO carguePuntual(@PathParam ("placa") String placa) {
        return carguePuntualLogica.CarguePuntual(placa, getUsuario(), getIpCliente());
    }
    
    @GET
    @Path("/marcar/{placa}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO carguePuntualMarcar(@PathParam ("placa") String placa){
        return carguePuntualLogica.MarcarCarguePuntual(placa, getUsuario(), getIpCliente());
    }
    
    @GET
    @Path("/eliminaexport")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO eliminarExport(){
        return carguePuntualLogica.EliminarExportRNA();
    }
}
