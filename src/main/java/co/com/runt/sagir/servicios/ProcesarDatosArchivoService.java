/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.servicios;

import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.logica.ProcesarArchivoRepotenciacionLogica;
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
@Path("/procesararchivo/{nroTicket}/{idSecretaria}")
@Stateless
public class ProcesarDatosArchivoService {
    
    @EJB
    private ProcesarArchivoRepotenciacionLogica procesarArchivoLogica;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO procesarDatos (@PathParam("nroTicket") String nroTicker, @PathParam("idSecretaria") Long idSecretaria){
        return procesarArchivoLogica.procesar(nroTicker, idSecretaria);
    }
    
}
