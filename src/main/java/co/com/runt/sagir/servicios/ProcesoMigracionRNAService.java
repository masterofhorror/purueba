/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.servicios;

import co.com.runt.sagir.dto.CargueRnaDTO;
import co.com.runt.sagir.dto.MensajeCargaDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.logica.ProcesoMigracionRNALogica;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author APENA
 */
@Stateless
@Path("procesomigracionrna/")
public class ProcesoMigracionRNAService extends ManejadorServicio {
    
    @EJB
    private ProcesoMigracionRNALogica procesoMigracionRNALogica;
    
    @POST
    @Path("/uploadRNA")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeCargaDTO cargarArchivo(final CargueRnaDTO cargueRnaDTO){
        return procesoMigracionRNALogica.guardar(cargueRnaDTO, getUsuario(), getIpCliente());
    }
}
