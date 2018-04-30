/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.logica;

import co.com.runt.logconsultas.api.LogConsultaDTO;
import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.common.Mensajes;
import co.com.runt.sagir.dao.ConstanteDAO;
import co.com.runt.sagir.dao.ConsultaRNCMigraDAO;
import co.com.runt.sagir.dto.ConsultaFinalRNCMigraDTO;
import co.com.runt.sagir.dto.ConsultaRNCMigraDTO;
import co.com.runt.sagir.dto.ConsultaRNCMigraIntermediaDTO;
import co.com.runt.sagir.dto.ConsultaRechazosRNCMigraDTO;
import co.com.runt.sagir.dto.InfoUsuarioDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.entities.ConsultaRNCFinalMigra;
import co.com.runt.sagir.entities.ConsultaRNCMigra;
import co.com.runt.sagir.entities.ConsultaRNCMigraIntermedia;
import co.com.runt.sagir.entities.ConsultaRechazosRNCMigra;
import co.com.runt.sagir.utils.TransformacionDozer;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Dsalamanca
 */
@Stateless
public class ConsultaRNCMigraLogica {

    private static final Logger LOG = Logger.getLogger(ConsultaRNCMigraLogica.class.getSimpleName());

    @EJB
    private ConsultaRNCMigraDAO consultaRNCMigraDAO;

    @EJB
    private ConstanteDAO constanteDAO;

    @EJB
    private LogsConsultasLogica guardarLogLogica;

    public MensajeDTO consultaLicenciasMigrado(final Long idDocumento, final String nroDocumento, final InfoUsuarioDTO usuario, final String ip) {
        MensajeDTO salida = new MensajeDTO();
        String mensaje;
        String tipoConsulta;
        if (idDocumento != null) {
            if (idDocumento.compareTo(1L) == 0) {
                try {
                    List<ConsultaRNCMigraDTO> listDTO;
                    List<ConsultaRNCMigra> rncMigras = consultaRNCMigraDAO.consultaRNCMigraNroDocu(nroDocumento);
                    String tipoDocumento = constanteDAO.tipoDocumento(idDocumento);
                    JsonObject json = new JsonObject();
                    json.addProperty(Constantes.TIPODOCUMENTO, tipoDocumento);
                    json.addProperty(Constantes.NRODOCUMENTO, nroDocumento);
                    //guardado
                    tipoConsulta = Constantes.TIPO_CONSULTA_RNC_MIGRA;
                    Long idauttra = usuario.getIdOrganizacion();
                    String datoUsuario = usuario.getLogin();
                    LogConsultaDTO log = guardarLogLogica.casteadorLogRNC(json, idauttra, datoUsuario, ip, tipoConsulta);
                    guardarLogLogica.guardarLog(log);
                    if (!rncMigras.isEmpty()) {
                        listDTO = TransformacionDozer.transformar(rncMigras, ConsultaRNCMigraDTO.class);
                        salida.setCodmensaje(Mensajes.OK);
                        salida.setObject(listDTO);
                    } else {
                        salida.setCodmensaje(Mensajes.ERROR);
                        mensaje = "No se encontraron datos con el número de documento ingresado";
                        salida.setMensaje(mensaje);
                    }
                } catch (NumberFormatException e) {
                    LOG.log(Level.SEVERE, "Error al consultar las tablas de migración ", e);
                    salida.setCodmensaje(Mensajes.ERROR);
                }
            } else {
                try {
                    List<ConsultaRNCMigraDTO> listDTO;
                    List<ConsultaRNCMigra> rncMigras = consultaRNCMigraDAO.consultaRNCMigraNroLc(nroDocumento);
                    String tipoDocumento = constanteDAO.tipoDocumento(idDocumento);
                    JsonObject json = new JsonObject();
                    json.addProperty(Constantes.TIPODOCUMENTO, tipoDocumento);
                    json.addProperty(Constantes.NRODOCUMENTO, nroDocumento);
                    //guardado
                    tipoConsulta = Constantes.TIPO_CONSULTA_RNC_MIGRA;
                    Long idauttra = usuario.getIdOrganizacion();
                    String datoUsuario = usuario.getLogin();
                    LogConsultaDTO log = guardarLogLogica.casteadorLogRNC(json, idauttra, datoUsuario, ip, tipoConsulta);
                    guardarLogLogica.guardarLog(log);
                    if (!rncMigras.isEmpty()) {
                        listDTO = TransformacionDozer.transformar(rncMigras, ConsultaRNCMigraDTO.class);
                        salida.setCodmensaje(Mensajes.OK);
                        salida.setObject(listDTO);
                    } else {
                        salida.setCodmensaje(Mensajes.ERROR);
                        mensaje = "No se encontraron datos con el número de documento ingresado";
                        salida.setMensaje(mensaje);
                    }
                } catch (NumberFormatException e) {
                    LOG.log(Level.SEVERE, "Error al consultar las tablas de migración ", e);
                    salida.setCodmensaje(Mensajes.ERROR);
                }
            }
        } else {
            salida.setCodmensaje(Mensajes.ERROR);
            mensaje = "Debe seleccionar el tipo de documento";
            salida.setMensaje(mensaje);
        }

        return salida;
    }

