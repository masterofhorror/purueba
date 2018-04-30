/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.servicios;

import co.com.runt.sagir.dto.CargueArchivoProcesoMigracionDTO;
import co.com.runt.sagir.dto.CargueRncDTO;
import co.com.runt.sagir.logica.CargueArchivoProcesoMigracionRNCLogica;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author dsalamanca
 */
@Stateless
@Path("procesomigracionrnc/")
public class ProcesoMigracionRNCService extends ManejadorServicio {

    @EJB
    private CargueArchivoProcesoMigracionRNCLogica procesoMigracionLogica;

    @POST
    @Path("upload")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public CargueArchivoProcesoMigracionDTO guardar(final CargueRncDTO entrada) throws Exception {
        return procesoMigracionLogica.guardar(entrada, getUsuario(), getIpCliente());
    }
    
}
