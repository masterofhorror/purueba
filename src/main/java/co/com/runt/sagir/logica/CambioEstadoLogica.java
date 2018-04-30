/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.logica;

import co.com.runt.sagir.common.Casteador;
import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.common.Mensajes;
import co.com.runt.sagir.dao.CambioFormatoPlacaNuevoDAO;
import co.com.runt.sagir.dao.CambioRNADAO;
import co.com.runt.sagir.dao.ConstanteDAO;
import co.com.runt.sagir.dao.PlSqlDAO;
import co.com.runt.sagir.dto.AutomotorDTO;
import co.com.runt.sagir.dto.InfoUsuarioDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.entities.Automotor;
import co.com.runt.sagir.entities.HiAutomotor;
import co.com.runt.sagir.entities.LicTto;
import co.com.runt.sagir.entities.MgActualizacionRna;
import co.com.runt.sagir.entities.TblLicTto;
import co.com.runt.sagir.utils.TransformacionDozer;
import java.util.List;
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

/**
 *
 * @author dsalamanca
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class CambioEstadoLogica {

    private static final Logger LOG = Logger.getLogger(CambioEstadoLogica.class.getName());

    @Resource
    protected EJBContext context;

    @EJB
    private CambioFormatoPlacaNuevoDAO validaRegistroDAO;

    @EJB
    private CambioRNADAO cambioRNADAO;

    @EJB
    private ConstanteDAO constanteDAO;

    @EJB
    private Casteador casteador;

    @EJB
    private PlSqlDAO almacenados;

    @EJB
    private CommonLogica commonLogica;

    public MensajeDTO cambioEstadoVehiculo(final String placa, final Integer estadoVehicSolicitado, final String nroTicket, final InfoUsuarioDTO info) {
        MensajeDTO salida = new MensajeDTO();
        String mensaje;
        Integer validaMigracion = constanteDAO.countLicTto(placa);
        if (validaMigracion == 0) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("El número de placa ingresado no se encuentra en la base de datos de Migración");
            return salida;
        }
        String validaRnrysRnma = commonLogica.validaRnrysRnma(placa);
        if (validaRnrysRnma != null) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje(validaRnrysRnma);
            return salida;
        }
        Automotor validaAutomotor = constanteDAO.consultaVehiculo(placa);
        //Valida que el vehículo se encuentre en el RNA
        if (validaAutomotor == null) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("El número de placa ingresado no se encuentra registrado en la base de datos de Producción");
            return salida;
        }
        Integer validaClase = constanteDAO.validaCamionTracto(placa);
        //Valida que el vehículo no sea clase camión o tracto camión
        if (validaClase != 0) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("El vehículo ingresado es de carga.");
            return salida;
        }
        Integer validaPostulacion = constanteDAO.countPostulacionByPlaca(placa);
        if (validaPostulacion != 0) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("El vehículo ingresado se encuentra postulado.");
            return salida;
        }
        Integer validaTramites = constanteDAO.countTramitesByPlaca(placa);
        //Valida que el vehículo no registre trámites ante RUNT
        if (validaTramites != 0) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("El vehículo ingresado registra trámites ante el RUNT.");
            return salida;
        }
        Integer tipoRegistro = Constantes.TIPO_PROCESO_CAMBIO_ESTADO;
        Integer validaRegistro = constanteDAO.validaRegistroProceso(placa, tipoRegistro);
        //Valida si el vehículo registra un proceso de actualización.
        if (validaRegistro != 0) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("El número de placa ya registra una solicitud de cambio de servicio");
            return salida;
        }
        String estadoAutomotor = validaAutomotor.getAutomotorEstavehicNombre();
        String estadoSolicita = constanteDAO.estadoVehiculo(estadoVehicSolicitado);
        if (estadoAutomotor.equals(estadoSolicita)) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("El estado seleccionado es el mismo que poseé registrado el vehículo ingresado.");
            return salida;
        }
        if (estadoAutomotor.equals(Constantes.ESTADO_CANCELADO)) {
            mensaje = "El estado del vehículo es CANCELADO y se quiere modificar a ACTIVO o TRASLADO, esta seguro del cambio";
            salida.setCodmensaje(Mensajes.VALIDADOR);
            salida.setMensaje(mensaje);
        } else {
            TblLicTto consultaLicTto = constanteDAO.consultaVehiculoLicTto(placa);
            if (consultaLicTto == null) {
                salida.setCodmensaje(Mensajes.ERROR);
                salida.setMensaje("El vehículo ingresado no registra un código de carga en estado aprobado");
                return salida;
            }
            Integer codCarga = consultaLicTto.getLicTtoPK().getCodCarga();
            LicTto datos = validaRegistroDAO.consultaVehiculo(placa);
            Automotor automotor = constanteDAO.consultaVehiculo(placa);
            Integer tipoProceso = Constantes.TIPO_PROCESO_CAMBIO_ESTADO;
            MgActualizacionRna actualizacionRna
                    = casteador.castMgActualizacionRna(datos, automotor, estadoAutomotor, estadoSolicita, tipoProceso, info.getLogin());
            HiAutomotor registroLog
                    = casteador.registrarLog(estadoAutomotor, estadoSolicita, Constantes.CAMPO_ESTADO_VEHICULO, info.getLogin(), placa, nroTicket);
            try {
                UserTransaction ut = context.getUserTransaction();
                ut.begin();
                boolean registroProceso = validaRegistroDAO.registraProceso(actualizacionRna);
                boolean registraLog = constanteDAO.registrarLog(registroLog);
                ut.commit();
                if (registraLog) {
                    if (registroProceso) {
                        almacenados.pActualizarEstadoRnaPlaca(placa, codCarga);
                        salida.setCodmensaje(Mensajes.OK);
                        mensaje = "Se proceso el cambio de estado de forma exitosa";
                        salida.setMensaje(mensaje);
                    } else {
                        salida.setCodmensaje(Mensajes.ERROR);
                        mensaje = "Error al registrar el proceso";
                        salida.setMensaje(mensaje);
                        ut.rollback();
                    }
                } else {
                    salida.setCodmensaje(Mensajes.ERROR);
                    mensaje = "Error al registrar el Log";
                    salida.setMensaje(mensaje);
                    ut.rollback();
                }
            } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
                LOG.log(Level.SEVERE, e.getLocalizedMessage());
            }
        }
        return salida;
    }

    public MensajeDTO cambioEstadoCancelado(final String placa, final Integer estadoVehicSolicitado, final String nroTicket, InfoUsuarioDTO info) {
        MensajeDTO salida = new MensajeDTO();
        String mensaje;
        List<LicTto> validaRegistro = validaRegistroDAO.consultaVehiculoEstadoCargue(placa);
        if (validaRegistro != null && !validaRegistro.isEmpty()) {
            for (LicTto lt : validaRegistro) {
                TblLicTto consultaLicTto = constanteDAO.consultaVehiculoLicTto(placa);
                if (consultaLicTto != null) {
                    Integer codCarga = consultaLicTto.getLicTtoPK().getCodCarga();
                    String nroPlaca = lt.getPlaca();
                    Long ot = Long.parseLong(lt.getIdSecretaria());
                    LicTto datos = validaRegistroDAO.consultaVehiculo(placa);
                    Automotor automotor = constanteDAO.consultaVehiculo(placa);
                    Integer tipoProceso = Constantes.TIPO_PROCESO_CAMBIO_ESTADO;
                    Integer estadoVehiculo = constanteDAO.idEstadoVehiculo(lt.getEstadoActual());
                    String estadoActual = constanteDAO.estadoVehiculo(estadoVehiculo);
                    String estadoSolicita = constanteDAO.estadoVehiculo(estadoVehicSolicitado);
                    MgActualizacionRna actualizacionRna
                            = casteador.castMgActualizacionRna(datos, automotor, estadoActual, estadoSolicita, tipoProceso, info.getLogin());
                    HiAutomotor registroLog
                            = casteador.registrarLog(estadoActual, estadoSolicita, Constantes.CAMPO_ESTADO_VEHICULO, info.getLogin(), placa, nroTicket);
                    Integer validaProceso = cambioRNADAO.consultaProceso(nroPlaca, ot, Constantes.TIPO_PROCESO_CAMBIO_ESTADO);
                    if (validaProceso == 0) {
                        salida.setCodmensaje(Mensajes.ERROR);
                        salida.setMensaje("El vehículo ingresado ya registra una solicitud de cambio de servicio");
                        return salida;
                    }
                    try {
                        UserTransaction ut = context.getUserTransaction();
                        ut.begin();
                        boolean registroProceso = validaRegistroDAO.registraProceso(actualizacionRna);
                        boolean validaLog = constanteDAO.registrarLog(registroLog);
                        ut.commit();
                        if (validaLog) {
                            if (registroProceso) {
                                almacenados.pActualizarEstadoRnaPlaca(placa, codCarga);
                                salida.setCodmensaje(Mensajes.OK);
                                mensaje = "Se proceso el cambio de estado de forma exitosa";
                                salida.setMensaje(mensaje);
                            } else {
                                mensaje = "Error al registrar el proceso";
                                salida.setCodmensaje(Mensajes.ERROR);
                                salida.setMensaje(mensaje);
                                ut.rollback();
                            }
                        } else {
                            mensaje = "Error al registrar el Log";
                            salida.setCodmensaje(Mensajes.ERROR);
                            salida.setMensaje(mensaje);
                            ut.rollback();
                        }
                    } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
                        LOG.log(Level.SEVERE, e.getLocalizedMessage());
                    }
                }
            }
        }
        return salida;
    }

    public MensajeDTO consultaVehiculo(final String placa) {
        LOG.log(Level.INFO, "=============Iniciando la consulta=========");
        MensajeDTO salida = new MensajeDTO();
        String validaRnrysRnma = commonLogica.validaRnrysRnma(placa);
        if (validaRnrysRnma == null) {
            AutomotorDTO automotorDTO;
            Automotor automotor = constanteDAO.consultaVehiculo(placa);
            if (automotor != null) {
                automotorDTO = TransformacionDozer.transformar(automotor, AutomotorDTO.class);
                salida.setCodmensaje(Mensajes.OK);
                salida.setObject(automotorDTO);
            } else {
                salida.setCodmensaje(Mensajes.ERROR);
                salida.setMensaje("El número de placa ingresado no se encuentra en la base de datos de Migración.");
            }
        } else {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje(validaRnrysRnma);
        }
        return salida;
    }

}
