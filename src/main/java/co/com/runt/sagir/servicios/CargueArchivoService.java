/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.servicios;

import co.com.runt.clienteserviciosfirma.ClienteServiciosFirma;
import co.com.runt.clienteserviciosfirma.dto.ValidacionFirmaDTO;
import co.com.runt.clienteserviciosfirma.dto.ValidacionFirmaInParamDTO;
import co.com.runt.sagir.common.CargueArchivoMensajes;
import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.dao.ConstanteDAO;
import co.com.runt.sagir.dto.CargueArchivoDTO;
import co.com.runt.sagir.dto.CargueArchivoRespuestaDTO;
import co.com.runt.sagir.entities.AutoridadTransitoHQ;
import co.com.runt.sagir.logica.CargueArchivoLogica;
import co.com.runt.sagir.utils.RegistroException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author dsalamanca
 */
@Path("cargueArchivosServices")
@Stateless
public class CargueArchivoService extends ManejadorServicio {

    public static final String VALIDAR_FIRMA = "http://weblogicInterno/ServiciosFirma/webresources/validacionFirma";

    private static final Logger LOGGER = Logger.getLogger(CargueArchivoService.class.getSimpleName());

    @Context
    private HttpServletRequest request;
    @EJB
    private CargueArchivoLogica cargueArchivoLogica;
    @EJB
    private ConstanteDAO constanteDAO;

    @POST
    @Path("/upload")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CargueArchivoRespuestaDTO cargarArchivo(final CargueArchivoDTO cargueArchivoDTO) throws Exception {
        String usuario = getUsuario().toString();
//        String autoridad = getIpCliente();
        String desc = null;
        String ip;
        if (cargueArchivoDTO.getArchivo() == null || "false".equals(cargueArchivoDTO.getArchivo())) {
            desc = "No se puede cargar un archivo sin firma digital";
        }

        Long idSecretaria = cargueArchivoDTO.getIdSecretaria();
        if (idSecretaria != null) {
            byte[] contenido = validarCertificado(usuario, cargueArchivoDTO.getArchivo(), idSecretaria);

            if (contenido == null) {
                desc = "Se presentó un problema al validar la firma digital, o la firma no es válida";
            }
            if (desc != null) {
                CargueArchivoRespuestaDTO error = new CargueArchivoRespuestaDTO();
                error.setEstado(CargueArchivoMensajes.RECHAZADO);
                error.setMensaje(desc);
                return error;
            }
            ip = getIpCliente();//(String)request.getHeader(Constantes.LOG_IP_HEADER);
            if (ip == null) {
                ip = (String) request.getRemoteAddr();
            }
            return cargueArchivoLogica.validaArchivo(cargueArchivoDTO, getUsuario(), ip);
        } else {
            LOGGER.log(Level.SEVERE, "El id de la secretaria es null");
        }
        return null;

    }

    /**
     *
     * @param usuario
     * @param firma
     * @param idSecretaria
     * @return
     * @throws Exception
     */
    public byte[] validarCertificado(String usuario, String firma, Long idSecretaria) throws Exception {
        //System.out.println("[validarCertificado][usuario]->"+usuario);
        //System.out.println("[validarCertificado][firma]->"+firma);

        ClienteServiciosFirma cliente = new ClienteServiciosFirma(VALIDAR_FIRMA);
        String parametroUsuario = constanteDAO.getByGrupoNombre(Constantes.GRUPO_CARGUE_ARCHIVO, Constantes.VALIDACION_USUARIO).getConstanteValor();
        ValidacionFirmaInParamDTO entrada = new ValidacionFirmaInParamDTO();
        //ValidacionFirmaOrganismoInDTO entrada = new ValidacionFirmaOrganismoInDTO();
        AutoridadTransitoHQ organismo = constanteDAO.consultaOT(idSecretaria);
        String nitOrganismo = organismo.getEmpresaPersona().getPersona().getPersonaNrodocume();
        entrada.setCedulaUsuario(nitOrganismo);
        entrada.setDatosFirmados(firma);
        entrada.setParametro(parametroUsuario);
//        entrada.setCedulaUsuario(usuario);
//        entrada.setDatosFirmados(firma);
//        entrada.setOrganismo(nitOrganismo);
//        ValidacionFirmaDTO respuesta = cliente.validarFirmaOrganismoTransito(entrada);
        ValidacionFirmaDTO respuesta = cliente.validarFirmaParam(entrada);
        if (respuesta == null) {
            throw new RegistroException("Error al validar la firma para el usuario: " + usuario);
        }
        return respuesta.getDatos();
    }

}
