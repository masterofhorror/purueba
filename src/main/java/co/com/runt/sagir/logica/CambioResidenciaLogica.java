/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.logica;

import co.com.runt.sagir.common.Casteador;
import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.common.Mensajes;
import co.com.runt.sagir.dao.ConstanteDAO;
import co.com.runt.sagir.dao.PlSqlDAO;
import co.com.runt.sagir.dto.InfoUsuarioDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.entities.Automotor;
import co.com.runt.sagir.entities.HiAutomotor;
import co.com.runt.sagir.entities.MigraActualizacionRna;
import co.com.runt.sagir.entities.TblLicTto;
import java.util.Date;
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
public class CambioResidenciaLogica {

    @Resource
    protected EJBContext context;

    @EJB
    private PlSqlDAO almacenados;

    @EJB
    private Casteador casteador;

    @EJB
    private ConstanteDAO constanteDAO;

    @EJB
    private CommonLogica commonLogica;
    
    private static final Logger LOG = Logger.getLogger(CambioFormatoPlacaLogica.class.getName());
    
    public MensajeDTO cambioResidencia(final String placa, final String nroTicket, final InfoUsuarioDTO infoUsuario, final String ip) {
        MensajeDTO salida = new MensajeDTO();
        String mensaje;
        String usuario = infoUsuario.getLogin();
        String validaRnrysRnma = commonLogica.validaRnrysRnma(placa);
        if (validaRnrysRnma == null) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje(validaRnrysRnma);
            return salida;
        }
        Integer validaMigracion = constanteDAO.countLicTto(placa);
        if (validaMigracion == 0) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("El número de placa ingresado no se encuentra en la base de datos de Migración.");
            return salida;
        }
        Automotor automotor = constanteDAO.consultaVehiculo(placa);
        if (automotor == null) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("El número de placa ingresado no se encuentra cargado en el RUNT");
            return salida;
        }
        Integer validaClase = constanteDAO.validaCamionTracto(placa);
        //Si el vehículo no es clase camión o tracto camión continua con el proceso
        if (validaClase != 0) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("El vehículo ingresado es de carga.");
            return salida;
        }
        String estadoVehiculo = automotor.getAutomotorEstavehicNombre();
        if (!Constantes.ESTADO_CAMBIO_RESIDENCIA.equals(estadoVehiculo)) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("El estado del vehículo es diferente a INCONSISTENTE.");
            return salida;
        }
        TblLicTto consultaLicTto = constanteDAO.consultaVehiculoLicTto(placa);
        if (consultaLicTto == null) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("El vehículo ingresado no registra un código de carga aprobado.");
            return salida;
        }
        Integer codCarga = consultaLicTto.getLicTtoPK().getCodCarga();
        Integer validaProceso = constanteDAO.consultaActualizacionRna(placa, codCarga);
        if (validaProceso != 0) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("El vehículo ya tiene un registro de cambio de residencia.");
            return salida;
        }
        MigraActualizacionRna mar = new MigraActualizacionRna();
        mar.setCodCarga(codCarga);
        mar.setPlaca(placa);
        mar.setEstadoProceso(Constantes.ESTADO_PROCESO_CAMBIO_RES);
        mar.setFechaProceso(new Date());
        mar.setUsuario(0);//Usuario
        try {
            UserTransaction ut = context.getUserTransaction();
            ut.begin();
            boolean registraProceso = constanteDAO.registraProceso(mar);
            if (registraProceso) {
                ut.commit();
                almacenados.actualizaResidenciaPlaca(codCarga, placa);
                almacenados.actPropietarCambResPlaca(codCarga, placa);
                TblLicTto nuevoAutomotor = constanteDAO.consultaVehiculoLicTto(placa);
                String secretariaSolicita = nuevoAutomotor.getLicTtoPK().getIdSecretaria().toString();
                String registro = placa;
                HiAutomotor registroLog
                        = casteador.registrarLog(automotor.getAutomotorAutotransIdauttra().toString(), secretariaSolicita, Constantes.CAMPO_RESIDENCIA_VEHICULO, usuario, registro, nroTicket);
                boolean registraLog = constanteDAO.registrarLog(registroLog);
                if (registraLog) {
                    salida.setCodmensaje(Mensajes.OK);
                    mensaje = "Proceso de actualizacion ejecutado de forma exitosa.";
                    salida.setMensaje(mensaje);
                } else {
                    salida.setCodmensaje(Mensajes.ERROR);
                    mensaje = "Error al registrar el Log.";
                    salida.setMensaje(mensaje);
                    ut.rollback();
                }
            } else {
                salida.setCodmensaje(Mensajes.ERROR);
                mensaje = "Error al registrar el proceso.";
                salida.setMensaje(mensaje);
                ut.rollback();
            }
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
        }

        return salida;
    }
}
