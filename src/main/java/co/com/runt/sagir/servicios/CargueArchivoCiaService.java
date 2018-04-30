/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.servicios;

import co.com.runt.clienteserviciosfirma.ClienteServiciosFirma;
import co.com.runt.clienteserviciosfirma.dto.ValidacionFirmaDTO;
import co.com.runt.clienteserviciosfirma.dto.ValidacionFirmaInParamDTO;
import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.common.Mensajes;
import co.com.runt.sagir.dao.ConstanteDAO;
import co.com.runt.sagir.dto.CargueArchivoCiaDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.logica.CargueCiaLogica;
import co.com.runt.sagir.utils.RegistroException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author APENA
 */
@Path("cargueArchivosCiaServices")
@Stateless
public class CargueArchivoCiaService extends ManejadorServicio {

    //public static final String VALIDAR_FIRMA_NIT = "http://weblogicInterno/ServiciosFirma/webresources/validacionFirma/validarFirmaAutoridad";
    //public static final String VALIDAR_FIRMA_NIT = "http://weblogicInterno/ServiciosFirma/webresources/validacionFirma";
    public static final String VALIDAR_FIRMA = "http://weblogicInterno/ServiciosFirma/webresources/validacionFirma";

    @Context
    private HttpServletRequest request;

    @EJB
    private CargueCiaLogica cargueCiaLogica;

    @EJB
    private ConstanteDAO constanteDAO;

    @POST
    @Path("/uploadCia")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO cargarArchivo(final CargueArchivoCiaDTO cargueArchivoCiaDTO) throws Exception {
        String usuario = cargueArchivoCiaDTO.toString();

        String desc = null;
        String ip;

        if (cargueArchivoCiaDTO.getArchivo() == null || "false".equals(cargueArchivoCiaDTO.getArchivo())) {
            desc = "No se puede cargar un archivo sin firma digital";
        }
        String nitCia = cargueArchivoCiaDTO.getNitCia();
        byte[] contenido = validarCertificado(usuario, cargueArchivoCiaDTO.getArchivo(), nitCia);

        if (contenido == null) {
            desc = "Se presentó un problema al validar la firma digital, o la firma no es válida";
        }

        if (desc != null) {
            MensajeDTO error = new MensajeDTO();
            error.setCodmensaje(Mensajes.ERROR);
            error.setMensaje(desc);
            return error;
        }

        ip = getIpCliente();
        if (ip == null) {
            ip = (String) request.getRemoteAddr();
        }

        return cargueCiaLogica.validaArchivo(cargueArchivoCiaDTO, getUsuario(), ip);
    }

    public byte[] validarCertificado(final String usuario, final String firma, final String nitCia) throws Exception {

        if (usuario != null) {
            ClienteServiciosFirma cliente = new ClienteServiciosFirma(VALIDAR_FIRMA);
            String parametroUsuario = constanteDAO.getByGrupoNombre(Constantes.GRUPO_CARGUE_ARCHIVO, Constantes.VALIDACION_USUARIO).getConstanteValor();
            ValidacionFirmaInParamDTO entrada = new ValidacionFirmaInParamDTO();

            entrada.setCedulaUsuario(nitCia);
            entrada.setDatosFirmados(firma);
            entrada.setParametro(parametroUsuario);

            ValidacionFirmaDTO respuesta = cliente.validarFirmaParam(entrada);

            if (respuesta == null) {
                throw new RegistroException("Error al validar la firma para el NIT: " + usuario);
            }
            return respuesta.getDatos();
        } else {

        }

        return new byte[0];
    }
}
