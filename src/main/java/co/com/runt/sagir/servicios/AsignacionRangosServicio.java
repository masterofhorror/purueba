/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.servicios;

import co.com.runt.sagir.dto.AsignacionRangosDTO;
import co.com.runt.sagir.dto.BusquedaAsignacionRangosDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.dto.RangosEspeciesSerieRnaDTO;
import co.com.runt.sagir.logica.AsignacionRangosLogica;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Hmoreno
 */
@Path("/asignacionRangos")
@Stateless
public class AsignacionRangosServicio extends ManejadorServicio {

    @EJB
    private AsignacionRangosLogica asignacionRangosLogica;

    @POST
    @Path("totalItems")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Long getTotalItems(final BusquedaAsignacionRangosDTO busquedaAsignacionRangos) {

        return asignacionRangosLogica.getTotalItems(busquedaAsignacionRangos);
    }

    @POST
    @Path("items")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<RangosEspeciesSerieRnaDTO> getItems(final BusquedaAsignacionRangosDTO busquedaAsignacionRangos) {
        return asignacionRangosLogica.getItems(busquedaAsignacionRangos);
    }

    @POST
    @Path("guardar")
    public MensajeDTO guardar(final AsignacionRangosDTO entrada) {
        return asignacionRangosLogica.guardar(entrada, getUsuario());
    }
    
}
