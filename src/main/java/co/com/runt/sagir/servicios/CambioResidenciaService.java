/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.servicios;

import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.logica.CambioResidenciaLogica;
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
@Path("/cambioresidencia/{placa}/{nroTicket}")
@Stateless
public class CambioResidenciaService extends ManejadorServicio{
   
    @EJB
    private CambioResidenciaLogica residenciaLogica;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO cambioResidencia (@PathParam("placa") String placa, @PathParam("nroTicket") String nroTicket){
        return residenciaLogica.cambioResidencia(placa, nroTicket, getUsuario(), getIpCliente());
    }
    
}
