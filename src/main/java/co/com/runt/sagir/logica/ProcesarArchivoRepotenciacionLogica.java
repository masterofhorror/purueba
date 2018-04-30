/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.logica;

import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.common.Mensajes;
import co.com.runt.sagir.dao.BaseDAO;
import co.com.runt.sagir.dao.CargueRepotenciacionDAO;
import co.com.runt.sagir.dao.ConstanteDAO;
import co.com.runt.sagir.dao.PlSqlDAO;
import co.com.runt.sagir.dto.ArchivoPropietarioDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.entities.MgCarRepotenciacion;
import co.com.runt.sagir.entities.MgConfCarRepotenciacion;
import co.com.runt.sagir.entities.SgCarguearchivos;
import co.com.runt.sagir.utils.ListarBoletinException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author dsalamanca
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ProcesarArchivoRepotenciacionLogica {

    @EJB
    private ConstanteDAO constanteDAO;

    @EJB
    private CargueRepotenciacionDAO repotenciacionDAO;

    @EJB
    private PlSqlDAO almacenados;

    @EJB
    private CargarArchivoRepotenciacionLogica repotenciacionLogica;

    @Resource
    protected EJBContext context;

    private static final Logger LOG = Logger.getLogger(ConstanteDAO.class.getName());

    /**
     * Metodo que se encarga de procesar el cargue de repotenciacion
     *
     * @param nroTicket
     * @param idSecretaria
     * @return
     */
    public MensajeDTO procesar(final String nroTicket, final Long idSecretaria) {
        MensajeDTO salida = new MensajeDTO();
        String mensaje;
        try {
            SgCarguearchivos validaArchivo = constanteDAO.consultaByTicket(nroTicket);
            SgCarguearchivos carguearchivos = new SgCarguearchivos();
            if (validaArchivo != null) {
                carguearchivos.setCarguearchivosDatos(validaArchivo.getCarguearchivosDatos());
                carguearchivos.setCarguearchivosId(validaArchivo.getCarguearchivosId());
                carguearchivos.setCarguearchivosIdautra(idSecretaria.toString());
                carguearchivos.setTipoCargue(validaArchivo.getTipoCargue());
                carguearchivos.setCarguearchivosFecha(validaArchivo.getCarguearchivosFecha());
                carguearchivos.setCarguearchivosIp(validaArchivo.getCarguearchivosIp());
                carguearchivos.setCarguearchivosNombreDatos(validaArchivo.getCarguearchivosNombreDatos());
                carguearchivos.setIdCarga(validaArchivo.getIdCarga());
                carguearchivos.setCarguearchivosUsuario(validaArchivo.getCarguearchivosUsuario());
                //Valida si el proceso es repotenciación
                if (Constantes.CARGUE_REPOTENCIACION.equals(validaArchivo.getTipoCargue())) {
                    boolean procesoCorrecto = procesarArchivoRepotenciacion(validaArchivo.getCarguearchivosNombreDatos(), validaArchivo.getCarguearchivosDatos(),
                            idSecretaria, validaArchivo.getTipoCargue(), validaArchivo.getCarguearchivosUsuario());
                    if (procesoCorrecto) {
                        carguearchivos.setCarguearchivosEstado(Constantes.ESTADO_PROCESARO);
                        boolean actualizaEstado = constanteDAO.actualizarEstadoCargue(carguearchivos);
                        if (actualizaEstado) {
                            salida.setCodmensaje(Mensajes.OK);
                            mensaje = "Se proceso el archivo de repotenciación de forma correcta";
                            salida.setMensaje(mensaje);
                        } else {
                            salida.setCodmensaje(Mensajes.ERROR);
                            mensaje = "Error al actualizar el estado del cargue";
                            salida.setMensaje(mensaje);
                        }
                    } else {
                        carguearchivos.setCarguearchivosEstado(Constantes.ESTADO_RECHAZADO);
                        boolean actualizaEstado = constanteDAO.actualizarEstadoCargue(carguearchivos);
                        if (actualizaEstado) {
                            salida.setCodmensaje(Mensajes.ERROR);
                            mensaje = "Error al procesar el archivo";
                            salida.setMensaje(mensaje);
                        } else {
                            salida.setCodmensaje(Mensajes.ERROR);
                            mensaje = "Error al actualizar el estado del cargue";
                            salida.setMensaje(mensaje);
                        }
                    }
                }
            } else {
                salida.setCodmensaje(Mensajes.ERROR);
                mensaje = "No existe un archivo con el número de ticket ingresado";
                salida.setMensaje(mensaje);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e.getLocalizedMessage());
        }
        return salida;
    }

    /**
     * Metodo que invoca el procedimiento almacenado para procesar el cargue de
     * repotenciaciaón
     *
     * @param nombreArchivo
     * @param ruta
     * @param idSecretaria
     * @param tipoCargue
     * @param usuario
     * @return
     */
    public boolean procesarArchivoRepotenciacion(final String nombreArchivo, final String ruta, final Long idSecretaria, final String tipoCargue, final String usuario) {
        try {
            MgConfCarRepotenciacion Confrepotenciacion = new MgConfCarRepotenciacion();
            Confrepotenciacion.setCantidadActualizados(0);
            Confrepotenciacion.setCantidadReportada(0);
            Confrepotenciacion.setCodEstado(1);
            Confrepotenciacion.setFechaCarga(new Date());
            Confrepotenciacion.setIdSecretaria(idSecretaria);
            Confrepotenciacion.setNombreArchivo(ruta);
            UserTransaction ut = context.getUserTransaction();
            ut.begin();
            repotenciacionLogica.registraMgCarRepotenciacion(ruta, nombreArchivo, idSecretaria);
            boolean registroConfProceso = repotenciacionDAO.mgConfCarRepotenciacion(Confrepotenciacion);
            if (registroConfProceso) {
                MgConfCarRepotenciacion Confcargue = repotenciacionDAO.consultaMgRepotenciacion(ruta);
                Long idCargue = Confcargue.getCodCarga();
                boolean registroCarRepotenciacion = registraMgCarRepotenciacion(ruta, nombreArchivo, usuario, idCargue, idSecretaria);
                if (registroCarRepotenciacion) {
                    ut.commit();
                    almacenados.procesarRepotenciacion(Integer.parseInt(idCargue.toString()));       
                    return true;
                } else {
                    LOG.log(Level.SEVERE, "Error al registrar el proceso.");
                    ut.rollback();
                }
            } else {
                LOG.log(Level.SEVERE, "Error al registrar el proceso.");
            }
        } catch (IllegalStateException | NumberFormatException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {

        }

        return false;
    }

    /**
     * Metodo que registra en la tabla MG_CAR_REPOTENCIACION
     *
     * @param ruta
     * @param nombreArchivo
     * @param usuario
     * @param idCargue
     * @param idSecretaria
     * @return
     */
    public boolean registraMgCarRepotenciacion(final String ruta, final String nombreArchivo, final String usuario, final Long idCargue, final Long idSecretaria) {
        List<ArchivoPropietarioDTO> listDTO = repotenciacionLogica.datosArchivo(ruta, nombreArchivo, ruta, idSecretaria);
        if (listDTO != null && !listDTO.isEmpty()) {
            int pos = 1;
            for (ArchivoPropietarioDTO cast : listDTO) {
                MgCarRepotenciacion repotenciacion = new MgCarRepotenciacion();
                repotenciacion.setCodCarga(idCargue);
                repotenciacion.setCodEstado(0);
                repotenciacion.setNroregistro(pos++);
                repotenciacion.setRegistro(cast.getDatos());
                boolean registroCargue = repotenciacionDAO.mgCarRepotenciacion(repotenciacion);
                if (registroCargue) {
                    return true;
                } else {
                    LOG.log(Level.SEVERE, "Error al registrar el cargue");
                    return false;
                }
            }
        }
        return false;
    }
    
    public byte[] descargarPDF(final String nroTicket) {
        try {
            //Se devuelve el resultado de la generaciÃ³n del boletin
            return generarBoletinRepotenciacionPdf(nroTicket);
        } catch (ListarBoletinException ex) {
            ex.getLocalizedMessage();
        }
        return new byte[0];
    }

    /**
     * Metodo que genera el boletín del proceso de cargue de repotenciación
     *
     * @param nroTicket
     * @return
     * @throws ListarBoletinException
     */
    public byte[] generarBoletinRepotenciacionPdf(final String nroTicket) throws ListarBoletinException {
        Connection connection = null;
        MgConfCarRepotenciacion consulta = repotenciacionDAO.consultaPorNroTicket(nroTicket);
        if (consulta != null) {
            String codCarga = consulta.getCodCarga().toString();
            try {
                InputStream is = ProcesarArchivoRepotenciacionLogica.class.getResourceAsStream("BoletinRepotenciacion.jasper");
                Map<String, Object> parameters = new HashMap<>();
                //Código de la carga
                parameters.put("P_COD_CARGA", codCarga);

                connection = BaseDAO.getConnection();

                JasperPrint jasperPrint = JasperFillManager.fillReport(is, parameters, connection);
                //Se exporta el reporte como un pdf y se asigna aun arreglo de bytes
                byte[] archivo = JasperExportManager.exportReportToPdf(jasperPrint);

                //Se retorna el arreglo de bytes con el reporte generado
                return archivo;
            } catch (SQLException | JRException ex) {
                LOG.log(Level.SEVERE, null, ex);
                throw new ListarBoletinException(ex);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, null, e);
                throw new ListarBoletinException(e);
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException ex) {
                        LOG.log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return new byte[0];
    }
}
