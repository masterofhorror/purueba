/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.servicios;

import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.logica.CambioServicioLogica;
import co.com.runt.sagir.logica.CommonLogica;
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
@Path("cambioservicio/")
public class CambioServicioService extends ManejadorServicio{
    
    @EJB
    private CambioServicioLogica cambioServicioLogica;
    
    @EJB
    private CommonLogica commonLogica;
    
    @GET
    @Path("proceso/{placa}/{idTipoServicio}/{nroTicket}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO cambioServicio (@PathParam("placa") String placa, @PathParam("idTipoServicio") Integer idTipoServicio, @PathParam("nroTicket") String nroTicket){
        return cambioServicioLogica.cambioServicio(placa, idTipoServicio, nroTicket, getUsuario());
    }
    
    @GET
    @Path("consultatiposervicio")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO tipoServicio (){
        return commonLogica.tipoServicio();
    }
    
    @GET
    @Path("consulta/{placa}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO consultaVehiculo(@PathParam("placa") String placa){
        return cambioServicioLogica.consultaCambioServicio(placa);
    }
}
