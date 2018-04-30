/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.servicios;

import co.com.runt.sagir.dto.MensajeDTO;
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
@Path("common")
public class CommonService extends ManejadorServicio{
    
    @EJB
    private CommonLogica commonLogica;
    
    @GET
    @Path("/listarot")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO listarOt (){
        return commonLogica.listarOt();
    }
    
    @GET
    @Path("/consultaplacacommon/{placa}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO consultaLicTttoPlaca (@PathParam("placa") String placa){
        return commonLogica.consultaLicTtoPlaca(placa);
    }
    
}