    public MensajeDTO consultaRNCIntermedia(final Long idDocumento, final String nroDocumento, final InfoUsuarioDTO usuario, final String ip) {
        MensajeDTO salida = new MensajeDTO();
        String mensaje;
        String tipoConsulta;
        if (idDocumento != null) {
            if (idDocumento.compareTo(1L) == 0) {
                try {
                    List<ConsultaRNCMigraIntermediaDTO> listDTO;
                    List<ConsultaRNCMigraIntermedia> intermedia = consultaRNCMigraDAO.consultaRNCIntermediaNroDoc(nroDocumento);
                    String tipoDocumento = constanteDAO.tipoDocumento(idDocumento);
                    JsonObject json = new JsonObject();
                    json.addProperty(Constantes.TIPODOCUMENTO, tipoDocumento);
                    json.addProperty(Constantes.NRODOCUMENTO, nroDocumento);
                    tipoConsulta = Constantes.TIPO_CONSULTA_RNC_INTER;
                    Long idauttra = usuario.getIdOrganizacion();
                    String datoUsuario = usuario.getLogin();
                    LogConsultaDTO log = guardarLogLogica.casteadorLogRNC(json, idauttra, datoUsuario, ip, tipoConsulta);
                    guardarLogLogica.guardarLog(log);
                    if (!intermedia.isEmpty()) {
                        listDTO = TransformacionDozer.transformar(intermedia, ConsultaRNCMigraIntermediaDTO.class);
                        salida.setCodmensaje(Mensajes.OK);
                        salida.setObject(listDTO);
                    } else {
                        salida.setCodmensaje(Mensajes.ERROR);
                        mensaje = "No se encontraron datos con el número de documento ingresado";
                        salida.setMensaje(mensaje);
                    }
                } catch (NumberFormatException e) {
                    LOG.log(Level.SEVERE, "Error al consultar las tablas intermedias");
                    salida.setCodmensaje(Mensajes.ERROR);
                }
            } else {
                try {
                    List<ConsultaRNCMigraIntermediaDTO> listDTO;
                    List<ConsultaRNCMigraIntermedia> intermedia = consultaRNCMigraDAO.consultaRNCIntermediaNroLic(nroDocumento);
                    String tipoDocumento = constanteDAO.tipoDocumento(idDocumento);
                    JsonObject json = new JsonObject();
                    json.addProperty(Constantes.TIPODOCUMENTO, tipoDocumento);
                    json.addProperty(Constantes.NRODOCUMENTO, nroDocumento);
                    tipoConsulta = Constantes.TIPO_CONSULTA_RNC_INTER;
                    Long idauttra = usuario.getIdOrganizacion();
                    String datoUsuario = usuario.getLogin();
                    LogConsultaDTO log = guardarLogLogica.casteadorLogRNC(json, idauttra, datoUsuario, ip, tipoConsulta);
                    guardarLogLogica.guardarLog(log);
                    if (!intermedia.isEmpty()) {
                        listDTO = TransformacionDozer.transformar(intermedia, ConsultaRNCMigraIntermediaDTO.class);
                        salida.setCodmensaje(Mensajes.OK);
                        salida.setObject(listDTO);
                    } else {
                        salida.setCodmensaje(Mensajes.ERROR);
                        mensaje = ("No se encontraron datos con el número de documento ingresado");
                        salida.setMensaje(mensaje);
                    }
                } catch (NumberFormatException e) {
                    LOG.log(Level.SEVERE, "Error al conusltar las tablas intermedias");
                    salida.setCodmensaje(Mensajes.ERROR);
                }
            }
        } else {
            salida.setCodmensaje(Mensajes.ERROR);
            mensaje = "Debe seleccionar el tipo de documento";
            salida.setMensaje(mensaje);
        }
        return salida;
    }

