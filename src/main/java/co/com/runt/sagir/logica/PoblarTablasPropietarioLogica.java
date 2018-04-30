/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.logica;

import co.com.runt.sagir.common.CommonPropietarioLogica;
import co.com.runt.sagir.common.CargueArchivoMensajes;
import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.common.ValidacionesConstantes;
import co.com.runt.sagir.dao.CambioPropietarioAutomotorDAO;
import co.com.runt.sagir.dao.CargueArchivoDAO;
import co.com.runt.sagir.dao.ConstanteDAO;
import co.com.runt.sagir.dao.PlSqlDAO;
import co.com.runt.sagir.dto.CargueArchivoRespuestaDTO;
import co.com.runt.sagir.entities.Carga;
import co.com.runt.sagir.entities.ConfArcCar;
import co.com.runt.sagir.entities.LogArchivoFolio;
import co.com.runt.sagir.entities.OrganismosTransitoMigrunt;
import co.com.runt.sagir.entities.SgCarguearchivos;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

/**
 *
 * @author dsalamanca
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class PoblarTablasPropietarioLogica {

    private static final Logger LOG = Logger.getLogger(ProcesarCarguesPendientesLogica.class.getName());

    @Resource
    protected EJBContext context;

    @EJB
    private ConstanteDAO constanteDAO;

    @EJB
    private PlSqlDAO almacenadosDAO;

    @EJB
    private CommonPropietarioLogica commonPropietario;

    @EJB
    private CargueArchivoDAO cargueArchivoDAO;

    @EJB
    private CambioPropietarioAutomotorDAO cambioAutomotorDAO;

    @EJB
    private ValidacionesConstantes validacionesConstantes;

    public boolean poblarTablasMigra(final String nombreArchivo, final String rutaArchivo, final Long idSecretaria, final String usuario) {
        boolean procesoCorrecto = true;
        boolean error = false;
        LOG.log(Level.INFO, "=============validando carga archivo=========");
        CargueArchivoRespuestaDTO respuestaDTO = new CargueArchivoRespuestaDTO();
        String mensaje;
        String fechaFolio = validacionesConstantes.convertDatetoString(new Date());
        String idOtArchivo = commonPropietario.consultarSecretaria(rutaArchivo, nombreArchivo, usuario);
        OrganismosTransitoMigrunt autoridad = commonPropietario.obtenerAutoridadHQ(rutaArchivo, nombreArchivo, usuario);
        try {
            Long idOt = Long.parseLong(idOtArchivo);
            String idFolio = Constantes.REGISTRO_FOLIO + "_" + fechaFolio + "_" + idOt;
            SgCarguearchivos sgCarguearchivos = cambioAutomotorDAO.consultaArchivo(nombreArchivo, rutaArchivo);
            if (idFolio != null) {
                Carga carga = new Carga();
                carga.setIdSecretaria(autoridad);
                carga.setFechaCarga(new Date());
                carga.setTipoRegistro(Integer.parseInt(Constantes.TIPO_REGISTRO_RNA));
                carga.setIdFolio(idFolio);
                carga.setCodEstado(0);
                carga.setIdBoletin(null);
                carga.setArchmigraIdentific(Long.parseLong(Constantes.ARCMIGRAIDENTIFIC));
                boolean respuestaRegistraCarga = cargueArchivoDAO.registrarCarga(carga);
                if (respuestaRegistraCarga) {
                    LogArchivoFolio archivoFolio = new LogArchivoFolio();
                    archivoFolio.setIdFolio(idFolio);
                    archivoFolio.setNomArcOriginal(nombreArchivo);
                    archivoFolio.setCodEstado(0);
                    boolean respuestaRegistraLogArchivo
                            = cargueArchivoDAO.registrarLogArchivoInfo(archivoFolio);
                    if (respuestaRegistraLogArchivo) {
                        ConfArcCar confArcCar = new ConfArcCar();
                        confArcCar.setCodEstandar(4);
                        confArcCar.setNomArchivo(rutaArchivo);
                        confArcCar.setCodEstado(0);
                        confArcCar.setIdSecretaria(idOt);
                        confArcCar.setTipoArchivo(1);
                        confArcCar.setFecProceso(new Date());
                        confArcCar.setDesError(null);
                        confArcCar.setNumRegleidos(0);
                        confArcCar.setCodBoletin(0);
                        confArcCar.setCodCarga(carga);
                        confArcCar.setCantError(0);
                        confArcCar.setNomArcOriginal(nombreArchivo);
                        confArcCar.setTipoRegistro(1);
                        boolean respuestaRegistraConfArcCar
                                = cargueArchivoDAO.registrarConfArcCar(confArcCar);
                        if (respuestaRegistraConfArcCar) {
                            Integer respuestaArcCarg = commonPropietario.poblaTablaArcCar(rutaArchivo, nombreArchivo, idOt, confArcCar, carga, usuario);
                            if (respuestaArcCarg == 1) {
                                Integer idCarga = carga.getCodCarga();
                                if (idCarga != null) {
                                    //Procedimientos almacenados
                                    almacenadosDAO.poblamientoRNAProp(idCarga);
                                    almacenadosDAO.aplicaCriterios(idCarga, idOt);
                                    almacenadosDAO.verificaPropietario(idCarga);
                                    almacenadosDAO.cargaPropietario(idCarga);
                                    //Registra la información del archivo en la tabla de log
                                    commonPropietario.registraLogDatosArchivo(rutaArchivo, nombreArchivo, idSecretaria);
                                    //Valida si fue procesado por los procedimientos almacenados
                                    Integer respuesta = constanteDAO.validaEstadoCarga(idCarga);
                                    //Actualiza el estado del cargue del archivo
                                    if (respuesta != 0) {
                                        sgCarguearchivos.setCarguearchivosEstado(CargueArchivoMensajes.PROCESADO.name());
                                        sgCarguearchivos.setIdCarga(idCarga);
                                        constanteDAO.actualizarEstadoCargue(sgCarguearchivos);
                                    } else {
                                        sgCarguearchivos.setCarguearchivosEstado(CargueArchivoMensajes.RECHAZADO.name());
                                        sgCarguearchivos.setIdCarga(idCarga);
                                        constanteDAO.actualizarEstadoCargue(sgCarguearchivos);
                                    }
                                } else {
                                    System.out.println("Error al cargar el id de la carga");
                                    error = true;
                                }
                            } else {
                                error = true;
                                mensaje = "Error al registrar en la tabla ArcCar";
                                respuestaDTO.setMensaje(mensaje);
                                respuestaDTO.setEstado(CargueArchivoMensajes.ERROR);
                            }
                        }
                    } else {
                        error = true;
                        mensaje = "Error al registrar el Log del archivo";
                        respuestaDTO.setMensaje(mensaje);
                        respuestaDTO.setEstado(CargueArchivoMensajes.ERROR);
                    }
                } else {
                    error = true;
                    mensaje = "Error al registrar el código de carga";
                    respuestaDTO.setMensaje(mensaje);
                    respuestaDTO.setEstado(CargueArchivoMensajes.ERROR);
                }
            }
            if (error == true) {
                procesoCorrecto = false;
            }
        } catch (NumberFormatException e) {
            LOG.log(Level.SEVERE, e.getMessage());
            respuestaDTO.setEstado(CargueArchivoMensajes.ERROR);
            respuestaDTO.setMensaje("Error " + e.getLocalizedMessage());
        }
        return procesoCorrecto;
    }

    public CargueArchivoRespuestaDTO procesarCambioPropietario(final String nombreArchivo, final String rutaArchivo, final String usuario) {
        LOG.log(Level.INFO, "=============validando carga archivo=========");
        CargueArchivoRespuestaDTO respuestaDTO = new CargueArchivoRespuestaDTO();
        String fechaFolio = validacionesConstantes.convertDatetoString(new Date());
        String idOtArchivo = commonPropietario.consultarSecretaria(rutaArchivo, nombreArchivo, usuario);
        OrganismosTransitoMigrunt idOtAutoridad = commonPropietario.obtenerAutoridadHQ(rutaArchivo, nombreArchivo, usuario);
        try {
            Long idOt = Long.parseLong(idOtArchivo);
            String idFolio = Constantes.REGISTRO_FOLIO + "_" + fechaFolio + "_" + idOt;
            SgCarguearchivos sgCarguearchivos = cambioAutomotorDAO.consultaArchivo(nombreArchivo, rutaArchivo);
            if (idFolio == null) {
                respuestaDTO.setEstado(CargueArchivoMensajes.ERROR);
                respuestaDTO.setMensaje("Error al generar el número de folio");
                return respuestaDTO;
            }
            Carga carga = commonPropietario.castCargaPropietario(idOtAutoridad, idFolio);
            boolean respuestaRegistraCarga = cargueArchivoDAO.registrarCarga(carga);
            if (!respuestaRegistraCarga) {
                respuestaDTO.setMensaje("Error al registrar el código de carga");
                respuestaDTO.setEstado(CargueArchivoMensajes.ERROR);
                return respuestaDTO;
            }
            LogArchivoFolio archivoFolio = commonPropietario.castLogArchivFolioPropietario(nombreArchivo, idFolio);
            boolean respuestaRegistraLogArchivo = cargueArchivoDAO.registrarLogArchivoInfo(archivoFolio);
            if (!respuestaRegistraLogArchivo) {
                respuestaDTO.setMensaje("Error al registrar el log del archivo");
                respuestaDTO.setEstado(CargueArchivoMensajes.ERROR);
                return respuestaDTO;
            }
            ConfArcCar confArcCar = commonPropietario.castConfArcCarPropietario(rutaArchivo, idOt, carga, nombreArchivo);
            boolean respuestaRegistraConfArcCar = cargueArchivoDAO.registrarConfArcCar(confArcCar);
            if (!respuestaRegistraConfArcCar) {
                respuestaDTO.setMensaje("Error al registrar en la tabla MIGRACIONQX.CONF_ARC_CAR");
                respuestaDTO.setEstado(CargueArchivoMensajes.ERROR);
                return respuestaDTO;
            }
            Integer respuestaArcCarg = commonPropietario.poblaTablaArcCar(rutaArchivo, nombreArchivo, idOt, confArcCar, carga, usuario);
            if (respuestaArcCarg != 1) {
                respuestaDTO.setMensaje("Error al registrar en la tabla MIGRACIONQX.ARC_CAR");
                respuestaDTO.setEstado(CargueArchivoMensajes.ERROR);
                return respuestaDTO;
            }
            Integer idCarga = carga.getCodCarga();
            if (idCarga == null) {
                respuestaDTO.setMensaje("Error al cargar el id de la carga");
                respuestaDTO.setEstado(CargueArchivoMensajes.ERROR);
                return respuestaDTO;
            }
            //Procedimientos almacenados
            almacenadosDAO.poblamientoRNAProp(idCarga);
            almacenadosDAO.aplicaCriterios(idCarga, idOt);
            almacenadosDAO.verificaPropietario(idCarga);
            almacenadosDAO.cargaPropietario(idCarga);
            //Registra la información del archivo en la tabla de log
            commonPropietario.registraLogDatosArchivo(rutaArchivo, nombreArchivo, idOtAutoridad.getIdSecretaria());
            //Valida si fue procesado por los procedimientos almacenados
            Integer respuesta = constanteDAO.validaEstadoCarga(idCarga);
            //Actualiza el estado del cargue del archivo
            if (respuesta != 0) {
                sgCarguearchivos.setCarguearchivosEstado(CargueArchivoMensajes.PROCESADO.name());
                sgCarguearchivos.setIdCarga(idCarga);
                constanteDAO.actualizarEstadoCargue(sgCarguearchivos);
                respuestaDTO.setMensaje("PROCESADO");
            } else {
                sgCarguearchivos.setCarguearchivosEstado(CargueArchivoMensajes.RECHAZADO.name());
                sgCarguearchivos.setIdCarga(idCarga);
                constanteDAO.actualizarEstadoCargue(sgCarguearchivos);
                respuestaDTO.setMensaje("Error al procesar archivo");
                respuestaDTO.setEstado(CargueArchivoMensajes.ERROR);
            }
        } catch (NumberFormatException e) {
            LOG.log(Level.SEVERE, e.getMessage());
            respuestaDTO.setEstado(CargueArchivoMensajes.ERROR);
            respuestaDTO.setMensaje("Error " + e.getLocalizedMessage());
        }

        return respuestaDTO;
    }
}
