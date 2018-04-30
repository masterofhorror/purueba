/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.servicios;

import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.logica.MarcarVehiculoMalCargadoLogica;
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
@Path("marcarvehiculo")
@Stateless
public class MarcarVehiculoMalCargadoService extends ManejadorServicio{
    
    @EJB
    private MarcarVehiculoMalCargadoLogica malCargadoLogica;
    
    @GET
    @Path("/consultaprod/{placa}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO consultaVehiculoMalCargadoProd(@PathParam("placa") String placa){
        return malCargadoLogica.consultarVehiculoProd(placa);
    }
    
    @GET
    @Path("/consultamigra/{placa}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO consultaVehiculoMalCargadoMigra(@PathParam("placa") String placa){
        return malCargadoLogica.consultarVehiculoMigra(placa);
    }
    
    
    @GET
    @Path("/marcavehimigra/{placa}/{nroticket}/{observacion}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO eliminaVehiculoMigra(@PathParam("placa") String placa, @PathParam("nroticket") String nroticket, @PathParam("observacion") String observacion){
        return malCargadoLogica.eliminaVehiculoMigra(placa, nroticket, observacion, getUsuario());
    }
    
    @GET
    @Path("/marcavehiprod/{placa}/{nroticket}/{observacion}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO eliminaVehiculoProd(@PathParam("placa") String placa, @PathParam("nroticket") String nroticket,@PathParam("observacion") String observacion){
        return malCargadoLogica.marcarVehiculoProd(placa, nroticket, observacion, getUsuario());
    }
    
}
