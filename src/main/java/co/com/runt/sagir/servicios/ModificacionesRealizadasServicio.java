/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.servicios;

import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.logica.ModificacionesRealizadasLogica;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Hmoreno
 */
@Path("/modificacionesRealizadas")
@Stateless
public class ModificacionesRealizadasServicio extends ManejadorServicio {

    @EJB
    private ModificacionesRealizadasLogica modificacionesRealizadasLogica;

    @GET
    @Path("/{placa}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO consultarPorPlaca(@PathParam("placa") String placa) {
        return modificacionesRealizadasLogica.consultarPorPlaca(placa, getUsuario(), getIpCliente());
    }

}
