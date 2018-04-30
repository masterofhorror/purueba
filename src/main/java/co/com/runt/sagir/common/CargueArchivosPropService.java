/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.common;

import co.com.runt.clienteserviciosfirma.ClienteServiciosFirma;
import co.com.runt.clienteserviciosfirma.dto.ValidacionFirmaDTO;
import co.com.runt.clienteserviciosfirma.dto.ValidacionFirmaInParamDTO;
import co.com.runt.sagir.dao.ConstanteDAO;
import co.com.runt.sagir.dto.CargueArchivoDTO;
import co.com.runt.sagir.dto.CargueArchivoRespuestaDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.entities.AutoridadTransitoHQ;
import co.com.runt.sagir.logica.CargueArchivoPropLogica;
import co.com.runt.sagir.logica.ProcesarCarguesPropietarioLogica;
import co.com.runt.sagir.servicios.ManejadorServicio;
import co.com.runt.sagir.utils.RegistroException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.core.Context;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author ccepeda
 */
@Path("cargueArchivosProp")
@Stateless
public class CargueArchivosPropService extends ManejadorServicio {

    public static final String VALIDAR_FIRMA = "http://weblogicInterno/ServiciosFirma/webresources/validacionFirma";

    @Context
    private HttpServletRequest request;
    @EJB
    private CargueArchivoPropLogica cargueArchivoLogica;
    @EJB
    private ConstanteDAO constanteDAO;
    @EJB
    private ProcesarCarguesPropietarioLogica propietarioLogica;

    @POST
    @Path("/upload")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CargueArchivoRespuestaDTO cargarArchivo(final CargueArchivoDTO cargueArchivoDTO) throws Exception {
        String usuario = getUsuario().toString();

        String desc = null;
        String ip;
        String nombreArchivo = cargueArchivoDTO.getNombre();
        //Expresión regular, que valida que elnombre del archivo cumpla con las condiciones
        Pattern expRegular = Pattern.compile(CargueArchivoCommon.EXPRESION_REGULAR_NOMBRE_ARCHIVO_PROPIETARIOS);
        //Se pasa el nombre del archivo por la expresión regular
        Matcher input = expRegular.matcher(nombreArchivo);
        //Se valdia que el nombre del archivo cumpla la expresión regular
        if (input.find()) {
            //Se guarda el grupo 1, de la empresión regular
            String idSecretaria = input.group(1);
            if (idSecretaria != null) {   
                if (cargueArchivoDTO.getArchivo() == null || "false".equals(cargueArchivoDTO.getArchivo())) {
                    desc = "No se puede cargar un archivo sin firma digital";
                }
                byte[] contenido = validarCertificado(usuario, cargueArchivoDTO.getArchivo(), Long.parseLong(idSecretaria));

                if (contenido == null) {
                    desc = "Se presentó un problema al validar la firma digital, o la firma no es válida";
                }
                if (desc != null) {
                    CargueArchivoRespuestaDTO error = new CargueArchivoRespuestaDTO();
                    error.setEstado(CargueArchivoMensajes.RECHAZADO);
                    error.setMensaje(desc);
                    return error;
                }
                ip = getIpCliente();
                if (ip == null) {
                    ip = (String) request.getRemoteAddr();
                }

                return cargueArchivoLogica.validaArchivo(cargueArchivoDTO, getUsuario(), ip);
            }
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

        ClienteServiciosFirma cliente = new ClienteServiciosFirma(VALIDAR_FIRMA);
        String parametroUsuario = constanteDAO.getByGrupoNombre(Constantes.GRUPO_CARGUE_ARCHIVO, Constantes.VALIDACION_USUARIO).getConstanteValor();
        ValidacionFirmaInParamDTO entrada = new ValidacionFirmaInParamDTO();
        AutoridadTransitoHQ organismo = constanteDAO.consultaOT(idSecretaria);
        String nitOrganismo = organismo.getEmpresaPersona().getPersona().getPersonaNrodocume();
        entrada.setCedulaUsuario(nitOrganismo);
        entrada.setDatosFirmados(firma);
        entrada.setParametro(parametroUsuario);
        ValidacionFirmaDTO respuesta = cliente.validarFirmaParam(entrada);
        if (respuesta == null) {
            throw new RegistroException("Error al validar la firma para el usuario: " + usuario);
        }
        return respuesta.getDatos();
    }
    
    @GET
    @Path("procesar/{nroTicket}")
    @Produces(MediaType.APPLICATION_JSON)
    public MensajeDTO procesarCambioPropietario (@PathParam("nroTicket") String nroTicket){
        return propietarioLogica.procesarPropietario(nroTicket, getUsuario());
    }

}
