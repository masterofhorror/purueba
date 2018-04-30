/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.logica;

import co.com.runt.sagir.common.CargueArchivoMensajes;
import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.dao.CargueArchivoDAO;
import co.com.runt.sagir.dao.ConstanteDAO;
import co.com.runt.sagir.dto.CargueArchivoDTO;
import co.com.runt.sagir.dto.CargueArchivoRespuestaDTO;
import co.com.runt.sagir.dto.InfoUsuarioDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.entities.ComConstantes;
import co.com.runt.sagir.entities.SgCarguearchivos;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author dsalamanca
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class CargueArchivoPropLogica {

    @Resource
    protected EJBContext context;

    @EJB
    private CargueArchivoDAO cargueArchivoDAO;

    @EJB
    private ConstanteDAO constanteDAO;

    @EJB
    private ProcesarCarguesPropietarioLogica procesarPropietario;

    private static final Logger LOG = Logger.getLogger(CargueArchivoPropLogica.class.getSimpleName());

    /**
     * *
     * Metodo que se encarga de hacer las validaciones antes del cargue del
     * archivo plano
     *
     * @param data
     * @param usuario
     * @param ip
     * @return
     */
    public CargueArchivoRespuestaDTO validaArchivo(CargueArchivoDTO data, final InfoUsuarioDTO usuario, final String ip) {
        LOG.log(Level.INFO, "=============validando carga archivo========={0}", data.toString());
        CargueArchivoRespuestaDTO respuestaDTO = new CargueArchivoRespuestaDTO();
        String nombreArchivo = data.getNombre();
        String oTNombreArchivo = nombreArchivo.substring(0, 8);
        String mensaje;
        Long autoridad = usuario.getIdOrganizacion();
        String user = usuario.getLogin();
        Integer validaTicket = constanteDAO.countCantidadTickets(data.getNroTicket());
        if (validaTicket != 0) {
            mensaje = "El número de ticket ingresado ya se encuentra registrado.";
            respuestaDTO.setMensaje(mensaje);
            respuestaDTO.setEstado(CargueArchivoMensajes.RECHAZADO);
            return respuestaDTO;
        }
        if (!validaNroArchivosCargados(data)) {
            mensaje = "La autoridad de tránsito ha superado la cantidad de archivos permitidos por día";
            respuestaDTO.setMensaje(mensaje);
            respuestaDTO.setEstado(CargueArchivoMensajes.RECHAZADO);
        } else {
            Integer valida = validaIdOt(oTNombreArchivo);
            if (valida == -1) {
                mensaje = "El código de Organismo de Tránsito registrado en el archivo no registra en la base de datos.";
                respuestaDTO.setMensaje(mensaje);
                respuestaDTO.setEstado(CargueArchivoMensajes.RECHAZADO);
            } else {
                cargarArchivoPropietarios(data, user, autoridad.toString(), ip);
                respuestaDTO.setEstado(CargueArchivoMensajes.SIN_PROCESAR);
                MensajeDTO proceso = procesarPropietario.procesarPropietario(data.getNroTicket(), usuario);
                if (Constantes.ESTADO_OK.equals(proceso.getCodmensaje().toString())) {
                    respuestaDTO.setMensaje(proceso.getMensaje());
                    respuestaDTO.setEstado(CargueArchivoMensajes.PROCESADO);
                } else {
                    respuestaDTO.setMensaje(proceso.getMensaje());
                    respuestaDTO.setEstado(CargueArchivoMensajes.RECHAZADO);
                }
            }
        }
        return respuestaDTO;
    }

    /**
     * Metodo que se encarga de registrar el cargue de la tabla
     * CSWSAGIR.TBL_CARGUEARCHIVO
     *
     * @param data
     * @param user
     * @param autoridad
     * @param ip
     * @return
     */
    public CargueArchivoRespuestaDTO cargarArchivoPropietarios(CargueArchivoDTO data, final String user, final String autoridad, final String ip) {

        LOG.log(Level.INFO, "=============procesando carga archivo========={0}", data.toString());

        SgCarguearchivos cargue = new SgCarguearchivos();
        CargueArchivoRespuestaDTO respuestaDTO = new CargueArchivoRespuestaDTO();

        try {
            UserTransaction ut = context.getUserTransaction();
            ut.begin();

            //cargue.setCarguearchivosDatos(data.getArchivo());
            cargue.setCarguearchivosEstado(CargueArchivoMensajes.SIN_PROCESAR.toString());
            cargue.setCarguearchivosFecha(new Date());
            cargue.setCarguearchivosNombreDatos(data.getNombre());
            cargue.setTipoCargue(Constantes.CARGUE_PROPIETARIOS);
            //sesion
            cargue.setCarguearchivosUsuario(user);
            cargue.setCarguearchivosIdautra(autoridad);

            //xfordware for
            cargue.setCarguearchivosIp(ip);

            cargueArchivoDAO.insertCargue(cargue);
            Long idCargue = cargue.getCarguearchivosId();
            ut.commit();
            respuestaDTO.setIdCargue(idCargue);
            respuestaDTO.setEstado(CargueArchivoMensajes.SIN_PROCESAR);

            String pathArchivo = guardarArchivosAdjuntos(data, cargue.getCarguearchivosId());
            cargue.setCarguearchivosDatos(pathArchivo);

            cargueArchivoDAO.actualizarPathFile(cargue);
        } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException | SystemException e) {
            respuestaDTO.setEstado(CargueArchivoMensajes.RECHAZADO);
            Logger.getLogger(CargueArchivoPropLogica.class.getName()).log(Level.SEVERE, null, e);
        } catch (NotSupportedException ex) {
            Logger.getLogger(CargueArchivoPropLogica.class.getName()).log(Level.SEVERE, null, ex);
        }

        return respuestaDTO;
    }

    /**
     * Metodo que se encarga de crear el directorio y de guardar el archivo en
     * el
     *
     * @param listaAdjuntos
     * @param idCargue
     * @return
     */
    private String guardarArchivosAdjuntos(CargueArchivoDTO listaAdjuntos, Long idCargue) {

        ComConstantes constante = constanteDAO.consultarPorGrupoYNombre(Constantes.GRUPO_CARGUE_ARCHIVO, Constantes.CONSTANTE_PATH_FILE_SYSTEM_CARGUE_ARCHIVO);
        Date fechaSistema = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaSistema);
        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        StringBuilder pathCarpeta = new StringBuilder();
        String pathBase;
        String pathFile = "";

        if (constante != null) {
            try {
                pathBase = constante.getConstanteValor();
                pathCarpeta.append(pathBase);
                File fileSystem = new File(pathCarpeta.toString());
                // Se crea el directorio principal
                if (!fileSystem.exists()) {
                    fileSystem.mkdir();
                }
                pathCarpeta.append("/").append(String.valueOf(anio));
                fileSystem = new File(pathCarpeta.toString());
                if (!fileSystem.exists()) {
                    fileSystem.mkdir();
                }
                pathCarpeta.append("/").append(String.valueOf(mes));
                fileSystem = new File(pathCarpeta.toString());
                if (!fileSystem.exists()) {
                    fileSystem.mkdir();
                }

                pathCarpeta.append("/").append(idCargue.toString());
                fileSystem = new File(pathCarpeta.toString());
                if (!fileSystem.exists()) {
                    fileSystem.mkdir();
                }
                //Concatena el número de ticket al path donde se guarda el archivo firmado
                pathCarpeta.append("/").append(listaAdjuntos.getNroTicket());
                fileSystem = new File(pathCarpeta.toString());
                if (!fileSystem.exists()) {
                    fileSystem.mkdir();
                }
                LOG.log(Level.INFO, "=== PATH CARPETA SOLICITUD : {0}", pathCarpeta.toString());

                if (listaAdjuntos != null) {

                    pathFile = pathCarpeta.toString() + "/" + listaAdjuntos.getNombre();

                    LOG.log(Level.INFO, "=== PATH ARCHIVO : {0}", pathFile);
                    if (listaAdjuntos.getIdArchivo() == null) {
                        byte[] file = Base64.decodeBase64(listaAdjuntos.getArchivo());
                        FileUtils.writeByteArrayToFile(new File(pathFile), file);
                    }
                }
            } catch (IOException e) {
                LOG.log(Level.INFO, "Error en guardarArchivosAdjuntos : {0}", e.getCause());
            }
        }
        return pathFile;
    }

    /**
     * Metodo que valida el número de archivos cargados
     *
     * @param autoridad
     * @return
     */
    private boolean validaNroArchivosCargados(CargueArchivoDTO data) {
        Pattern regExp = Pattern.compile(Constantes.EXPRESION_ID_SECRETARIA);
        Matcher validaRegExp = regExp.matcher(data.getNombre());
        if (validaRegExp.find()) {
            String autoridad = validaRegExp.group(1);
            Integer cantArchivosCargados = cargueArchivoDAO.nroArchivosCargados(autoridad, new Date());
            ComConstantes constante = constanteDAO.consultarPorGrupoYNombre(Constantes.GRUPO_CARGUE_ARCHIVO, Constantes.VALOR_CONSTANTE_CANTIDAD_ARCHIVOS);
            Integer cantArchivosPermitidos = Integer.parseInt(constante.getConstanteValor());
            if(cantArchivosCargados.intValue() == cantArchivosPermitidos.intValue()){
                return false;
            }
            if(cantArchivosCargados > cantArchivosPermitidos){
                return false;
            }
        }
        return true;
    }

    /*
     *Metodo que valida si el id el organismo de transtio existe en la base de datos
     */
    private Integer validaIdOt(final String oTNombreArchivo) {
        try {
            Long validaot = Long.parseLong(oTNombreArchivo);
            Integer lista = constanteDAO.validaOt(validaot);
            if (lista != 0) {
                return 1;
            }
        } catch (NumberFormatException e) {
            LOG.log(Level.SEVERE, "Error al validar el OT{0}", e.getMessage());
        }
        return -1;
    }

}
