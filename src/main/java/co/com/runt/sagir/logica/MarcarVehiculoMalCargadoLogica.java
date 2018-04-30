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
import co.com.runt.sagir.dao.ConstanteDAO;
import co.com.runt.sagir.dao.MarcarVehiculoDAO;
import co.com.runt.sagir.dao.PlSqlDAO;
import co.com.runt.sagir.dto.InfoUsuarioDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.dto.VehiculoMalCargadoMigraDTO;
import co.com.runt.sagir.dto.VehiculoMalCargadoProdDTO;
import co.com.runt.sagir.entities.Automotor;
import co.com.runt.sagir.entities.EvPlaca;
import co.com.runt.sagir.entities.HiAutomotor;
import co.com.runt.sagir.entities.MgActualizacionRna;
import co.com.runt.sagir.entities.Propietario;
import co.com.runt.sagir.entities.TblLicTto;
import co.com.runt.sagir.entities.VehiculoMalCargadoMigra;
import co.com.runt.sagir.entities.VehiculoMalCargadoProd;
import co.com.runt.sagir.utils.TransformacionDozer;
import java.util.List;
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
public class MarcarVehiculoMalCargadoLogica {

    @EJB
    private MarcarVehiculoDAO marcarVehiculoDAO;

    @EJB
    private PlSqlDAO almacenados;

    @EJB
    private ConstanteDAO constanteDAO;

    @EJB
    private CambioFormatoPlacaNuevoDAO formatoPlacaNuevoDAO;

    @EJB
    private Casteador casteador;

    @Resource
    protected EJBContext context;

    @EJB
    private CommonLogica commonLogica;

    public MensajeDTO consultarVehiculoProd(final String placa) {
        MensajeDTO salida = new MensajeDTO();
        String mensaje;
        String validaRnrysRnma = commonLogica.validaRnrysRnma(placa);
        if (validaRnrysRnma == null) {
            List<VehiculoMalCargadoProdDTO> listDTO;
            List<VehiculoMalCargadoProd> malCargadoProd = marcarVehiculoDAO.vehiculoMalCargadoProd(placa);
            if (malCargadoProd != null && !malCargadoProd.isEmpty()) {
                listDTO = TransformacionDozer.transformar(malCargadoProd, VehiculoMalCargadoProdDTO.class);
                salida.setCodmensaje(Mensajes.OK);
                salida.setObject(listDTO);
            } else {
                salida.setCodmensaje(Mensajes.ERROR);
                mensaje = ("La placa ingresada no se encuentra en la base de datos de producción.");
                salida.setMensaje(mensaje);
            }
        } else {
            salida.setCodmensaje(Mensajes.ERROR);
            mensaje = validaRnrysRnma;
            salida.setMensaje(mensaje);
        }
        return salida;
    }

    public MensajeDTO consultarVehiculoMigra(final String placa) {
        MensajeDTO salida = new MensajeDTO();
        String mensaje;
        String validaRnrysRnma = commonLogica.validaRnrysRnma(placa);
        if (validaRnrysRnma == null) {
            Integer formato = 3;
            String nuevaPlaca = almacenados.functionCambioPlaca(placa, formato);
            if (nuevaPlaca != null) {
                List<VehiculoMalCargadoMigraDTO> listDTO;
                List<VehiculoMalCargadoMigra> malCargadoProd = marcarVehiculoDAO.vehiculoMalCargadoMigra(placa, nuevaPlaca);
                if (malCargadoProd != null && !malCargadoProd.isEmpty()) {
                    listDTO = TransformacionDozer.transformar(malCargadoProd, VehiculoMalCargadoMigraDTO.class);
                    salida.setCodmensaje(Mensajes.OK);
                    salida.setObject(listDTO);
                } else {
                    salida.setCodmensaje(Mensajes.ERROR);
                    mensaje = ("La placa ingresada no se encuentra en la base de datos de migración.");
                    salida.setMensaje(mensaje);
                }
            } else {
                salida.setCodmensaje(Mensajes.ERROR);
                mensaje = ("Error al modificar el formato de la placa.");
                salida.setMensaje(mensaje);
            }
        } else {
            salida.setCodmensaje(Mensajes.ERROR);
            mensaje = validaRnrysRnma;
            salida.setMensaje(mensaje);
        }
        return salida;
    }

