/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.servicios;

import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.logica.CargarArchivoRepotenciacionLogica;
import co.com.runt.sagir.logica.ProcesarArchivoRepotenciacionLogica;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author dsalamanca
 */
@Stateless
@Path("carguerepotencion")
public class CargarRepotenciacionService extends ManejadorServicio{
    
    @EJB
    private CargarArchivoRepotenciacionLogica repotenciacionLogica;
    
    @EJB
    private ProcesarArchivoRepotenciacionLogica procesarArchivoLogica;
    
    @GET
    @Path("/consulta/{placa}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO consultaCargarRepotenciacion(@PathParam("placa") String placa){
        return repotenciacionLogica.cantidadSolicitudesPlaca(placa, getUsuario(), getIpCliente());
    }
    
    @GET
    @Path("/consultadetalle/{placa}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO consultaDetalleSolicitudes(@PathParam("placa") String placa){
        return repotenciacionLogica.consultaRepotenciacion(placa, getUsuario(), getIpCliente());
    }
    
    @GET
    @Path("/procesararchivo/{nroTicket}/{idSecretaria}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO procesarDatos (@PathParam("nroTicket") String nroTicker, @PathParam("idSecretaria") Long idSecretaria){
        return procesarArchivoLogica.procesar(nroTicker, idSecretaria);
    }
  
    @GET
    @Produces("application/pdf")
    @Path("/generarpdf/{nroTicket}.pdf")
    public byte[] getReporte(@Context HttpServletResponse servletResponse, @PathParam("nroTicket")final String nroTicket) {
        servletResponse.addHeader("Content-Disposition", "attachment; filename="+nroTicket+".pdf");
        return procesarArchivoLogica.descargarPDF(nroTicket);
    }
}
