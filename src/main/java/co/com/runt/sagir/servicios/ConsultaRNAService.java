/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.servicios;

import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.logica.ConsultaRNALogica;
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
@Path("/consultarna")
@Stateless
public class ConsultaRNAService extends ManejadorServicio{
    
    @EJB
    private ConsultaRNALogica consultaRNALogica;
    
    @GET
    @Path("/consultarporplaca/{placa}")
    @Produces (MediaType.APPLICATION_JSON)
    public MensajeDTO consultarPorPlaca (@PathParam("placa") String placa){
        return consultaRNALogica.consultarRNASagir(placa, getUsuario(), getIpCliente());
    }
    
}
