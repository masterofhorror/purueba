/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.servicios;

import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.logica.ConsultaDianLogica;
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
@Path("/consultadadeclaimport")
@Stateless
public class ConsultaDeclaImportService extends ManejadorServicio {
    
    @EJB
    private ConsultaDianLogica consultaDianLogica;
    
    @GET
    @Path("/{numDeclaracion}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO consultaDeclaracionImportacion (@PathParam ("numDeclaracion") String numDeclaracion){
        return consultaDianLogica.consultaDeclaImpor(numDeclaracion, getUsuario(), getIpCliente());
    }
    
}


