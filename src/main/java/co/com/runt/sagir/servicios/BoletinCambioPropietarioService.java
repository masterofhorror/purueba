/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.servicios;

import co.com.runt.sagir.dto.ListarBoletinPorFechasDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.logica.BoletinCargaLogica;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author dsalamanca
 */
@Path("/boletin")
@Stateless
public class BoletinCambioPropietarioService extends ManejadorServicio{
    
    @EJB
    private BoletinCargaLogica cargaLogica;
    
    @GET
    @Path("/cambioprop/{idCarga}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO boletinCambioProp (@PathParam("idCarga") final Long idCarga){
        return cargaLogica.consultaBoletinCambioProp(idCarga);
        
    }
    
    @POST
    @Path("/archivoshistoricos")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO consultaHistorico (ListarBoletinPorFechasDTO fechaDTO){
        return cargaLogica.consultarHistoricoCargues(getUsuario(), fechaDTO);
    }
}
