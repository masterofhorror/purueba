/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.servicios;

import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.logica.ConsultaRangoPlacaLogica;
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
@Path("/consultarangoplaca")
@Stateless
public class ConsultaRangoPlacasService extends ManejadorServicio{
    
    @EJB
    private ConsultaRangoPlacaLogica rangoPlacaLogica;
    
    @GET
    @Path("/rangoplaca/{placa_ini}/{placa_fin}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO listadoPlaca (@PathParam ("placa_ini") String placaIni, @PathParam ("placa_fin") String placaFin){
        return rangoPlacaLogica.consultaRangoPlaca(placaIni, placaFin, getUsuario(), getIpCliente());
    }
    
    @GET
    @Path("/rangoplacatotal")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO totalPlacasRango (){
        return rangoPlacaLogica.consultaRangoPlacaTotal(getUsuario(), getIpCliente());
    }
    
    @GET
    @Path("/rangoplacaRNA")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO listadoRangoRNA (){
        return rangoPlacaLogica.consultaRangoPlacaRNA(getUsuario(), getIpCliente());
    }
}
