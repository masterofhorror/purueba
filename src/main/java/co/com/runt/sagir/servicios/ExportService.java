/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.servicios;

import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.logica.ExportLogica;
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
@Stateless
@Path("export")
public class ExportService extends ManejadorServicio{
    
    @EJB
    private ExportLogica exportLogica;
    
    @GET
    @Path("/pendientesmigrar")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO consultaPendientesMigrar(){
        return exportLogica.consultaPendientesMigrar();
    }
    
    @GET
    @Path("/migrados")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO consultaMigrados(){
        return exportLogica.consultaMigrados();
    }
    
    @GET
    @Path("/resultado")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO consultaResultados(){
        return exportLogica.consultaResultado();
    }
    
    @GET
    @Path("/procesar/{codCarga}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO procesar(@PathParam("codCarga") String codCarga){
        return exportLogica.procesar(codCarga);
    }
}
