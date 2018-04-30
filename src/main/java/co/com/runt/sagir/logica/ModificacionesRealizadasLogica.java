/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.logica;

import co.com.runt.logconsultas.api.LogConsultaDTO;
import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.common.Mensajes;
import co.com.runt.sagir.dao.ModificacionesRealizadasDAO;
import co.com.runt.sagir.dto.InfoUsuarioDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.dto.ModificacionesRealizadasActualizacionDTO;
import co.com.runt.sagir.dto.ModificacionesRealizadasCasosEspecialesDTO;
import co.com.runt.sagir.dto.ModificacionesRealizadasDTO;
import co.com.runt.sagir.entities.InfoModificacionesRealizadasActualizacion;
import co.com.runt.sagir.entities.InfoModificacionesRealizadasCasosEspeciales;
import co.com.runt.sagir.utils.TransformacionDozer;
import com.google.gson.JsonObject;
import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Hmoreno
 */
@Stateless
public class ModificacionesRealizadasLogica {

    private static final Logger LOGGER = Logger.getLogger(ModificacionesRealizadasLogica.class.getSimpleName());

    @EJB
    private ModificacionesRealizadasDAO modificacionesRealizadasDAO;

    @EJB
    private LogsConsultasLogica guardarLogLogica;

    public MensajeDTO consultarPorPlaca(String placa, final InfoUsuarioDTO usuario, final String ip) {
        MensajeDTO respuesta = new MensajeDTO();
        String tipoConsulta;
        try {
            ModificacionesRealizadasDTO modificacionesRealizadasDTO = new ModificacionesRealizadasDTO();

            List<InfoModificacionesRealizadasActualizacion> listaActualizaciones = modificacionesRealizadasDAO.buscarModificacionesActualizacionPorPlaca(placa);
            List<InfoModificacionesRealizadasCasosEspeciales> listaCasosEspeciales = modificacionesRealizadasDAO.buscarModificacionesCasosEspecialesPorPlaca(placa);

            if (!listaActualizaciones.isEmpty() || !listaCasosEspeciales.isEmpty()) {
                modificacionesRealizadasDTO.setActualizaciones(TransformacionDozer.transformar(listaActualizaciones, ModificacionesRealizadasActualizacionDTO.class));
                modificacionesRealizadasDTO.setCasosEspeciales(TransformacionDozer.transformar(listaCasosEspeciales, ModificacionesRealizadasCasosEspecialesDTO.class));
                JsonObject json = new JsonObject();
                json.addProperty("placa", placa);
                tipoConsulta = Constantes.TIPO_CONSULTA_MODIFICACIONES;
                Long idauttra = usuario.getIdOrganizacion();
                String datoUsuario = usuario.getLogin();
                LogConsultaDTO log = guardarLogLogica.casteadorLogRNA(json, idauttra, datoUsuario, ip, tipoConsulta);
                guardarLogLogica.guardarLog(log);
                respuesta.setCodmensaje(Mensajes.OK);
                respuesta.setObject(modificacionesRealizadasDTO);
            } else {
                respuesta.setCodmensaje(Mensajes.ERROR);
                respuesta.setMensaje(MessageFormat.format("No existe modificaciones realizadas para el vehículo de placa {0}.", placa));
            }
        } catch (Exception e) {
            respuesta.setCodmensaje(Mensajes.ERROR);
            respuesta.setMensaje("Error realizando la consulta de modificaciones realizadas. Por favor intente mas tarde.");

            LOGGER.log(Level.SEVERE, "Error realizando la consulta de modificaciones realizadas", e);
        }

        return respuesta;
    }

}
