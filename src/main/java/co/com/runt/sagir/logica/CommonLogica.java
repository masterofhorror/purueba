/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.logica;

import co.com.runt.sagir.common.Mensajes;
import co.com.runt.sagir.dao.ConstanteDAO;
import co.com.runt.sagir.dao.PlSqlDAO;
import co.com.runt.sagir.dto.InfoUsuarioDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.dto.OrganismosTransitoMigruntDTO;
import co.com.runt.sagir.dto.TipoServicioDTO;
import co.com.runt.sagir.entities.Automotor;
import co.com.runt.sagir.entities.MgActualizacionRna;
import co.com.runt.sagir.entities.OrganismosTransitoMigrunt;
import co.com.runt.sagir.entities.TblLicTto;
import co.com.runt.sagir.entities.TipoServicio;
import co.com.runt.sagir.utils.TransformacionDozer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author dsalamanca
 */
@Stateless
public class CommonLogica {

    @EJB
    private ConstanteDAO constanteDAO;

    @EJB
    private PlSqlDAO almacenados;

    /**
     * Metodo que enlista los Organismos de Tránsito de la tabla
     * MIGRUNT1.ORGANISMOS_TRANSITO
     *
     * @return
     */
    public MensajeDTO listarOt() {
        MensajeDTO salida = new MensajeDTO();
        String mensaje;
        List<OrganismosTransitoMigruntDTO> listDTO = new ArrayList<>();
        List<OrganismosTransitoMigrunt> consulta = constanteDAO.listarOt();
        if (consulta != null && !consulta.isEmpty()) {
            listDTO = TransformacionDozer.transformar(consulta, OrganismosTransitoMigruntDTO.class);
            salida.setCodmensaje(Mensajes.OK);
            salida.setObject(listDTO);
        } else {
            salida.setCodmensaje(Mensajes.ERROR);
            mensaje = ("Error al consultar los Organismos de Tránsito");
            salida.setMensaje(mensaje);
        }
        return salida;
    }

    /**
     * Metodo que consulta en la tabla LICTTO por número de placa
     *
     * @param placa
     * @return
     */
    public MensajeDTO consultaLicTtoPlaca(final String placa) {
        MensajeDTO salida = new MensajeDTO();
        String mensaje;
        List<TblLicTto> listDTO = new ArrayList<>();
        List<TblLicTto> consultaVehiculo = constanteDAO.consultaLicTto(placa);
        if (consultaVehiculo != null && !consultaVehiculo.isEmpty()) {
            listDTO = TransformacionDozer.transformar(consultaVehiculo, TblLicTto.class);
            salida.setCodmensaje(Mensajes.OK);
            salida.setObject(listDTO);
        } else {
            salida.setCodmensaje(Mensajes.ERROR);
            mensaje = ("El número de placa no se encuentra registrado en la base de datos de Migración");
            salida.setMensaje(mensaje);
        }
        return salida;
    }

