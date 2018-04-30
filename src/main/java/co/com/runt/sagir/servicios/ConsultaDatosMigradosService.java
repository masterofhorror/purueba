/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.servicios;

import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.logica.ConsultaDatosMigradosLogica;
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
@Path("/consultadatosmigrados")
@Stateless
public class ConsultaDatosMigradosService extends ManejadorServicio{
    
    @EJB
    private ConsultaDatosMigradosLogica datosMigradosLogica;
    
    @GET
    @Path("/{placa}/{idCarga}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO consultaDatosMigrados (@PathParam ("placa") String placa, @PathParam ("idCarga") Long idCarga){
        return datosMigradosLogica.consultaDatosMigrados(placa, idCarga, getUsuario(), getIpCliente());
    }
}