    public MensajeDTO consultaRechazosRNC(final Long idDocumento, final String nroDocumento, final InfoUsuarioDTO usuario, final String ip) {
        MensajeDTO salida = new MensajeDTO();
        String mensaje;
        String tipoConsulta;
        if (idDocumento != null) {
            if (idDocumento.compareTo(1L) == 0) {
                try {
                    List<ConsultaRechazosRNCMigraDTO> listDTO;
                    List<ConsultaRechazosRNCMigra> rechazosRNCMigra = consultaRNCMigraDAO.consultaRechazosRNCNroDoc(nroDocumento);
                    String tipoDocumento = constanteDAO.tipoDocumento(idDocumento);
                    JsonObject json = new JsonObject();
                    json.addProperty(Constantes.TIPODOCUMENTO, tipoDocumento);
                    json.addProperty(Constantes.NRODOCUMENTO, nroDocumento);
                    tipoConsulta = Constantes.TIPO_CONSULTA_RNC_RECHAZO;
                    Long idauttra = usuario.getIdOrganizacion();
                    String datoUsuario = usuario.getLogin();
                    LogConsultaDTO log = guardarLogLogica.casteadorLogRNC(json, idauttra, datoUsuario, ip, tipoConsulta);
                    guardarLogLogica.guardarLog(log);
                    if (!rechazosRNCMigra.isEmpty()) {
                        listDTO = TransformacionDozer.transformar(rechazosRNCMigra, ConsultaRechazosRNCMigraDTO.class);
                        salida.setCodmensaje(Mensajes.OK);
                        salida.setObject(listDTO);
                    } else {
                        salida.setCodmensaje(Mensajes.ERROR);
                        mensaje = "No se encontraron datos con el número de documento ingresado";
                        salida.setMensaje(mensaje);
                    }
                } catch (NumberFormatException e) {
                    LOG.log(Level.SEVERE, "Error al consultar los rechazos");
                    salida.setCodmensaje(Mensajes.ERROR);
                }

            } else {
                try {
                    List<ConsultaRechazosRNCMigraDTO> listDTO;
                    List<ConsultaRechazosRNCMigra> rechazosRNCMigra = consultaRNCMigraDAO.consultaRechazosRNCNroLc(nroDocumento);
                    String tipoDocumento = constanteDAO.tipoDocumento(idDocumento);
                    JsonObject json = new JsonObject();
                    json.addProperty(Constantes.TIPODOCUMENTO, tipoDocumento);
                    json.addProperty(Constantes.NRODOCUMENTO, nroDocumento);
                    tipoConsulta = Constantes.TIPO_CONSULTA_RNC_RECHAZO;
                    Long idauttra = usuario.getIdOrganizacion();
                    String datoUsuario = usuario.getLogin();
                    LogConsultaDTO log = guardarLogLogica.casteadorLogRNC(json, idauttra, datoUsuario, ip, tipoConsulta);
                    guardarLogLogica.guardarLog(log);
                    if (!rechazosRNCMigra.isEmpty()) {
                        listDTO = TransformacionDozer.transformar(rechazosRNCMigra, ConsultaRechazosRNCMigraDTO.class);
                        salida.setCodmensaje(Mensajes.OK);
                        salida.setObject(listDTO);
                    } else {
                        salida.setCodmensaje(Mensajes.ERROR);
                        mensaje = "No se encontraron datos con el número de documento ingresado";
                        salida.setMensaje(mensaje);
                    }
                } catch (NumberFormatException e) {
                    LOG.log(Level.SEVERE, "Error al consultar los rechazos");
                    salida.setCodmensaje(Mensajes.ERROR);
                }
            }
        } else {
            salida.setCodmensaje(Mensajes.ERROR);
            mensaje = "Debe seleccionar el tipo de documento";
            salida.setMensaje(mensaje);
        }
        return salida;
    }

