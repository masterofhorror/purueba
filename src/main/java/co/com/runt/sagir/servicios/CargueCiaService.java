/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.servicios;

import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.logica.CargueCiaLogica;
import co.com.runt.sagir.utils.ListarBoletinException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
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
 * @author APENA
 */
@Stateless
@Path("cargueCia")
public class CargueCiaService extends ManejadorServicio {

    @EJB
    private CargueCiaLogica cargueCiaLogica;

    @GET
    @Path("/listarcia")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO listarCia() {
        return cargueCiaLogica.listarCia();
    }

    @GET
    @Produces("application/pdf")
    @Path("/generarpdf/{codCarga}.pdf")
    public byte[] getReporte(@Context HttpServletResponse servletResponse, @PathParam("codCarga") final Long codCarga) {
        servletResponse.addHeader("Content-Disposition", "attachment; filename=" + codCarga + ".pdf");
        return cargueCiaLogica.descargarPDF(codCarga);
    }

    @GET
    @Path("/reportePorCargue/{codCarga}")
    @Produces("application/zip")
    public byte[] reportePorCargue(@PathParam("codCarga") final Long cargue) {

        try {
            byte[] pdf = cargueCiaLogica.generarBoletinCiaPdf(cargue);

            byte[] txt = cargueCiaLogica.generarBoletinCiaTxt(cargue);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try (ZipOutputStream zos = new ZipOutputStream(baos)) {
                ZipEntry ze = new ZipEntry("BOLETIN_CARGUE_CIA_" + cargue + ".pdf");
                zos.putNextEntry(ze);
                zos.write(pdf);
                zos.closeEntry();
                
                if (txt != null) {
                    ZipEntry ze1 = new ZipEntry("ERRORES_CARGUE_"+ cargue +".TXT");
                    zos.putNextEntry(ze1);
                    zos.write(txt);
                    zos.closeEntry();
                }
            }

            return baos.toByteArray();
        } catch (ListarBoletinException | IOException ex) {
            Logger.getLogger(CargueCiaService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new byte[0];
    }
    
    @GET
    @Path("/consultacargacia/{codCarga}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO consultaCargaCia(@PathParam ("codCarga") Long codCarga) {
        return cargueCiaLogica.consultaExisteCargaCia(codCarga);
    }
    
}
