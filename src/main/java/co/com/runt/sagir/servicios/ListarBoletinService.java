/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.servicios;

import co.com.runt.sagir.dto.ListarBoletinPorFechasDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.logica.ListarBoletinLogica;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
@Path("listarboletin")
public class ListarBoletinService extends ManejadorServicio {

    @EJB
    private ListarBoletinLogica boletinLogica;

    @GET
    @Path("/carga/{codCarga}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO listarByCodCarga(@PathParam("codCarga") Integer codCarga) {
        return boletinLogica.listarByCodCarga(codCarga);
    }

    @GET
    @Path("/tiporegistro/{tipoRegistro}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO listarByTipoRegisgtro(@PathParam("tipoRegistro") Integer tipoRegistro) {
        return boletinLogica.listarByTipoRegisgtro(tipoRegistro);
    }

    @GET
    @Path("/idsecretaria/{idSecretaria}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO listaByIdSecretaria(@PathParam("idSecretaria") Long idSecretaria) {
        return boletinLogica.listaByIdSecretaria(idSecretaria);
    }

    @POST
    @Path("/rangofecha")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO listaByRangoFecha(ListarBoletinPorFechasDTO fechasDTO) {
        return boletinLogica.listaByRangoFecha(fechasDTO);
    }

    @GET
    @Produces("application/zip")
    @Path("/generarboletin/{idCarga}.zip")
    public byte[] getReporte(@Context HttpServletResponse servletResponse, @PathParam("idCarga") final Long idCarga) {
        servletResponse.addHeader("Content-Disposition", "attachment; filename=" + idCarga + ".zip");
        return boletinLogica.descargarBoletinZip(idCarga);
    }
}