    /**
     * Metodo que marca con -REP un vehículo en producción y desasocia los
     * propietarios
     *
     * @param placa
     * @param observacion
     * @param usuario
     * @param nroTicket
     * @return
     */
    public MensajeDTO marcarVehiculoProd(final String placa, final String nroTicket, final String observacion, final InfoUsuarioDTO usuario) {
        MensajeDTO salida = new MensajeDTO();
        String mensaje;
        String placaMarcada;
        Integer validaTicket = constanteDAO.countCantidadTickets(nroTicket);
        if (validaTicket != 0) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("El número de ticket ingresado ya se encuentra registrado.");
            return salida;
        }
        String validaRnrysRnma = commonLogica.validaRnrysRnma(placa);
        if (validaRnrysRnma != null) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje(validaRnrysRnma);
            return salida;
        }
        Automotor automotor = constanteDAO.consultaVehiculo(placa);
        if (automotor == null) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("El vehículo ingresado no se encuentra registrado en la base de datos.");
            return salida;
        }
        Integer validaTramites = constanteDAO.validaSolicitudPlaca(placa);
        if (validaTramites != 0) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("El vehículo ingresado poseé trámites registrados.");
            return salida;
        }
        Integer postulacionPlaca = formatoPlacaNuevoDAO.verificaPostulacion(placa);
        if (postulacionPlaca != 0) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("El vehículo ingresado se encuentra postulado.");
            return salida;
        }
        EvPlaca evPlaca = marcarVehiculoDAO.consultaEvPlaca(placa);
        if (evPlaca != null) {
            EvPlaca ep = new EvPlaca();
            ep.setEstadoPlacaNombre(Constantes.ESTADO_ANULADO);
            ep.setFechExped(evPlaca.getFechExped());
            ep.setIdForPla(evPlaca.getIdForPla());
            ep.setIdRango(evPlaca.getIdRango());
            ep.setIdVehiculo(automotor.getAutomotorNroregveh());
            ep.setIdauttra(evPlaca.getIdauttra());
            placaMarcada = placa + Constantes.MARCA_PLACA;
            ep.setPlaca(placaMarcada);
            try {
                UserTransaction ut = context.getUserTransaction();
                ut.begin();
                boolean registraEvPlaca = marcarVehiculoDAO.registraEvPlaca(ep);
                ut.commit();
                if (registraEvPlaca) {
                    Automotor castAutomotor = new Automotor();
                    automotor.setAutomotorEstavehicNombre(Constantes.ESTADO_INACTIVO_VEHIC);
                    automotor.setAutomotorPlacaNumplaca(placaMarcada);
                    boolean actualizaEstadoVehic = constanteDAO.actualizaEstadoVehiculo(automotor);
                    if (actualizaEstadoVehic) {
                        HiAutomotor registroLog
                                = casteador.registrarLog(placa, placaMarcada, Constantes.CAMPO_PLACA_VEHICULO, usuario.getLogin(), placa, nroTicket);
                        boolean registraLog = constanteDAO.registrarLog(registroLog);
                        if (registraLog) {
                            List<Propietario> propietario = constanteDAO.consultaPropietarios(automotor.getAutomotorNroregveh());
                            if (propietario != null && !propietario.isEmpty()) {
                                for (Propietario p : propietario) {
                                    Propietario cast = new Propietario();
                                    cast.setPropietarEstadoNombre(p.getPropietarEstadoNombre());
                                    cast.setPropietarFecfinpro(p.getPropietarFecfinpro());
                                    cast.setPropietarFechactua(p.getPropietarFechactua());
                                    cast.setPropietarFecinipro(p.getPropietarFecinipro());
                                    cast.setPropietarFecmigra(p.getPropietarFecmigra());
                                    cast.setPropietarIdpropiet(p.getPropietarIdpropiet());
                                    cast.setPropietarMigrado(p.getPropietarMigrado());
                                    cast.setPropietarPorcpropi(p.getPropietarPorcpropi());
                                    cast.setPropietarProindivi(p.getPropietarProindivi());
                                    cast.setPropietarProsegest(p.getPropietarProsegest());
                                    cast.setPropietarTipopropiCodtippro(p.getPropietarTipopropiCodtippro());
                                    cast.setTrPersona(p.getTrPersona());
                                    castAutomotor.setAutomotorNroregveh(Long.parseLong(Constantes.MARCA_VEHICULO));
                                    cast.setRaAutomotor(castAutomotor);
                                    String idPropietar = Long.toString(p.getPropietarIdpropiet());
                                    boolean marcaPropietario = constanteDAO.marcaPropietario(cast);
                                    if (marcaPropietario) {
                                        HiAutomotor registroLogPropietar
                                                = casteador.registrarLog(idPropietar, Constantes.MARCA_VEHICULO, Constantes.CAMPO_PLACA_VEHICULO, usuario.getLogin(), placa, nroTicket);
                                        boolean registraLogPropietar = constanteDAO.registrarLog(registroLogPropietar);
                                        if (registraLogPropietar) {
                                            MgActualizacionRna actualizacionRna
                                                    = casteador.castMgActualizacionRnaMarcarVehiculo(automotor, observacion, usuario.getLogin());
                                            boolean registroLogMgActualizacion = constanteDAO.registroMgActualizacionRna(actualizacionRna);
                                            if (registroLogMgActualizacion) {
                                                salida.setCodmensaje(Mensajes.OK);
                                                mensaje = ("Se realizo el proceso de marcación del vehículo de forma correcta.");
                                                salida.setMensaje(mensaje);
                                            }
                                        } else {
                                            salida.setCodmensaje(Mensajes.ERROR);
                                            mensaje = ("Error al desasociar los propietarios.");
                                            salida.setMensaje(mensaje);
                                        }
                                    } else {
                                        salida.setCodmensaje(Mensajes.ERROR);
                                        mensaje = ("Error al desasociar los propietarios.");
                                        salida.setMensaje(mensaje);
                                    }
                                }
                            }
                        }
                    } else {
                        salida.setCodmensaje(Mensajes.ERROR);
                        mensaje = ("Error al actualizar el vehículo en RA_AUTOMOTOR.");
                        salida.setMensaje(mensaje);
                    }
                } else {
                    salida.setCodmensaje(Mensajes.ERROR);
                    mensaje = ("Error al registrar en la tabla EV_PLACA.");
                    salida.setMensaje(mensaje);
                    ut.rollback();
                }
            } catch (IllegalStateException | NumberFormatException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
            }
        }
        return salida;
    }

    /**
     * Metodo que elimina el registro de un vehículo en las tablas de migración
     *
     * @param placa
     * @param observacion
     * @param usuario
     * @param nroTicket
     * @return
     */
    public MensajeDTO eliminaVehiculoMigra(final String placa, final String nroTicket, final String observacion, final InfoUsuarioDTO usuario) {
        MensajeDTO salida = new MensajeDTO();
        String mensaje;
        Integer validaTicket = constanteDAO.countCantidadTickets(nroTicket);
        if (validaTicket != 0) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("El número de ticket ingresado ya se encuentra registrado.");
            return salida;
        }
        Integer validaAutomotorMigra = marcarVehiculoDAO.automotorMigraQx(placa);
        Integer validaAutomotorMigrarunt = marcarVehiculoDAO.migraAutomotor(placa);
        if (validaAutomotorMigra == 0 && validaAutomotorMigrarunt == 0) {
            salida.setCodmensaje(Mensajes.ADVERTENCIA);
            salida.setMensaje("El vehículo ingresado ya fue eliminado de las tablas de migración");
            return salida;
        }
        String validaRnrysRnma = commonLogica.validaRnrysRnma(placa);
        if (validaRnrysRnma != null) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje(validaRnrysRnma);
            return salida;
        }

        List<TblLicTto> consultaVehiculo = constanteDAO.consultaLicTto(placa);
        if (consultaVehiculo != null && !consultaVehiculo.isEmpty()) {
            for (TblLicTto tto : consultaVehiculo) {
                TblLicTto cast = casteador.castTblLicTto(tto, observacion);
                boolean registraLogLicTto = marcarVehiculoDAO.registraLogMarcaLicTto(cast);
                if (registraLogLicTto) {
                    almacenados.eliminaMigraRna(placa);
                    almacenados.eliminaFinalRnaMigra(placa);
                } else {
                    salida.setCodmensaje(Mensajes.ERROR);
                    mensaje = ("Error al registrar en la tabla LICTTO.");
                    salida.setMensaje(mensaje);
                }
            }
            HiAutomotor registroLog
                    = casteador.registrarLog(placa, null, Constantes.CAMPO_PLACA_VEHICULO, usuario.getLogin(), placa, nroTicket);
            boolean registraLog = constanteDAO.registrarLog(registroLog);
            if (registraLog) {
                salida.setCodmensaje(Mensajes.OK);
                mensaje = ("Se elimino el registro de la base de datos de forma exitosa.");
                salida.setMensaje(mensaje);
            } else {
                salida.setCodmensaje(Mensajes.ERROR);
                mensaje = ("Error al registrar el Log del proceso.");
                salida.setMensaje(mensaje);
            }
        } else {
            salida.setCodmensaje(Mensajes.ERROR);
            mensaje = ("El vehículo ingresado no se encuentra registrado en la tabla LICTTO.");
            salida.setMensaje(mensaje);
        }
        return salida;
    }

}
