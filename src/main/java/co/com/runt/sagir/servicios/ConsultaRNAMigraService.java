/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.servicios;

import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.logica.ConsultaRNAMigraLogica;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Dsalamanca
 */
@Path("/consultamigrarna")
@Stateless
public class ConsultaRNAMigraService extends ManejadorServicio{
    
    @EJB
    private ConsultaRNAMigraLogica aMigraLogica;
    
    @GET
    @Path("/consultaporplaca/{placa}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO consultaPorPlaca (@PathParam("placa") String placa){
        return aMigraLogica.consultaPlacaMigra(placa, getUsuario(), getIpCliente());        
    }
    
    @GET
    @Path("/consultaintermedia/{placa}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO consultaIntermedia (@PathParam("placa") String placa){
        return aMigraLogica.consultaIntermediaRNA(placa, getUsuario(), getIpCliente());
    }
    
    @GET
    @Path("/consultaboletin/{placa}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO consultaBoletinRNAMigra (@PathParam("placa") String placa){
        return aMigraLogica.consultaBoletinRNAMigra(placa, getUsuario(), getIpCliente());
    }
    
    @GET
    @Path("/consultarechazo/{placa}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO consultaRechazoRNAMigra (@PathParam("placa") String placa){
        return aMigraLogica.consultaRechazoRNAMigra(placa, getUsuario(), getIpCliente());
    }
    
    @GET
    @Path("/consultafinalmigra/{placa}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO consultaRNAFinalMigra (@PathParam("placa") String placa){
        return aMigraLogica.consultarRNAFinalMigra(placa, getUsuario(), getIpCliente());
    }
}
