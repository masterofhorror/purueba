/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.logica;

import co.com.runt.logconsultas.api.LogConsultaDTO;
import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.common.Mensajes;
import co.com.runt.sagir.dao.ConsultaRangoPlacaDAO;
import co.com.runt.sagir.dao.PlSqlDAO;
import co.com.runt.sagir.dto.ConsultaRangoPlacaDTO;
import co.com.runt.sagir.dto.ConsultaRangoPlacaRNADTO;
import co.com.runt.sagir.dto.ConsultaRangoPlacaTotalDTO;
import co.com.runt.sagir.dto.InfoUsuarioDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.entities.ConsultaRangoPlaca;
import co.com.runt.sagir.entities.ConsultaRangoPlacaRNA;
import co.com.runt.sagir.entities.ConsultaRangoPlacaTotal;
import co.com.runt.sagir.utils.TransformacionDozer;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

/**
 *
 * @author APENA
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ConsultaRangoPlacaLogica {
    
    @EJB
    private ConsultaRangoPlacaDAO rangoPlacaDAO;
    
    @EJB
    private PlSqlDAO almacenados;
    
    @EJB
    private LogsConsultasLogica guardarLogLogica;
    
    public MensajeDTO consultaRangoPlaca (String placaIni, String placaFin, InfoUsuarioDTO infUsuario, final String ip){
        String mensaje;
        MensajeDTO salida = new MensajeDTO();
        almacenados.pConsultarRangosPlacas(placaIni, placaFin);
        List<ConsultaRangoPlacaDTO> listaDTO;
        List<ConsultaRangoPlaca> lista = rangoPlacaDAO.rangoPlacas();
        if (lista != null && !lista.isEmpty()){
            listaDTO = TransformacionDozer.transformar(lista, ConsultaRangoPlacaDTO.class);
            salida.setCodmensaje(Mensajes.OK);
            salida.setObject(listaDTO);
            JsonObject json = new JsonObject();
            json.addProperty(Constantes.PLACAINICIAL, placaIni);
            json.addProperty(Constantes.PLACAFINAL, placaFin);
            String tipoConsulta = Constantes.RANGO_PLACA;
            Long autoridad = infUsuario.getIdOrganizacion();
            String usuario = infUsuario.getLogin();
            LogConsultaDTO log = guardarLogLogica.casteadorLogRNA(json, autoridad, usuario, ip, tipoConsulta);
            guardarLogLogica.guardarLog(log);
        }else{
            salida.setCodmensaje(Mensajes.ERROR);
            mensaje = "No existen datos en la consulta de rango de placas";
            salida.setMensaje(mensaje);
        }
        return salida;
    }
    
    public MensajeDTO consultaRangoPlacaTotal (InfoUsuarioDTO infUsuario, final String ip){
        String mensaje;
        MensajeDTO salida = new MensajeDTO();
        
        List<ConsultaRangoPlacaTotalDTO> listaDTO;
        List<ConsultaRangoPlacaTotal> lista = rangoPlacaDAO.rangoPlacasTotal();
        
        
        if (lista != null && !lista.isEmpty()){
            listaDTO = TransformacionDozer.transformar(lista, ConsultaRangoPlacaTotalDTO.class);
            salida.setCodmensaje(Mensajes.OK);
            salida.setObject(listaDTO);
            String placaMin = rangoPlacaDAO.minRangoPlaca();
            String placaMax = rangoPlacaDAO.maxRangoPlaca();
            JsonObject json = new JsonObject();
            json.addProperty(Constantes.PLACAINICIAL, placaMin);
            json.addProperty(Constantes.PLACAFINAL, placaMax);
            String tipoConsulta = Constantes.RANGO_PLACA_TOTAL;
            Long autoridad = infUsuario.getIdOrganizacion();
            String usuario = infUsuario.getLogin();
            LogConsultaDTO log = guardarLogLogica.casteadorLogRNA(json, autoridad, usuario, ip, tipoConsulta);
            guardarLogLogica.guardarLog(log);
        }else{
            salida.setCodmensaje(Mensajes.ERROR);
            mensaje = "No existen datos en la consulta de rango de placas";
            salida.setMensaje(mensaje);
        }
        return salida;
    }
    
    public MensajeDTO consultaRangoPlacaRNA (InfoUsuarioDTO infUsuario, final String ip){
        String mensaje;
        MensajeDTO salida = new MensajeDTO();
        
        List<ConsultaRangoPlacaRNADTO> listaDTO;
        List<ConsultaRangoPlacaRNA> lista = rangoPlacaDAO.rangoPlacasRNA();
        
        if (lista != null && !lista.isEmpty()){
            listaDTO = TransformacionDozer.transformar(lista, ConsultaRangoPlacaRNADTO.class);
            salida.setCodmensaje(Mensajes.OK);
            salida.setObject(listaDTO);
            String placaMin = rangoPlacaDAO.minRangoPlaca();
            String placaMax = rangoPlacaDAO.maxRangoPlaca();
            JsonObject json = new JsonObject();
            json.addProperty(Constantes.PLACAINICIAL, placaMin);
            json.addProperty(Constantes.PLACAFINAL, placaMax);
            String tipoConsulta = Constantes.RANGO_PLACA_TOTAL;
            Long autoridad = infUsuario.getIdOrganizacion();
            String usuario = infUsuario.getLogin();
            LogConsultaDTO log = guardarLogLogica.casteadorLogRNA(json, autoridad, usuario, ip, tipoConsulta);
            guardarLogLogica.guardarLog(log);
        }else{
            salida.setCodmensaje(Mensajes.ERROR);
            mensaje = "No existen datos para el rango de placas en el RNA";
            salida.setMensaje(mensaje);
        }
        return salida;
    }
    
}
