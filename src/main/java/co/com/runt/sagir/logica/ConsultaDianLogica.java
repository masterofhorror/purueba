/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.logica;

import co.com.runt.logconsultas.api.LogConsultaDTO;
import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.common.Mensajes;
import co.com.runt.sagir.dao.PlSqlDAO;
import co.com.runt.sagir.dto.ConsultaDianDTO;
import co.com.runt.sagir.dto.InfoUsuarioDTO;
import co.com.runt.sagir.dto.MensajeDTO;
import com.google.gson.JsonObject;
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
public class ConsultaDianLogica {

    @EJB
    private PlSqlDAO almacenados;

    @EJB
    private LogsConsultasLogica guardarLogLogica;

    public MensajeDTO consultaDeclaImpor(String numDeclaracion, InfoUsuarioDTO infUsuario, final String ip) {
        String mensaje;
        MensajeDTO salida = new MensajeDTO();
        ConsultaDianDTO declaImp = new ConsultaDianDTO();
        String ver1 = null, ver2= null, ver3 = null, ver4 = null, ver5 = null;

        declaImp = almacenados.pConsultaDeclaImpor(numDeclaracion);

        ver1 = declaImp.getFecLevante();
        ver2 = declaImp.getNumIdentificacionImpo();
        ver3 = declaImp.getNumLevante();
        ver4 = declaImp.getNumSubpartida();
        ver5 = declaImp.getTipIdentificacionImpo();
        

        if (ver1 != null && ver2 != null && ver3 != null && ver4 != null && ver5 != null) {
            if (declaImp != null) {
                salida.setCodmensaje(Mensajes.OK);
                salida.setObject(declaImp);
                JsonObject json = new JsonObject();
                json.addProperty("declaracion de Importacion", numDeclaracion);
                String tipoConsulta = Constantes.DECLARACION_IMPORTACION;
                Long autoridad = infUsuario.getIdOrganizacion();
                String usuario = infUsuario.getLogin();
                LogConsultaDTO log = guardarLogLogica.casteadorLogRNA(json, autoridad, usuario, ip, tipoConsulta);
                guardarLogLogica.guardarLog(log);

            } else {
                salida.setCodmensaje(Mensajes.ERROR);
                mensaje = ("No existen datos para la declaracion de importacion.");
                salida.setMensaje(mensaje);
            }
        } else {
            salida.setCodmensaje(Mensajes.ERROR);
            mensaje = ("No se encontro informacion con los parametro ingresados.");
            salida.setMensaje(mensaje);
        }

        return salida;
    }
}
