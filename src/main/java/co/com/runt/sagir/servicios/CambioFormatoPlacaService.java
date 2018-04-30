/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.servicios;

import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.logica.CambioFormatoPlacaLogica;
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
@Path("/cambioformatoplaca")
@Stateless
public class CambioFormatoPlacaService extends ManejadorServicio{

    @EJB
    private CambioFormatoPlacaLogica formatoPlacaLogica;

    @GET
    @Path("/antiguonuevo/{placa}/{nroticket}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO cambioFormatoAntiguoNuevo(@PathParam ("placa") String placa,@PathParam ("nroticket") String nroTicket) {
        return formatoPlacaLogica.cambioAntiguo(placa, nroTicket, getUsuario());
    }
    
    @GET
    @Path("/nuevoantiguo/{placa}/{nroticket}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO cambioFormatoNuevoAntiguo(@PathParam ("placa") String placa,@PathParam ("nroticket") String nroTicket) {
        return formatoPlacaLogica.cambioNuevoFormato(placa, nroTicket, getUsuario());
    }
    
    @GET
    @Path("consultacambioformato/{placa}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO consultaCambioFormato (@PathParam ("placa") String placa){
        return formatoPlacaLogica.consultaCambioFormato(placa);
    }    
    
    @GET
    @Path("consultacambioformatodetalle/{placa}/{boletin}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO consultaCambioFormatoDetalle (@PathParam ("placa") String placa, @PathParam ("boletin") String boletin){
        return formatoPlacaLogica.consultaCambioFormatoDetalle(placa, boletin);
    }

}
