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
import co.com.runt.sagir.dto.ConsultaFormatoPlacaDTO;
import co.com.runt.sagir.dto.InfoUsuarioDTO;
import co.com.runt.sagir.dto.LicTtoDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.entities.Automotor;
import co.com.runt.sagir.entities.HiAutomotor;
import co.com.runt.sagir.entities.LicTto;
import co.com.runt.sagir.entities.MgActualizacionRna;
import co.com.runt.sagir.utils.TransformacionDozer;
import java.util.ArrayList;
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
public class CambioFormatoPlacaLogica {

    @Resource
    protected EJBContext context;

    @EJB
    private ConstanteDAO constanteDAO;

    @EJB
    private PlSqlDAO almacenados;

    @EJB
    private Casteador casteador;

    @EJB
    private CambioFormatoPlacaNuevoDAO formatoNuevoDAO;

    @EJB
    private CommonLogica commonLogica;

    private static final Logger LOG = Logger.getLogger(CambioFormatoPlacaLogica.class.getName());

    public MensajeDTO cambioAntiguo(final String placa, final String nroTicket, final InfoUsuarioDTO info) {
        String mensaje;
        MensajeDTO salida = new MensajeDTO();
        String usuario = info.getLogin();
        Integer validaVehiculoLicTtoAnterior = constanteDAO.countLicTto(placa);
        Integer validaFormatoPlaca = almacenados.functionValidaFormatoPlaca(placa);
        String nuevoFormato = almacenados.functionCambioPlaca(placa, validaFormatoPlaca);
        Integer validaVehiculoLicTtoNueva = constanteDAO.countLicTto(nuevoFormato);
        if (validaVehiculoLicTtoAnterior == 0 || validaVehiculoLicTtoNueva == 0) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("El número de placa ingresado no se encuentra en la base de datos de migración");
            return salida;
        }
        String validaRnrysRnma = commonLogica.validaRnrysRnma(placa);
        if (validaRnrysRnma != null) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje(validaRnrysRnma);
            return salida;
        }
        AutomotorDTO automotor;
        Automotor consultaVehiculo = constanteDAO.consultaVehiculo(placa);
        if (consultaVehiculo == null) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("El vehículo no se encuentra registrado en el RNA");
            return salida;
        }
        //Valida que el estado del vehículo sea ACTIVO en el RNA
        if (!consultaVehiculo.getAutomotorEstavehicNombre().equals(Constantes.ESTADO_VEHICULO)) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("El estado del vehículo ingresado es diferente a ACTIVO.");
            return salida;
        }
        //Valida ei el vehículo es clase camión o tracto camión
        Integer validaClase = constanteDAO.validaCamionTracto(placa);
        //Si el vehículo no es clase camión o tracto camión continua con el proceso
        if (validaClase != 0) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("El vehículo es de carga.");
            return salida;
        }
        String nombreCampo = Constantes.CAMPO_PLACA_VEHICULO;
        automotor = TransformacionDozer.transformar(consultaVehiculo, AutomotorDTO.class);
        Long nroRegistroAutomotor = automotor.getAutomotorNroregveh();
        String resultado = almacenados.prMgActualizarPlaca24RNA(nroRegistroAutomotor, nroTicket);
        if (resultado.equals(Constantes.VALIDADOR)) {
            Automotor nuevaPlaca = constanteDAO.consultaVehiculoByNroRegistro(nroRegistroAutomotor);
            HiAutomotor registroLog
                    = casteador.registrarLog(placa, nuevaPlaca.getAutomotorPlacaNumplaca(), nombreCampo, usuario, placa, nroTicket);
            boolean registrarLog = constanteDAO.registrarLog(registroLog);
            if (registrarLog) {
                salida.setCodmensaje(Mensajes.OK);
                salida.setMensaje("Se proceso correctamente el cambio de formato de placa");
            } else {
                salida.setCodmensaje(Mensajes.ERROR);
                salida.setMensaje("Error al registrar el log");
            }
        } else {
            salida.setCodmensaje(Mensajes.ERROR);
            mensaje = resultado;
            salida.setMensaje(mensaje);
        }
        return salida;
    }

    public MensajeDTO cambioNuevoFormato(final String placa, final String nroTicket, final InfoUsuarioDTO info) {
        MensajeDTO salida = new MensajeDTO();
        String mensaje;
        String usuario = info.getLogin();
        Integer validaFormatoPlaca = almacenados.functionValidaFormatoPlaca(placa);
        String nuevoFormato = almacenados.functionCambioPlaca(placa, validaFormatoPlaca);
        Integer validaVehiculoLicTto = constanteDAO.countLicTto(placa);
        Integer validaVehiculoLicTtoNueva = constanteDAO.countLicTto(nuevoFormato);
        if (validaVehiculoLicTto == 0 || validaVehiculoLicTtoNueva == 0) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("El número de placa ingresado no se encuentra en la base de datos de migración");
            return salida;
        }
        String validaRnrysRnma = commonLogica.validaRnrysRnma(placa);
        if (validaRnrysRnma != null) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje(validaRnrysRnma);
            return salida;
        }
        String nombreCampo = Constantes.CAMPO_PLACA_VEHICULO;
        Integer tramites = constanteDAO.validaSolicitudPlaca(placa);
        if (tramites != 0) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("El vehículo ingresado posee trámites registrados en RUNT");
            return salida;
        }
        Integer postulacion = formatoNuevoDAO.verificaPostulacion(placa);
        if (postulacion != 0) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("El cargue se encuentra rechazado");
            return salida;
        }
        boolean estadoCague = formatoNuevoDAO.consultaPlacaCargada(placa);
        if (!estadoCague) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("El vehículo no fue migrado");
            return salida;
        }
        boolean estadoMigrado = formatoNuevoDAO.consultaVehiculoMigrado(placa);
        if (!estadoMigrado) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("El vehículo ingresado no fue migrado");
            return salida;
        }
        Integer validaFormatoa = almacenados.functionValidaFormatoPlaca(placa);
        if (validaFormatoa == 0) {
            salida.setCodmensaje(Mensajes.ERROR);
            salida.setMensaje("El formato de placa no es valido");
            return salida;
        }
        try {
            String nuevaPlaca = almacenados.functionCambioPlaca(placa, validaFormatoa);
            Integer validacion = constanteDAO.validaVehiculo(nuevaPlaca);
            if (validacion != 0) {
                salida.setCodmensaje(Mensajes.ERROR);
                salida.setMensaje("Error, el nuevo formato de placa ya se encuentra registrado para otro vehículo");
                return salida;
            }
            LicTto datos = formatoNuevoDAO.consultaVehiculo(placa);
            if (datos != null) {
                Automotor automotor = constanteDAO.consultaVehiculo(placa);
                //Valida que el estado del vehículo sea ACTIVO en el RNA
                if (!automotor.getAutomotorEstavehicNombre().equals(Constantes.ESTADO_VEHICULO)) {
                    salida.setCodmensaje(Mensajes.ERROR);
                    salida.setMensaje("El estado del vehículo es diferente a ACTIVO.");
                    return salida;
                }
                //Valida ei el vehículo es clase camión o tracto camión
                Integer validaClase = constanteDAO.validaCamionTracto(placa);
                //Si el vehículo no es clase camión o tracto camión continua con el proceso
                if (validaClase != 0) {
                    salida.setCodmensaje(Mensajes.ERROR);
                    salida.setMensaje("El vehículo ingresado es de carga.");
                }
                Integer estadoVehic = Integer.parseInt(datos.getEstado());
                String estadoActual = constanteDAO.estadoVehiculo(Integer.parseInt(datos.getEstado()));
                String estadoSolicita = constanteDAO.estadoVehiculo(estadoVehic);
                Integer tipoProceso = Constantes.TIPO_PROCESO_CAMBIO_FORMATO;
                MgActualizacionRna actualizacionRna
                        = casteador.castMgActualizacionRna(datos, automotor, estadoActual, estadoSolicita, tipoProceso, info.getLogin());
                HiAutomotor registroLog
                        = casteador.registrarLog(placa, nuevaPlaca, nombreCampo, usuario, placa, nroTicket);
                UserTransaction ut = context.getUserTransaction();
                ut.begin();
                boolean registro = formatoNuevoDAO.registraProceso(actualizacionRna);
                boolean registrarLog = constanteDAO.registrarLog(registroLog);
                if (registrarLog) {
                    if (registro) {
                        Integer codCarga = Integer.parseInt(datos.getCodCarga());
                        ut.commit();
                        almacenados.pActualizarEstadoRnaPlaca(placa, codCarga);
                        Integer validaCambio = constanteDAO.validaCambioFormatoPlaca(placa, tipoProceso);
                        if (validaCambio != 0) {
                            salida.setCodmensaje(Mensajes.OK);
                            mensaje = "Procesado el cambio de placa de forma exitosa";
                            salida.setMensaje(mensaje);
                        } else {
                            salida.setCodmensaje(Mensajes.ERROR);
                            mensaje = "Error al ejecutar el procedimiento almacenado";
                            salida.setMensaje(mensaje);
                        }
                    } else {
                        salida.setCodmensaje(Mensajes.ERROR);
                        mensaje = "Error al registrar la información";
                        salida.setMensaje(mensaje);
                        ut.rollback();
                    }
                } else {
                    salida.setCodmensaje(Mensajes.ERROR);
                    mensaje = "Error al registrar el Log";
                    salida.setMensaje(mensaje);
                }
            } else {
                LicTto datosNuevoFormato = formatoNuevoDAO.consultaVehiculo(nuevoFormato);
                if (datosNuevoFormato == null) {
                    salida.setCodmensaje(Mensajes.ERROR);
                    salida.setMensaje("Error al consultar la tabla LIC_TTO");
                    return salida;
                }
                Automotor automotor = constanteDAO.consultaVehiculo(placa);
                //Valida que el estado del vehículo sea ACTIVO en el RNA
                if (!automotor.getAutomotorEstavehicNombre().equals(Constantes.ESTADO_VEHICULO)) {
                    salida.setCodmensaje(Mensajes.ERROR);
                    salida.setMensaje("El estado del vehículo es diferente a ACTIVO.");
                    return salida;
                }
                //Valida ei el vehículo es clase camión o tracto camión
                Integer validaClase = constanteDAO.validaCamionTracto(placa);
                //Si el vehículo no es clase camión o tracto camión continua con el proceso
                if (validaClase == 0) {
                    salida.setCodmensaje(Mensajes.ERROR);
                    salida.setMensaje("El vehículo ingresado es de carga.");
                    return salida;
                }
                Integer estadoVehic = Integer.parseInt(datosNuevoFormato.getEstado());
                String estadoActual = constanteDAO.estadoVehiculo(Integer.parseInt(datosNuevoFormato.getEstado()));
                String estadoSolicita = constanteDAO.estadoVehiculo(estadoVehic);
                Integer tipoProceso = Constantes.TIPO_PROCESO_CAMBIO_FORMATO;
                MgActualizacionRna actualizacionRna
                        = casteador.castMgActualizacionRna(datosNuevoFormato, automotor, estadoActual, estadoSolicita, tipoProceso, info.getLogin());
                HiAutomotor registroLog
                        = casteador.registrarLog(placa, nuevaPlaca, nombreCampo, usuario, placa, nroTicket);
                UserTransaction ut = context.getUserTransaction();
                ut.begin();
                boolean registro = formatoNuevoDAO.registraProceso(actualizacionRna);
                boolean registrarLog = constanteDAO.registrarLog(registroLog);
                if (registrarLog) {
                    if (registro) {
                        Integer codCarga = Integer.parseInt(datosNuevoFormato.getCodCarga());
                        ut.commit();
                        almacenados.pActualizarEstadoRnaPlaca(placa, codCarga);
                        Integer validaCambio = constanteDAO.validaCambioFormatoPlaca(placa, tipoProceso);
                        if (validaCambio != 0) {
                            salida.setCodmensaje(Mensajes.OK);
                            mensaje = "Procesado el cambio de placa de forma exitosa";
                            salida.setMensaje(mensaje);
                        } else {
                            salida.setCodmensaje(Mensajes.ERROR);
                            mensaje = "Error al ejecutar el procedimiento almacenado";
                            salida.setMensaje(mensaje);
                        }
                    } else {
                        salida.setCodmensaje(Mensajes.ERROR);
                        mensaje = "Error al registrar la información";
                        salida.setMensaje(mensaje);
                        ut.rollback();
                    }
                } else {
                    salida.setCodmensaje(Mensajes.ERROR);
                    mensaje = "Error al registrar el Log";
                    salida.setMensaje(mensaje);
                    ut.rollback();
                }

            }
        } catch (IllegalStateException | NumberFormatException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
            LOG.log(Level.SEVERE, e.getMessage());
        }
        return salida;
    }

    public MensajeDTO consultaCambioFormato(final String placa) {
        LOG.log(Level.INFO, "=============Iniciando la consulta=========");
        MensajeDTO salida = new MensajeDTO();
        String mensaje;
        String validaRnrysRnma = commonLogica.validaRnrysRnma(placa);
        if (validaRnrysRnma == null) {
            String placaAnterior = consultaNuevo(placa);
            List<ConsultaFormatoPlacaDTO> listConsulta = new ArrayList<>();
            List<LicTto> list = formatoNuevoDAO.consultaCambioFormatoAntigANuevo(placa);
            if (list != null && !list.isEmpty()) {
                for (LicTto lt : list) {
                    ConsultaFormatoPlacaDTO listDTO = new ConsultaFormatoPlacaDTO();
                    listDTO.setPlacaProduccion(placaAnterior);
                    listDTO.setPlacaMigrada(lt.getPlaca());
                    listDTO.setIdSecretaria(lt.getSecretaria());
                    listDTO.setEstadoCargue(lt.getEstadoCargue());
                    listDTO.setDescModalidad(lt.getDescModalidad());
                    listConsulta.add(listDTO);
                    salida.setCodmensaje(Mensajes.OK);
                    salida.setObject(listConsulta);
                }
            } else {
                Integer validaFormatoPlaca = almacenados.functionValidaFormatoPlaca(placa);
                String nuevoFormato = almacenados.functionCambioPlaca(placa, validaFormatoPlaca);
                List<LicTto> listNuevoFormato = formatoNuevoDAO.consultaCambioFormatoAntigANuevo(nuevoFormato);
                if (listNuevoFormato != null && !listNuevoFormato.isEmpty()) {
                    for (LicTto lt : listNuevoFormato) {
                        ConsultaFormatoPlacaDTO listDTO = new ConsultaFormatoPlacaDTO();
                        listDTO.setPlacaProduccion(placaAnterior);
                        listDTO.setPlacaMigrada(lt.getPlaca());
                        listDTO.setIdSecretaria(lt.getSecretaria());
                        listDTO.setEstadoCargue(lt.getEstadoCargue());
                        listDTO.setDescModalidad(lt.getDescModalidad());
                        listConsulta.add(listDTO);
                        salida.setCodmensaje(Mensajes.OK);
                        salida.setObject(listConsulta);
                    }
                } else {
                    mensaje = "El número de placa ingresado no existe en la base de datos de Migración.";
                    salida.setCodmensaje(Mensajes.ERROR);
                    salida.setMensaje(mensaje);
                }
            }
        } else {
            salida.setCodmensaje(Mensajes.ERROR);
            mensaje = validaRnrysRnma;
            salida.setMensaje(mensaje);
        }
        return salida;
    }

    public MensajeDTO consultaCambioFormatoDetalle(final String placa, final String boletin) {
        LOG.log(Level.INFO, "=============Iniciando la consulta=========");
        MensajeDTO salida = new MensajeDTO();
        String mensaje;
        try {
            List<LicTtoDTO> listDTO;
            List<LicTto> list = formatoNuevoDAO.consultaCambioFormatoDetalle(placa, boletin);
            if (list != null && !list.isEmpty()) {
                listDTO = TransformacionDozer.transformar(list, LicTtoDTO.class);
                salida.setCodmensaje(Mensajes.OK);
                salida.setObject(listDTO);
            } else {
                mensaje = "El vehículo ingresado no fue migrado";
                salida.setCodmensaje(Mensajes.ERROR);
                salida.setMensaje(mensaje);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
        }
        return salida;
    }

    public String consultaNuevo(final String placa) {
        LOG.log(Level.INFO, "=============Iniciando la consulta=========");
        String salida;
        Integer validaFormatoPlaca = almacenados.functionValidaFormatoPlaca(placa);
        if (validaFormatoPlaca == 0) {
            LOG.log(Level.SEVERE, "Error al validar la placa");
        }
        String nuevaPlaca = almacenados.functionCambioPlaca(placa, validaFormatoPlaca);
        if (nuevaPlaca == null) {
            LOG.log(Level.SEVERE, "Error al validar la nueva placa");
        }
        String validaRnrysRnma = commonLogica.validaRnrysRnma(nuevaPlaca);
        if (validaRnrysRnma != null) {
            LOG.log(Level.SEVERE, "El vehículo pertenece al registro de RNRYS");
        }
        Automotor automotor = constanteDAO.consultaVehiculo(nuevaPlaca);
        if (automotor != null) {
            salida = automotor.getAutomotorPlacaNumplaca();
        } else {
            Automotor automotorAnterior = constanteDAO.consultaVehiculo(placa);
            if (automotorAnterior != null) {
                salida = automotorAnterior.getAutomotorPlacaNumplaca();
            } else {
                String nuevoFormato = almacenados.functionCambioPlaca(placa, 2);
                Automotor automotorNuevoFormato = constanteDAO.consultaVehiculo(nuevoFormato);
                if (automotorNuevoFormato != null) {
                    salida = automotorNuevoFormato.getAutomotorPlacaNumplaca();
                } else {
                    salida = "Error al consultar la placa en producción";
                }
            }
        }
        return salida;
    }
}
