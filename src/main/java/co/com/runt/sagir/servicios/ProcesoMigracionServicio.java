/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.servicios;

import co.com.runt.sagir.dto.CargueRnaDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.logica.ProcesoMigracionLogica;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 *
 * @author Hmoreno
 */
@Path("/procesoMigracion")
@Stateless
public class ProcesoMigracionServicio extends ManejadorServicio {

    @EJB
    private ProcesoMigracionLogica procesoMigracionLogica;

    @POST
    @Path("programacion")
    public MensajeDTO guardar(final CargueRnaDTO entrada) {
        return procesoMigracionLogica.guardar(entrada, getUsuario(), getIpCliente());
    }

}