    public MensajeDTO consultaFinalRNC(final Long idDocumento, final String nroDocumento, final InfoUsuarioDTO usuario, final String ip) {
        MensajeDTO salida = new MensajeDTO();
        String mensaje;
        String tipoConsulta;
        if (idDocumento != null) {
            if (idDocumento.compareTo(1L) == 0) {
                try {
                    List<ConsultaFinalRNCMigraDTO> listDTO;
                    List<ConsultaRNCFinalMigra> finalRNC = consultaRNCMigraDAO.consultaRNCFinalNroDoc(nroDocumento);
                    String tipoDocumento = constanteDAO.tipoDocumento(idDocumento);
                    JsonObject json = new JsonObject();
                    json.addProperty(Constantes.TIPODOCUMENTO, tipoDocumento);
                    json.addProperty(Constantes.NRODOCUMENTO, nroDocumento);
                    tipoConsulta = Constantes.TIPO_CONSULTA_RNC_SAGIR;
                    Long idauttra = usuario.getIdOrganizacion();
                    String datoUsuario = usuario.getLogin();
                    LogConsultaDTO log = guardarLogLogica.casteadorLogRNC(json, idauttra, datoUsuario, ip, tipoConsulta);
                    guardarLogLogica.guardarLog(log);
                    if (!finalRNC.isEmpty()) {
                        listDTO = TransformacionDozer.transformar(finalRNC, ConsultaFinalRNCMigraDTO.class);
                        salida.setCodmensaje(Mensajes.OK);
                        salida.setObject(listDTO);
                    } else {
                        salida.setCodmensaje(Mensajes.ERROR);
                        mensaje = "No se encontraron datos con el número de documento ingresado";
                        salida.setMensaje(mensaje);
                    }
                } catch (NumberFormatException e) {
                    LOG.log(Level.SEVERE, "Error al consultar los rechazos");
                    salida.setCodmensaje(Mensajes.ERROR);
                }
            } else {
                try {
                    List<ConsultaFinalRNCMigraDTO> listDTO;
                    List<ConsultaRNCFinalMigra> finalRNC = consultaRNCMigraDAO.consultaRNCFinalNroLic(nroDocumento);
                    String tipoDocumento = constanteDAO.tipoDocumento(idDocumento);
                    JsonObject json = new JsonObject();
                    json.addProperty(Constantes.TIPODOCUMENTO, tipoDocumento);
                    json.addProperty(Constantes.NRODOCUMENTO, nroDocumento);
                    tipoConsulta = Constantes.TIPO_CONSULTA_RNC_SAGIR;
                    Long idauttra = usuario.getIdOrganizacion();
                    String datoUsuario = usuario.getLogin();
                    LogConsultaDTO log = guardarLogLogica.casteadorLogRNC(json, idauttra, datoUsuario, ip, tipoConsulta);
                    guardarLogLogica.guardarLog(log);
                    if (!finalRNC.isEmpty()) {
                        listDTO = TransformacionDozer.transformar(finalRNC, ConsultaFinalRNCMigraDTO.class);
                        salida.setCodmensaje(Mensajes.OK);
                        salida.setObject(listDTO);
                    } else {
                        salida.setCodmensaje(Mensajes.ERROR);
                        mensaje = "No se encontraron datos con el número de documento ingresado";
                        salida.setMensaje(mensaje);
                    }
                } catch (NumberFormatException e) {
                    LOG.log(Level.SEVERE, "Error al consultar los rechazos");
                    salida.setCodmensaje(Mensajes.ERROR);
                }
            }
        } else {
            salida.setCodmensaje(Mensajes.ERROR);
            mensaje = ("Debe seleccionar el tipo de documento");
            salida.setMensaje(mensaje);
        }
        return salida;
    }

}