    public boolean registrarMgActualizacionRNA(final String placa, final Integer codCarga, final String idSecretaria, final InfoUsuarioDTO infoUsuarioDTO) {
        String observacion;
        Integer validaLicTto = constanteDAO.countLicTto(placa);
        if (validaLicTto != 0) {
            TblLicTto consulta = constanteDAO.consultaPlacaCodCargaIdSecretaria(placa, codCarga, codCarga);
            if (consulta != null) {
                Automotor consultaAutomotor = constanteDAO.consultaVehiculo(placa);
                if (consultaAutomotor != null) {
                    observacion = ("Vehículo actualizado");
                    Long idVehiculo = consultaAutomotor.getAutomotorNroregveh();
                    String idEstadoVehic = consulta.getEstadoVehiculo();
                    String estado = converEstadosVehic(Integer.parseInt(idEstadoVehic));
                    castRegistroMgActualizacion(placa, idVehiculo, observacion, infoUsuarioDTO, estado);
                    return true;
                } else {
                    observacion = ("El vehículo no existe en la base de datos de producción");
                    Integer validaFormatoPlaca = almacenados.functionValidaFormatoPlaca(placa);
                    if (validaFormatoPlaca != 0) {
                        String nuevaPlaca = almacenados.functionCambioPlaca(placa, validaFormatoPlaca);
                        if (nuevaPlaca != null) {
                            Automotor consultaNuevaPlaca = constanteDAO.consultaVehiculo(nuevaPlaca);
                            Long idVehiculo = consultaNuevaPlaca.getAutomotorNroregveh();
                            String idEstadoVehic = consulta.getEstadoVehiculo();
                            String estado = converEstadosVehic(Integer.parseInt(idEstadoVehic));
                            castRegistroMgActualizacion(placa, idVehiculo, observacion, infoUsuarioDTO, estado);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public MgActualizacionRna castRegistroMgActualizacion(final String placa, final Long idVehiculo, final String observacion, final InfoUsuarioDTO infoUsuarioDTO, final String estado) {
        MgActualizacionRna actualizacionRna = new MgActualizacionRna();
        actualizacionRna.setNroRegistroVehiculo(idVehiculo);
        actualizacionRna.setPlaca(placa);
        actualizacionRna.setOtCargo(0);
        actualizacionRna.setOtSolicita(0);
        actualizacionRna.setEstadoVehiculoCargado(estado);
        actualizacionRna.setEstadoVehiculoSolicita("No aplica");
        actualizacionRna.setIdClaseCargado(0);
        actualizacionRna.setIdClaseSolicita(0);
        actualizacionRna.setIdMarcaCargado(0);
        actualizacionRna.setIdMarcaSolicita(0);
        actualizacionRna.setModeloCargado(0);
        actualizacionRna.setModeloSolicita(0);
        actualizacionRna.setTipoProceso(6);
        actualizacionRna.setEstadoProceso(0);
        actualizacionRna.setFechaProceso(new Date());
        actualizacionRna.setDescripcionEstado(observacion);
        actualizacionRna.setIdFuncionario(infoUsuarioDTO.getLogin());
        return null;
    }

    public String converEstadosVehic(final Integer idEstado) {
        String estado = null;
        if (idEstado == 1) {
            estado = ("ACTIVO");
        } else {
            if (idEstado == 2) {
                estado = ("CANCELADO");
            } else {
                if (idEstado == 3) {
                    estado = ("TRASLADO");
                }
            }
        }
        return estado;
    }

    /**
     * Metodo que enlista la tabla RUNTPROD.PA_TIPOSERVI
     *
     * @return
     */
    public MensajeDTO tipoServicio() {
        MensajeDTO salida = new MensajeDTO();
        String mensaje;
        List<TipoServicioDTO> listDTO = new ArrayList<>();
        List<TipoServicio> consultaTipoServicio = constanteDAO.tipoServicio();
        if (consultaTipoServicio != null && !consultaTipoServicio.isEmpty()) {
            listDTO = TransformacionDozer.transformar(consultaTipoServicio, TipoServicioDTO.class);
            salida.setCodmensaje(Mensajes.OK);
            salida.setObject(listDTO);
        } else {
            salida.setCodmensaje(Mensajes.ERROR);
            mensaje = "Error al consultar los tipos de servicio";
            salida.setMensaje(mensaje);
        }
        return salida;
    }

    public String validaRnrysRnma(final String placa) {
        String mensaje = null;
        Integer validaRnrys = constanteDAO.validaRnrys(placa);
        if (validaRnrys != 0) {
            mensaje = ("El número de placa ingresado corresponde al registro de RNRYS");
        }
        if (mensaje == null) {
            Integer validaRnma = constanteDAO.validaRnma(placa);
            if (validaRnma != 0) {
                mensaje = ("El número de placa ingresado corresponde al registro de RNMA");
            }
        }
        return mensaje;
    }
}
