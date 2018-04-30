/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.servicios;

import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.logica.ReprocesarRNALogica;
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
@Path("reprocesarrna")
@Stateless
public class ReprocesarRNAService extends ManejadorServicio{
    
    @EJB
    private ReprocesarRNALogica reprocesarRNALogica;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/consulta/{idCarga}")
    public MensajeDTO consultaReprocesoRNA(@PathParam("idCarga") Integer idCarga){
        return reprocesarRNALogica.consultaReprocesoRNA(idCarga, getUsuario(), getIpCliente());
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/procesacarga/{idCarga}")
    public MensajeDTO procesaCarga (@PathParam("idCarga") Integer idCarga){
        return reprocesarRNALogica.procesarTodo(idCarga, getUsuario(), getIpCliente());
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/procesaplaca/{idCarga}/{placa}")
    public MensajeDTO procesaPlaca (@PathParam("idCarga") Integer idCarga, @PathParam("placa") String placa){
        return reprocesarRNALogica.procesar(idCarga, placa, getUsuario(), getIpCliente());
    }
}
