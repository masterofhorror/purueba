/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.servicios;

import co.com.runt.sagir.dto.CargueRnaDTO;
import co.com.runt.sagir.dto.MensajeCargaDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.dto.MensajeProgramadoDTO;
import co.com.runt.sagir.logica.ProgramacionCargueLogica;
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
 * @author Hmoreno
 */
@Path("/programacionCargue")
@Stateless
public class ProgramacionCargueServicio extends ManejadorServicio{

    @EJB
    private ProgramacionCargueLogica programacionCargueLogica;
    
    //consulta los cargues pendientes
    @GET
    @Path("/consulta")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO consultar() {
        return programacionCargueLogica.consultar();
    }
    
    //ejecuta los procedimientos almacenados 
    @GET
    @Path("/procesamiento")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeProgramadoDTO procesar() {
        return programacionCargueLogica.procesar();
    }
    
    //carga los archivos para programarlos
    @POST
    @Path("/uploadRNA")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO cargarArchivo(final CargueRnaDTO cargueRnaDTO){
        return programacionCargueLogica.guardar(cargueRnaDTO, getUsuario(), getIpCliente());
    }

}
