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
import java.util.Date;
import java.util.Objects;
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
public class CambioServicioLogica {

    @Resource
    protected EJBContext context;

    @EJB
    private ConstanteDAO constanteDAO;

    @EJB
    private PlSqlDAO almacenados;

    @EJB
    private CambioFormatoPlacaNuevoDAO validaRegistroDAO;

    @EJB
    private Casteador casteador;

    @EJB
    private CommonLogica commonLogica;

    private static final Logger LOG = Logger.getLogger(CambioFormatoPlacaLogica.class.getName());

    public MensajeDTO cambioServicio(final String placa, final Integer idTipoServicio, final String nroTicket, final InfoUsuarioDTO usuario) {
        MensajeDTO salida = new MensajeDTO();
        String mensaje;
        //Valida que el vehículo exista en la base de datos de migración
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
        String estadoVehiculoAutomotor = validaAutomotor.getAutomotorEstavehicNombre();
        //Valida que el vehículo se encuentre en estado activo
        if (!estadoVehiculoAutomotor.equals(Constantes.ESTADO_VEHICULO)) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("El vehículo ingresado se encuentra en estado diferente a ACTIVO.");
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
        Integer tipoRegistro = Constantes.TIPO_PROCESO_CAMBIO_SERVICIO;
        Integer validaRegistro = constanteDAO.validaRegistroProceso(placa, tipoRegistro);
        //Valida si el vehículo registra un proceso de actualización.
        if (validaRegistro != 0) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("El número de placa ya registra una solicitud de cambio de servicio");
            return salida;
        }
        Integer idTipoServicioAutomotor = validaAutomotor.getTipoServicio().getIdeTipSer();
        if (Objects.equals(idTipoServicioAutomotor, idTipoServicio)) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("El tipo de servicio seleccionado es el mismo que poseé registrado el vehículo ingresado.");
            return salida;
        }
        TblLicTto consultaLicTto = constanteDAO.consultaVehiculoLicTto(placa);
        if (consultaLicTto != null) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("El vehículo ingresado no registra un código de carga en estado aprobado");
            return salida;
        }
        Integer codCarga = consultaLicTto.getLicTtoPK().getCodCarga();
        LicTto datos = validaRegistroDAO.consultaVehiculo(placa);
        Automotor automotor = constanteDAO.consultaVehiculo(placa);
        Integer tipoProceso = Constantes.TIPO_PROCESO_CAMBIO_SERVICIO;
        Integer servicioActual = automotor.getTipoServicio().getIdeTipSer();
        MgActualizacionRna actualizacionRna
                = castMgActualizacionRna(datos, automotor, datos.getEstado(), servicioActual, idTipoServicio, tipoProceso, usuario.getLogin());
        HiAutomotor registroLog
                = casteador.registrarLog(servicioActual.toString(), idTipoServicio.toString(), Constantes.CAMPO_SERVICIO_VEHICULO, usuario.getLogin(), placa, nroTicket);
        try {
            UserTransaction ut = context.getUserTransaction();
            ut.begin();
            boolean registroProceso = validaRegistroDAO.registraProceso(actualizacionRna);
            boolean registraLog = constanteDAO.registrarLog(registroLog);
            ut.commit();
            if (!registraLog) {
                salida.setCodmensaje(Mensajes.ERROR);
                salida.setMensaje("Error al registrar el Log");
                ut.rollback();
            }
            if (registroProceso) {
                salida.setCodmensaje(Mensajes.ERROR);
                salida.setMensaje("Error al registrar el Log");
                ut.rollback();
            }
            ut.begin();
            almacenados.pActualizarEstadoRnaPlaca(placa, codCarga);
            Automotor consultaVehiculo = constanteDAO.consultaVehiculo(placa);
            ut.commit();
            if (consultaVehiculo != null) {
                Integer validaCambio = consultaVehiculo.getTipoServicio().getIdeTipSer();
                if (Objects.equals(validaCambio, idTipoServicio)) {
                    salida.setCodmensaje(Mensajes.OK);
                    mensaje = "Se proceso el cambio de servicio de forma exitosa";
                    salida.setMensaje(mensaje);

                } else {
                    salida.setCodmensaje(Mensajes.ERROR);
                    mensaje = "Error al registrar el proceso";
                    salida.setMensaje(mensaje);
                }
            }
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return salida;
    }

    public MensajeDTO consultaCambioServicio(final String placa) {
        MensajeDTO salida = new MensajeDTO();
        String mensaje;
        String validaRnrysRnma = commonLogica.validaRnrysRnma(placa);
        if (validaRnrysRnma == null) {
            Automotor automotor = constanteDAO.consultaVehiculo(placa);
            AutomotorDTO automotorDTO;
            if (automotor != null) {
                salida.setCodmensaje(Mensajes.OK);
                automotorDTO = TransformacionDozer.transformar(automotor, AutomotorDTO.class);
                salida.setObject(automotorDTO);
            } else {
                salida.setCodmensaje(Mensajes.ERROR);
                salida.setMensaje("No se encontro información del vehículo ingresado");
            }
        } else {
            salida.setCodmensaje(Mensajes.ERROR);
            mensaje = validaRnrysRnma;
            salida.setMensaje(mensaje);
        }
        return salida;
    }

    public MgActualizacionRna castMgActualizacionRna(LicTto datos, Automotor automotor, final String estadoVehiculo, final Integer servicioActual, final Integer servicioSolicita, final Integer tipoProceso, final String idUsuario) {
        MgActualizacionRna actualizacionRna = new MgActualizacionRna();
        actualizacionRna.setCodCarga(Integer.parseInt(datos.getCodCarga()));
        actualizacionRna.setDescripcionEstado(null);
        actualizacionRna.setEstadoProceso(0);
        actualizacionRna.setEstadoVehiculoCargado(estadoVehiculo);
        actualizacionRna.setEstadoVehiculoSolicita(estadoVehiculo);
        actualizacionRna.setFechaProceso(new Date());
        actualizacionRna.setIdClaseCargado(0);
        actualizacionRna.setIdClaseSolicita(0);
        actualizacionRna.setIdFuncionario(idUsuario);
        actualizacionRna.setIdMarcaCargado(0);
        actualizacionRna.setIdMarcaSolicita(0);
        actualizacionRna.setIdServicioAnterior(servicioActual);
        actualizacionRna.setIdServicioNuevo(servicioSolicita);
        actualizacionRna.setModeloCargado(0);
        actualizacionRna.setModeloSolicita(0);
        actualizacionRna.setNroChasisCargado(null);
        actualizacionRna.setNroChasisSolicita(null);
        actualizacionRna.setNroMotorCargado(null);
        actualizacionRna.setNroMotorSolicita(null);
        actualizacionRna.setNroRegistroVehiculo(automotor.getAutomotorNroregveh());
        actualizacionRna.setNroSerieCargado(null);
        actualizacionRna.setNroSerieSolicita(null);
        actualizacionRna.setOtCargo(Integer.parseInt(datos.getIdSecretaria()));
        actualizacionRna.setOtSolicita(Integer.parseInt(datos.getIdSecretaria()));
        actualizacionRna.setPlaca(automotor.getAutomotorPlacaNumplaca());
        actualizacionRna.setTipoProceso(tipoProceso);
        return actualizacionRna;
    }

}
