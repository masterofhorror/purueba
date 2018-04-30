/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.logica;


import co.com.runt.logconsultas.api.LogConsultaDTO;
import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.common.Mensajes;
import co.com.runt.sagir.dao.ConsultaDatosMigradosDAO;
import co.com.runt.sagir.dto.ConsultaDatosMigradosDTO;
import co.com.runt.sagir.dto.InfoUsuarioDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import co.com.runt.sagir.entities.ConsultaDatosMigrados;
import co.com.runt.sagir.utils.TransformacionDozer;
import com.google.gson.JsonObject;
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
public class ConsultaDatosMigradosLogica {
    
    @EJB
    private ConsultaDatosMigradosDAO datoMigradoDAO;
    
    @EJB
    private LogsConsultasLogica guardarLogLogica;
    
    public MensajeDTO consultaDatosMigrados (final String placa, final Long idCarga, InfoUsuarioDTO infUsuario, final String ip){
        String mensaje;
        MensajeDTO salida = new MensajeDTO();
        List<ConsultaDatosMigradosDTO> listaDTO;
        List<ConsultaDatosMigrados> lista = datoMigradoDAO.datosMigrados(placa, idCarga);
        
        if (lista != null && !lista.isEmpty()){
            listaDTO = TransformacionDozer.transformar(lista, ConsultaDatosMigradosDTO.class);
            salida.setCodmensaje(Mensajes.OK);
            salida.setObject(listaDTO);
            JsonObject json = new JsonObject();
            json.addProperty("Placa", placa);
            json.addProperty("Codido Carga", idCarga);
            String tipoConsulta = Constantes.DATOS_MIGRADOS;
            Long autoridad = infUsuario.getIdOrganizacion();
            String usuario = infUsuario.getLogin();
            LogConsultaDTO log = guardarLogLogica.casteadorLogRNA(json, autoridad, usuario, ip, tipoConsulta);
            guardarLogLogica.guardarLog(log);
        }else{
            salida.setCodmensaje(Mensajes.ERROR);
            mensaje = ("No existen datos en la consulta de datos migrados");
            salida.setMensaje(mensaje);
        }
        return salida;
    }
    
}
