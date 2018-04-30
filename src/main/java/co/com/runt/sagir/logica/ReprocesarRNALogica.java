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
import co.com.runt.sagir.dao.PlSqlDAO;
import co.com.runt.sagir.dao.ReprocesarRnaDAO;
import co.com.runt.sagir.dto.InfoUsuarioDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.dto.MgActualizacionRnaDTO;
import co.com.runt.sagir.dto.MigraActualizacionRnaDTO;
import co.com.runt.sagir.dto.ReprocesarRnaDTO;
import co.com.runt.sagir.entities.MigraActualizacionRna;
import co.com.runt.sagir.entities.ReprocesarRna;
import co.com.runt.sagir.utils.TransformacionDozer;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.UserTransaction;

/**
 *
 * @author dsalamanca
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ReprocesarRNALogica {

    @EJB
    private ReprocesarRnaDAO reprocesarRnaDAO;

    @EJB
    private LogsConsultasLogica guardarLogLogica;

    @EJB
    private ConstanteDAO constanteDAO;

    @EJB
    private PlSqlDAO almacenados;

    @Resource
    protected EJBContext context;

    /**
     * Logica que consulta en la tabla MIGRACIONQX.PROPIETARIOS_VEHICULO
     *
     * @param idCarga
     * @return
     */
    public MensajeDTO consultaReprocesoRNA(final Integer idCarga, InfoUsuarioDTO infoUsuario, String ip) {
        MensajeDTO salida = new MensajeDTO();
        String mensaje;
        String tipoConsulta;
        List<ReprocesarRnaDTO> listDTO = new ArrayList<>();
        List<ReprocesarRna> consulta = reprocesarRnaDAO.consultaReprocesar(idCarga);
        if (consulta != null && !consulta.isEmpty()) {
            salida.setCodmensaje(Mensajes.OK);
            listDTO = TransformacionDozer.transformar(consulta, ReprocesarRnaDTO.class);
            salida.setObject(listDTO);
            //Guardar Log de la consulta
            JsonObject json = new JsonObject();
            json.addProperty("idCarga", idCarga);
            tipoConsulta = Constantes.TIPO_CONSULTA_REPROCESAR_RNA;
            Long idauttra = infoUsuario.getIdOrganizacion();
            String datoUsuario = infoUsuario.getLogin();
            LogConsultaDTO log = guardarLogLogica.casteadorLogRNA(json, idauttra, datoUsuario, ip, tipoConsulta);
            guardarLogLogica.guardarLog(log);
        } else {
            salida.setCodmensaje(Mensajes.ERROR);
            mensaje = ("No se encuentran cargues para reprocesar");
            salida.setMensaje(mensaje);
        }
        return salida;
    }

    /**
     * Metodo que reprocesa toda la carga
     *
     * @param idCarga
     * @param infoUsuario
     * @param ip
     * @return
     */
    public MensajeDTO procesarTodo(final Integer idCarga, InfoUsuarioDTO infoUsuario, String ip) {
        MensajeDTO salida = new MensajeDTO();
        String mensaje;
        List<ReprocesarRna> consulta = reprocesarRnaDAO.consultaReprocesar(idCarga);
        if (consulta != null && !consulta.isEmpty()) {
            for (ReprocesarRna rna : consulta) {
                Integer validaRegistro = constanteDAO.consultaActualizacionRna(rna.getPlaca(), idCarga);
                if (validaRegistro == 0) {
                    MigraActualizacionRna cast = new MigraActualizacionRna();
                    cast.setCodCarga(idCarga);
                    cast.setEstadoProceso(Constantes.ESTADO_REPROCESO_P);
                    cast.setFechaProceso(new Date());
                    cast.setUsuario(Integer.parseInt(infoUsuario.getLogin()));
                    cast.setPlaca(rna.getPlaca());
                    try {
                        UserTransaction ut = context.getUserTransaction();
                        ut.begin();
                        boolean actualizarProceso = constanteDAO.actualizaProceso(cast);
                        if (actualizarProceso) {
                            ut.commit();
                            almacenados.actualizaResidenciaPlaca(idCarga, rna.getPlaca());
                            almacenados.actPropietarCambResPlaca(idCarga, rna.getPlaca());
                            salida.setCodmensaje(Mensajes.OK);
                            mensaje = ("Se reproceso correctamente la información");
                            salida.setMensaje(mensaje);
                        } else {
                            salida.setCodmensaje(Mensajes.ERROR);
                            mensaje = ("Error al registrar el proceso.");
                            salida.setMensaje(mensaje);
                            ut.rollback();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    MigraActualizacionRna consultaMigra = constanteDAO.consultaDetalleActualizacionRna(rna.getPlaca(), idCarga);
                    if (consultaMigra != null) {
                        MigraActualizacionRna cast = new MigraActualizacionRna();
                        cast.setCodCarga(idCarga);
                        cast.setEstadoProceso(consultaMigra.getEstadoProceso());
                        cast.setFechaProceso(new Date());
                        cast.setUsuario(Integer.parseInt(infoUsuario.getLogin()));
                        cast.setPlaca(rna.getPlaca());
                        try {
                            UserTransaction ut = context.getUserTransaction();
                            ut.begin();
                            boolean actualizarProceso = constanteDAO.actualizaProceso(cast);
                            if (actualizarProceso) {
                                ut.commit();
                                almacenados.actualizaResidenciaPlaca(idCarga, rna.getPlaca());
                                almacenados.actPropietarCambResPlaca(idCarga, rna.getPlaca());
                                salida.setCodmensaje(Mensajes.OK);
                                mensaje = ("Se reproceso correctamente la información");
                                salida.setMensaje(mensaje);
                            } else {
                                salida.setCodmensaje(Mensajes.ERROR);
                                mensaje = ("Error al registrar el proceso.");
                                salida.setMensaje(mensaje);
                                ut.rollback();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return salida;
    }

    /**
     * Metodo que reprocesa por placa
     *
     * @param idCarga
     * @param infoUsuario
     * @param ip
     * @return
     */
    public MensajeDTO procesar(final Integer idCarga, final String placa, InfoUsuarioDTO infoUsuario, String ip) {
        MensajeDTO salida = new MensajeDTO();
        String mensaje;
        Integer validaRegistro = constanteDAO.consultaActualizacionRna(placa, idCarga);
        //Se valida si para el código de carga existen registros
        if (validaRegistro == 0) {
            //Sino existen registros se insertan
            MigraActualizacionRna cast = new MigraActualizacionRna();
            cast.setCodCarga(idCarga);
            cast.setEstadoProceso(Constantes.ESTADO_REPROCESO_P);
            cast.setFechaProceso(new Date());
            cast.setUsuario(Integer.parseInt(infoUsuario.getLogin()));
            cast.setPlaca(placa);
            try {
                UserTransaction ut = context.getUserTransaction();
                ut.begin();
                boolean registraProceso = constanteDAO.registraProceso(cast);
                if (registraProceso) {
                    ut.commit();
                    almacenados.actualizaResidenciaPlaca(idCarga, placa);
                    almacenados.actPropietarCambResPlaca(idCarga, placa);
                    salida.setCodmensaje(Mensajes.OK);
                    mensaje = ("Se reproceso correctamente la información");
                    salida.setMensaje(mensaje);
                } else {
                    salida.setCodmensaje(Mensajes.ERROR);
                    mensaje = ("Error al registrar el proceso.");
                    salida.setMensaje(mensaje);
                    ut.rollback();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //Si existen registros se actualizan
            MigraActualizacionRna consulta = constanteDAO.consultaDetalleActualizacionRna(placa, idCarga);
            if (consulta != null) {
                MigraActualizacionRna cast = new MigraActualizacionRna();
                cast.setCodCarga(idCarga);
                cast.setEstadoProceso(consulta.getEstadoProceso());
                cast.setFechaProceso(new Date());
                cast.setUsuario(Integer.parseInt(infoUsuario.getLogin()));
                cast.setPlaca(placa);
                try {
                    UserTransaction ut = context.getUserTransaction();
                    ut.begin();
                    boolean actualizarProceso = constanteDAO.actualizaProceso(cast);
                    if (actualizarProceso) {
                        ut.commit();
                        almacenados.actualizaResidenciaPlaca(idCarga, placa);
                        almacenados.actPropietarCambResPlaca(idCarga, placa);
                        salida.setCodmensaje(Mensajes.OK);
                        mensaje = ("Se reproceso correctamente la información");
                        salida.setMensaje(mensaje);
                    } else {
                        salida.setCodmensaje(Mensajes.ERROR);
                        mensaje = ("Error al registrar el proceso.");
                        salida.setMensaje(mensaje);
                        ut.rollback();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        return salida;
    }
}
