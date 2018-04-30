/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.logica;

import co.com.runt.logconsultas.api.LogConsultaDTO;
import co.com.runt.sagir.common.Constantes;
import co.com.runt.sagir.dto.InfoUsuarioDTO;
import co.com.runt.sagir.exception.ErrorConsultaLogException;
import co.com.runt.sagir.impl.ClienteRunt;
import co.com.runt.sagir.impl.ClienteRuntBuilder;
import com.google.gson.JsonObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author dsalamanca
 */
@Stateless
public class GuardarLogLogica {

    public LogConsultaDTO casteadorLogRNA(JsonObject json, final Long idauttra, final String usuario, final String ip, final String tipoConsulta) throws NumberFormatException {
        //guardado
        LogConsultaDTO log = new LogConsultaDTO();
        log.setAplicacion(Constantes.APLICATION_CONSULTA);
        log.setAutoridad(idauttra);
        log.setConsulta(json.toString());
        log.setIpCliente(ip);
        log.setTipoConsulta(tipoConsulta);
        log.setUsuario(usuario);
        return log;
    }

    public LogConsultaDTO casteadorLogRNC(JsonObject json, final Long idauttra, final String usuario, final String ip, final String tipoConsulta) throws NumberFormatException {
        //guardado
        LogConsultaDTO log = new LogConsultaDTO();
        log.setAplicacion(Constantes.APLICATION_CONSULTA);
        log.setAutoridad(idauttra);
        log.setConsulta(json.toString());
        log.setIpCliente(ip);
        log.setTipoConsulta(tipoConsulta);
        log.setUsuario(usuario);
        return log;
    }

    public void guardarLog(LogConsultaDTO entrada) {
        try {
            final ClienteRuntBuilder builder = new ClienteRuntBuilder();
            final ClienteRunt clienteRunt = builder
                    .servidor("10.10.6.41")
                    .puerto(80)
                    .build();

            clienteRunt.guardarLog(entrada);
        } catch (ErrorConsultaLogException ex) {
            Logger.getLogger(GuardarLogLogica.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
