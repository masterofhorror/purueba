/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.servicios;

import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.logica.ConsultaRNCMigraLogica;
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
@Path ("/consultamigrarnc")
@Stateless
public class ConsultaRNCMigraService extends ManejadorServicio{
    
    @EJB
    private ConsultaRNCMigraLogica rncMigraLogica;
    
    @GET
    @Path("/rncmigra/{tipodoc}/{nrodocumento}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO consultaRNCMigra (@PathParam("tipodoc") Long tipoDocumento, @PathParam("nrodocumento") String nroDocumento){
        return rncMigraLogica.consultaLicenciasMigrado(tipoDocumento, nroDocumento, getUsuario(), getIpCliente());
    }
    
    @GET
    @Path("/rncintermedia/{tipodoc}/{nrodocumento}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO consultaRNCIntermedia (@PathParam("tipodoc") Long tipoDocumento, @PathParam("nrodocumento") String nroDocumento){
        return rncMigraLogica.consultaRNCIntermedia(tipoDocumento, nroDocumento, getUsuario(), getIpCliente());
    }
    
    @GET
    @Path("/rncrechazos/{tipodoc}/{nrodocumento}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO consultaRNCRechazos (@PathParam("tipodoc") Long tipoDocumento, @PathParam("nrodocumento") String nroDocumento){
        return rncMigraLogica.consultaRechazosRNC(tipoDocumento, nroDocumento, getUsuario(), getIpCliente());
    }
    
    @GET
    @Path("/rncfinal/{tipodoc}/{nrodocumento}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO consultaRNCFinal (@PathParam("tipodoc") Long tipoDocumento, @PathParam("nrodocumento") String nroDocumento){
        return rncMigraLogica.consultaFinalRNC(tipoDocumento, nroDocumento, getUsuario(), getIpCliente());
    }
}
