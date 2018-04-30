/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.servicios;

import co.com.runt.sagir.dao.ConstanteDAO;
import co.com.runt.sagir.dto.CargueTemporalDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.logica.CargueArchivoTempoLogica;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author APENA
 */
@Path("cargueArchivoTemp")
@Stateless
public class CargueArchivoTemporalService extends ManejadorServicio{
    
    @EJB
    private CargueArchivoTempoLogica cargueArchivoTempoLogica;

    @EJB
    private ConstanteDAO constanteDAO;
    
    @POST
    @Path("/upload")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO cargarArchivo(final CargueTemporalDTO cargueTemporalDTO) throws Exception {
        return cargueArchivoTempoLogica.guardarArchivosAdjuntosTempo2(cargueTemporalDTO);
    }
    
    @GET
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO borrarTemporal(){
        return cargueArchivoTempoLogica.borrarCargueTemporal();
    }
}
